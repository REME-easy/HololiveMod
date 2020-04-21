package actions;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AkiRosenthal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class CallMoonahoshinovaAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CARD_SELECT");
    private static final String[] TEXT = uiStrings.TEXT;
    private float startingDuration;
    private boolean upgraded = false;
    private Logger logger = LogManager.getLogger(CallMoonahoshinovaAction.class);
    public CallMoonahoshinovaAction(boolean upgraded){
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.upgraded = upgraded;
    }

    public void update(){
        int akicount = 0;
        int akipluscount = 0;
        int i;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startingDuration) {
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                        ++akipluscount;
                    if (orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                        ++akicount;
                }
                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                ArrayList<AbstractCard> derp = new ArrayList<>();

                while(derp.size() != (upgraded?4:3)) {
                    boolean dupe = false;
                    int roll = AbstractDungeon.cardRandomRng.random(99);
                    AbstractCard.CardRarity cardRarity;
                    if (roll < 55) {
                        cardRarity = AbstractCard.CardRarity.COMMON;
                    } else if (roll < 85) {
                        cardRarity = AbstractCard.CardRarity.UNCOMMON;
                    } else {
                        cardRarity = AbstractCard.CardRarity.RARE;
                    }

                    AbstractCard tmp = CardLibrary.getAnyColorCard(cardRarity);
                    Iterator var6 = derp.iterator();

                    while(var6.hasNext()) {
                        AbstractCard c = (AbstractCard)var6.next();
                        if (c.cardID.equals(tmp.cardID)) {
                            dupe = true;
                            break;
                        }
                    }

                    if (!dupe) {
                        derp.add(tmp.makeCopy());
                    }
                }


                logger.info( "cards.size = " + derp.size());
                for(AbstractCard card:derp){
                    tmpGroup.addToBottom(card);
                }

                if(akipluscount != 0){
                    for(AbstractCard card:tmpGroup.group){
                        for(i = 0 ; i < akicount + 1 ; i++){
                            AbstractCard c = card.makeCopy();
                            this.addToBot(new NewQueueCardAction(c,true,false,true));
                        }
                    }
                }else{
                    if(tmpGroup.size() > 0){
                        logger.info( "open grid");
                        AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, true, TEXT[0] + 1 + TEXT[2]);
                    }
                }
            } else if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
                for(AbstractCard card:AbstractDungeon.gridSelectScreen.selectedCards){
                    for(i = 0 ; i < akicount + 1 ; i++){
                        AbstractCard c = card.makeCopy();
                        this.addToBot(new NewQueueCardAction(c,true,false,true));
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
                    if (gameAction instanceof CallMoonahoshinovaAction) {
                        gameAction.isDone = true;
                    }
                }
            }
        }
    }

}
