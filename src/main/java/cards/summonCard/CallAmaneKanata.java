package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.AmaneKanata;
import powers.SingPower;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallAmaneKanata extends AbstractSummonCard {
    private static final String ID = "Hololive_CallAmaneKanata";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallAmaneKanata.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    public CallAmaneKanata(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 6;
        this.cardHP = 6;
        if(CardHelper.isBalance){
            this.cardATK = 3;
            this.cardHP = 3;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：四期生");
            this.keywords.add("组合：天龙");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:四期生");
            this.keywords.add("てぇてぇ:収益化NG組");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:Four");
            this.keywords.add("Combination:Coco&Kanata");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AmaneKanata a = new AmaneKanata(this.upgraded);
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(a,this.cardATK,this.cardHP));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new SingPower(AbstractDungeon.player,1),1));

    }

    public AbstractCard makeCopy(){
        return new CallAmaneKanata();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}
