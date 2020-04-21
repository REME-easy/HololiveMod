package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;
import minions.FriendA;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallFriendA extends AbstractSummonCard {
    private static final String ID = "Hololive_CallFriendA";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallFriendA.png";
    private static final int COST = 1;

    public CallFriendA(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 5;
        this.cardHP = 8;
        if(CardHelper.isBalance){
            this.cardATK = 1;
            this.cardHP = 6;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：空A");
            this.keywords.add("组合：零期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:そらえー");
            this.keywords.add("てぇてぇ:零期生");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:SoraA");
            this.keywords.add("Combination:Zero");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new FriendA(this.upgraded),this.cardATK,this.cardHP));
        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
        }
    }

    public AbstractCard makeCopy(){
        return new CallFriendA();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：空A");
                this.keywords.add("组合：零期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:そらえー");
                this.keywords.add("てぇてぇ:零期生");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:SoraA");
                this.keywords.add("Combination:Zero");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
