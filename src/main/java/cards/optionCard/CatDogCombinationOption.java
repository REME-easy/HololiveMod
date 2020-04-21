package cards.optionCard;

import actions.SpawnMateAction;
import cards.summonCard.CallPigeonMan;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import minions.PigeonMan;
import relics.TheStar;

public class CatDogCombinationOption extends AbstractCombinatiionCard {
    private static final String ID = "Hololive_CatDogCombinationOption";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/optioncards/CatDog.png";

    public CatDogCombinationOption(){
        super(ID, NAME, IMG_PATH, DESCRIPTION);
        this.cardsToPreview = new CallPigeonMan();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void CombinationEffect() {
        for(AbstractRelic relic: AbstractDungeon.player.relics){
            if(relic instanceof TheStar){

                this.addToBot(new SpawnMateAction(new PigeonMan(false),4,4));
                TheStar.CombinationIndex[8] = true;
                relic.description = relic.getUpdatedDescription();
                relic.tips.clear();
                relic.tips.add(new PowerTip(relic.name, relic.description));
                ((TheStar) relic).ChooseCombination();
                break;
            }
        }
    }

    public void upgrade() {
    }

    public AbstractCard makeCopy() {
        return new CatDogCombinationOption();
    }
}
