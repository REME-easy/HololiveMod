package cards.optionCard;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.TheStar;

public class GamersCombinationOption extends AbstractCombinatiionCard {
    private static final String ID = "Hololive_GamersCombinationOption";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/optioncards/Gamers.png";

    public GamersCombinationOption(){
        super(ID, NAME, IMG_PATH,DESCRIPTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void CombinationEffect() {
        for(AbstractCard card:AbstractDungeon.player.drawPile.group){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card,1));
        }
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof TheStar){

                TheStar.CombinationIndex[3] = true;
                relic.description = relic.getUpdatedDescription();
                relic.tips.clear();
                relic.tips.add(new PowerTip(relic.name, relic.description));
                break;
            }
        }
    }

    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new GamersCombinationOption();
    }
}
