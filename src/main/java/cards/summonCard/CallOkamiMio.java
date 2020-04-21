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
import minions.OkamiMio;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallOkamiMio extends AbstractSummonCard {
    private static final String ID = "Hololive_CallOkamiMio";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallOkamiMio.png";
    private static final int COST = 2;

    public CallOkamiMio(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 7;
        this.cardHP = 7;
        if(CardHelper.isBalance){
            this.cardATK = 5;
            this.cardHP = 5;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：旧猫狗");
            this.keywords.add("组合：Gamers");
            this.keywords.add("组合：FAMS");
            this.keywords.add("组合：全员");

        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:フブミオ");
            this.keywords.add("てぇてぇ:おかころ");
            this.keywords.add("てぇてぇ:FAMS");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:CatDog");
            this.keywords.add("Combination:Gamers");
            this.keywords.add("Combination:FAMS");
            this.keywords.add("Combination:ALL MEMBER");


        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new OkamiMio(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallOkamiMio();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：旧猫狗");
                this.keywords.add("组合：Gamers");
                this.keywords.add("组合：FAMS");
                this.keywords.add("组合：全员");

            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:フブミオ");
                this.keywords.add("てぇてぇ:おかころ");
                this.keywords.add("てぇてぇ:FAMS");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:CatDog");
                this.keywords.add("Combination:Gamers");
                this.keywords.add("Combination:FAMS");
                this.keywords.add("Combination:ALL MEMBER");


            }

            this.initializeDescription();
        }
    }
}
