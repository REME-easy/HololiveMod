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
import minions.AyundaRisu;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallAyundaRisu extends AbstractSummonCard {
    private static final String ID = "Hololive_CallAyundaRisu";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallAyundaRisu.png";
    private static final int COST = 1;
    public CallAyundaRisu(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 7;
        this.cardHP = 4;
        if(CardHelper.isBalance){
            this.cardATK = 6;
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
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new AyundaRisu(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallAyundaRisu();
    }


    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.cardATK = this.cardATK + 3;
        }
    }
}
