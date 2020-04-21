package cards.optionCard;

import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.TheStar;

public class MatsuriLunaCombinationOption extends AbstractCombinatiionCard {
    private static final String ID = "Hololive_MatsuriLunaCombinationOption";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/optioncards/MatsuriLuna.png";

    public MatsuriLunaCombinationOption(){
        super(ID, NAME, IMG_PATH, DESCRIPTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void CombinationEffect() {
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof TheStar){
                TheStar.CombinationIndex[22] = true;
                for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                    if(c instanceof AbstractSummonCard){
                        ((AbstractSummonCard) c).cardATK = ((AbstractSummonCard) c).cardATK + 1;
                        ((AbstractSummonCard) c).cardHP = ((AbstractSummonCard) c).cardHP + 1;
                    }
                }
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
        return new MatsuriLunaCombinationOption();
    }
}
