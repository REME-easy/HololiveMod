package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.OzoraSubaru;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallOzoraSubaru extends AbstractSummonCard {
    private static final String ID = "Hololive_CallOzoraSubaru";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallOzoraSubaru.png";
    private static final int COST = 1;
    public CallOzoraSubaru(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 4;
        this.cardHP = 6;
        if(CardHelper.isBalance){
            this.cardATK = 2;
            this.cardHP = 3;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：FAMS");
            this.keywords.add("组合：二期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:FAMS");
            this.keywords.add("てぇてぇ:二期生");
            this.keywords.add("てぇてぇ:全員");
        } else {
            this.keywords.add("Combination:FAMS");
            this.keywords.add("Combination:Two");
            this.keywords.add("Combination:ALL MEMBER");
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new OzoraSubaru(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallOzoraSubaru();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：FAMS");
                this.keywords.add("组合：二期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:FAMS");
                this.keywords.add("てぇてぇ:二期生");
                this.keywords.add("てぇてぇ:全員");
            } else {
                this.keywords.add("Combination:FAMS");
                this.keywords.add("Combination:Two");
                this.keywords.add("Combination:ALL MEMBER");
            }

            this.initializeDescription();
        }
    }
}
