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
import minions.ShiroganeNoel;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallShiroganeNoel extends AbstractSummonCard {
    private static final String ID = "Hololive_CallShiroganeNoel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallShiroganeNoel.png";
    private static final int COST = 3;

    public CallShiroganeNoel(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 12;
        this.cardHP = 12;
        if(CardHelper.isBalance){
            this.cardATK = 8;
            this.cardHP = 8;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：银火");
            this.keywords.add("组合：三期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:ノエフレ");
            this.keywords.add("てぇてぇ:三期生");
            this.keywords.add("てぇてぇ:全員");
        } else {
            this.keywords.add("Combination:NoelFlare");
            this.keywords.add("Combination:Three");
            this.keywords.add("Combination:ALL MEMBER");
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new ShiroganeNoel(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallShiroganeNoel();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.cardATK += 2;
            this.cardHP += 2;
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：银火");
                this.keywords.add("组合：三期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:ノエフレ");
                this.keywords.add("てぇてぇ:三期生");
                this.keywords.add("てぇてぇ:全員");
            } else {
                this.keywords.add("Combination:NoelFlare");
                this.keywords.add("Combination:Three");
                this.keywords.add("Combination:ALL MEMBER");
            }

            this.initializeDescription();
        }
    }
}
