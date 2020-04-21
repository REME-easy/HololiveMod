package actions;

import cards.optionCard.AbstractCombinatiionCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import relics.TheStar;

import java.util.ArrayList;

public class ChooseAbilityAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CARD_SELECT");
    private static final String[] TEXT = uiStrings.TEXT;
    private float startingDuration;
    private Logger logger = LogManager.getLogger(ChooseAbilityAction.class);
    public ChooseAbilityAction(int num){
        this.amount = num;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update(){
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startingDuration) {
                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                ArrayList<AbstractCard> cards = TheStar.choices;
                logger.info( "cards.size = " + cards.size());
                for(AbstractCard card:cards){
                    tmpGroup.addToBottom(card);
                }
                if(tmpGroup.size() > 0){
                    if(tmpGroup.size() == 1 && tmpGroup.getBottomCard() instanceof AbstractCombinatiionCard){
                        ((AbstractCombinatiionCard) tmpGroup.getBottomCard()).CombinationEffect();
                        if(!CardLibrary.getCard(tmpGroup.getBottomCard().cardID).isSeen)
                            UnlockTracker.markCardAsSeen(tmpGroup.getBottomCard().cardID);
                        this.isDone = true;
                        for(AbstractGameAction gameAction:AbstractDungeon.actionManager.actions) {
                            if (gameAction instanceof ChooseAbilityAction) {
                                gameAction.isDone = true;
                            }
                        }
                    } else {
                        logger.info( "open grid");
                        AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0] + this.amount + TEXT[1]);
                    }
                }
            } else if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
                for(AbstractCard card:AbstractDungeon.gridSelectScreen.selectedCards){
                    if(card instanceof AbstractCombinatiionCard){
                        ((AbstractCombinatiionCard) card).CombinationEffect();
                         if(!CardLibrary.getCard(card.cardID).isSeen)
                             UnlockTracker.markCardAsSeen(card.cardID);
                    }
                }
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
            this.tickDuration();
        }
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "closeCurrentScreen"
    )
    public static class closeCurrentScreen {
        public closeCurrentScreen() {
        }

        @SpirePostfixPatch
        public static void postfix() {
            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
                for(AbstractGameAction gameAction:AbstractDungeon.actionManager.actions){
                    if (gameAction instanceof ChooseAbilityAction) {
                        gameAction.isDone = true;
                    }
                }
            }
        }
    }

}
