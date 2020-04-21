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
import minions.HimemoriLuna;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallHimemoriLuna extends AbstractSummonCard {
    private static final String ID = "Hololive_CallHimemoriLuna";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallHimemoriLuna.png";
    private static final int COST = 1;
    public CallHimemoriLuna(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 7;
        this.cardHP = 7;
        if(CardHelper.isBalance){
            this.cardATK = 3;
            this.cardHP = 3;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：夏娜");
            this.keywords.add("组合：四期生");
            this.keywords.add("组合：全员");
        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:フェスティバルーナ");
            this.keywords.add("てぇてぇ:四期生");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:Matsuri&Luna");
            this.keywords.add("Combination:Four");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new HimemoriLuna(this.upgraded),this.cardATK,this.cardHP));
        if(CardHelper.UsedSummonCardsThisBattle.size() > 1) {
                AbstractSummonCard c = CardHelper.UsedSummonCardsThisBattle.get(CardHelper.UsedSummonCardsThisBattle.size() - 2);
                    if (upgraded) {
                        c.upgrade();
                    }
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        }
    }

    public AbstractCard makeCopy(){
        return new CallHimemoriLuna();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：夏娜");
                this.keywords.add("组合：四期生");
                this.keywords.add("组合：全员");
            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:フェスティバルーナ");
                this.keywords.add("てぇてぇ:四期生");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:Matsuri&Luna");
                this.keywords.add("Combination:Four");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
