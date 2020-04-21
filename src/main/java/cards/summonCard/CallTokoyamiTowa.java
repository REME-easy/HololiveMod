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
import minions.TokoyamiTowa;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallTokoyamiTowa extends AbstractSummonCard {
    private static final String ID = "Hololive_CallTokoyamiTowa";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallTokoyamiTowa.png";
    private static final int COST = 2;
    public CallTokoyamiTowa(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 10;
        this.cardHP = 10;
        if(CardHelper.isBalance){
            this.cardATK = 6;
            this.cardHP = 6;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：四期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:四期生");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:Four");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new TokoyamiTowa(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallTokoyamiTowa();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.cardHP = this.cardHP + 3;
            this.cardATK = this.cardATK + 3;
            if(CardHelper.isBalance){
                this.cardHP = this.cardHP - 1;
                this.cardATK = this.cardATK - 1;
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：四期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:四期生");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:Four");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
