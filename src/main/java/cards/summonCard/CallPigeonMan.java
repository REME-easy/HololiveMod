package cards.summonCard;

import actions.CallPigeonManAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.PigeonMan;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallPigeonMan extends AbstractSummonCard {
    private static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings("Hololive_CantCallMinions").EXTENDED_DESCRIPTION;
    private static final String ID = "Hololive_CallPigeonMan";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallPigeonMan.png";
    private static final int COST = 0;

    public CallPigeonMan(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 4;
        this.cardHP = 4;
        if(CardHelper.isBalance){
            this.cardATK = 1;
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CallPigeonManAction(new PigeonMan(upgraded),cardATK,cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallPigeonMan();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
