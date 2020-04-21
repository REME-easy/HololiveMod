package cards.summonCard;

import actions.SpawnMateAction;
import cards.attackCard.HololiveBlizzard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.Artia;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallArtia extends AbstractSummonCard {
    private static final String ID = "Hololive_CallArtia";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallArtia.png";
    private static final int COST = 2;
    public CallArtia(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 2;
        this.cardHP = 14;
        if(CardHelper.isBalance){
            this.cardATK = 1;
            this.cardHP = 7;
        }
        this.cardsToPreview = new HololiveBlizzard();
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：CN二期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:ホロ中国2期");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:CN Two");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new Artia(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallArtia();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：CN二期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:ホロ中国2期");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:CN Two");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
