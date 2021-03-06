package cards.summonCard;

import actions.SpawnMateAction;
import cards.attackCard.Knife;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.Yogiri;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallYogiri extends AbstractSummonCard {
    private static final String ID = "Hololive_CallYogiri";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallYogiri.png";
    private static final int COST = 2;

    public CallYogiri(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardsToPreview = new Knife();
        this.cardATK = 3;
        this.cardHP = 10;
        if(CardHelper.isBalance){
            this.cardATK = 1;
            this.cardHP = 6;
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
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new Yogiri(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallYogiri();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
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
