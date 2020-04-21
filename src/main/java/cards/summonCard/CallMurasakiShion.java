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
import minions.MurasakiShion;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallMurasakiShion extends AbstractSummonCard {
    private static final String ID = "Hololive_CallMurasakiShion";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallMurasakiShion.png";
    private static final int COST = 0;

    public CallMurasakiShion(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 3;
        this.cardHP = 3;
        if(CardHelper.isBalance){
            this.cardATK = 1;
            this.cardHP = 2;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：卍组");
            this.keywords.add("组合：夏紫");
            this.keywords.add("组合：二期生");
            this.keywords.add("组合：全员");

        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:卍組");
            this.keywords.add("てぇてぇ:シオり");
            this.keywords.add("てぇてぇ:二期生");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:Manji");
            this.keywords.add("Combination:MatsuriShion");
            this.keywords.add("Combination:Two");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new MurasakiShion(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallMurasakiShion();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.cardATK = 5;
            this.cardHP = 5;
            if(CardHelper.isBalance){
                this.cardATK = 3;
                this.cardHP = 3;
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：卍组");
                this.keywords.add("组合：夏紫");
                this.keywords.add("组合：二期生");
                this.keywords.add("组合：全员");

            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:卍組");
                this.keywords.add("てぇてぇ:シオり");
                this.keywords.add("てぇてぇ:二期生");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:Manji");
                this.keywords.add("Combination:MatsuriShion");
                this.keywords.add("Combination:Two");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
