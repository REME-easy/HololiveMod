package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.SpadeEcho;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallSpadeEcho extends AbstractSummonCard {
    private static final String ID = "Hololive_CallSpadeEcho";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallSpadeEcho.png";
    private static final int COST = 0;

    public CallSpadeEcho(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 2;
        this.cardHP = 2;
        if(CardHelper.isBalance){
            this.cardATK = 1;
            this.cardHP = 1;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：吉祥三宝");
            this.keywords.add("组合：全员");

        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:ラッキートリオ");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:HappyFamily");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new SpadeEcho(this.upgraded),this.cardATK,this.cardHP));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }

    public AbstractCard makeCopy(){
        return new CallSpadeEcho();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：吉祥三宝");
                this.keywords.add("组合：全员");

            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:ラッキートリオ");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:HappyFamily");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
