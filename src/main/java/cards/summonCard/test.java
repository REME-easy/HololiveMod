package cards.summonCard;

import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import monsters.NatsuiroMatsuri;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class test extends AbstractSummonCard {
    private static final String ID = "Hololive_test";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallRoboco.png";
    private static final int COST = 0;

    public test(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.SPECIAL, CardTarget.NONE);
        this.cardATK = 9;
        this.cardHP = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new test1(),this.cardATK,this.cardHP));
        this.addToBot(new SpawnMonsterAction(new NatsuiroMatsuri(),false));
    }

    public AbstractCard makeCopy(){
        return new test();
    }

    public void upgrade() {
    }
}
