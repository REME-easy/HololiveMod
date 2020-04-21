package cards.optionCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.TheStar;

public class FamsCombinationOption extends AbstractCombinatiionCard {
    private static final String ID = "Hololive_FamsCombinationOption";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/optioncards/Fams.png";

    public FamsCombinationOption(){
        super(ID, NAME, IMG_PATH, DESCRIPTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void CombinationEffect() {
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof TheStar){

                for(AbstractCard card:AbstractDungeon.player.drawPile.group){
                    card.setCostForTurn(card.cost - 1);
                }
                for(AbstractCard card:AbstractDungeon.player.hand.group){
                    card.setCostForTurn(card.cost - 1);
                }
                for(AbstractCard card:AbstractDungeon.player.discardPile.group){
                    card.setCostForTurn(card.cost - 1);
                }
                TheStar.CombinationIndex[7] = true;
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
        return new FamsCombinationOption();
    }
}
