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
import minions.AiraniIofifteen;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallAiraniIofifteen extends AbstractSummonCard {
    private static final String ID = "Hololive_CallAiraniIofifteen";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallAiraniIofifteen.png";
    private static final int COST = 2;
    public CallAiraniIofifteen(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 2;
        this.cardHP = 8;
        if(CardHelper.isBalance){
            this.cardHP = 5;
        }
        if (Settings.language == Settings.GameLanguage.ZHS) {
            this.keywords.add("组合：Hololive-ID");
            this.keywords.add("组合：全员");
        } else if (Settings.language == Settings.GameLanguage.JPN) {
            this.keywords.add("てぇてぇ:ホロライブID");
            this.keywords.add("てぇてぇ:全員");
        } else {
            this.keywords.add("Combination:Hololive-ID");
            this.keywords.add("Combination:ALL MEMBER");
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new AiraniIofifteen(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallAiraniIofifteen();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardHP = this.cardHP + 3;
        }
    }
}
