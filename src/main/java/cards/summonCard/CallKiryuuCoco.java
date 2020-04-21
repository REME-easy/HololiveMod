package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.CardHelper;
import minions.AbstractMinion;
import minions.KiryuuCoco;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallKiryuuCoco extends AbstractSummonCard {
    private static final String ID = "Hololive_CallKiryuuCoco";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallKiryuuCoco.png";
    private static final int COST = 5;

    public CallKiryuuCoco(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
            this.cardATK = 10;
            this.cardHP = 10;
        if(CardHelper.isBalance){
            this.cardATK = 8;
            this.cardHP = 8;
        }
            for(AbstractOrb abstractOrb:AbstractDungeon.actionManager.orbsChanneledThisCombat){
                if(abstractOrb instanceof AbstractMinion){
                    this.updateCost(-1);
                }
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
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new KiryuuCoco(this.upgraded),this.cardATK,this.cardHP));
    }

    public void onCallMinion(){
        this.updateCost(-1);
    }

    public AbstractCard makeCopy(){
        return new CallKiryuuCoco();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(4);
        }
    }
}
