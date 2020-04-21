package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import helper.OrbHelper;
import minions.AZKi;
import minions.AbstractMinion;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallAZKi extends AbstractSummonCard {
    private static final String ID = "Hololive_CallAZKi";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallAZKi.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    public CallAZKi(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 7;
        this.cardHP = 7;
        if(CardHelper.isBalance){
            this.cardATK = 3;
            this.cardHP = 4;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：MUSIC");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:MUSIC");
            this.keywords.add("てぇてぇ:全員");
        } else {
            this.keywords.add("Combination:MUSIC");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        for(i = 0 ; i < p.orbs.size() - 1 ; ++i){
            if(p.orbs.get(i) instanceof AbstractMinion){
                AbstractSummonCard c = OrbHelper.GetCardInstance(i);
                if( c != null) {
                    if (((AbstractMinion) p.orbs.get(i)).upgraded) c.upgrade();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new AZKi(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallAZKi();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}
