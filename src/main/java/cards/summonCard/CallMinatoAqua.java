package cards.summonCard;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import helper.CardHelper;
import minions.MinatoAqua;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallMinatoAqua extends AbstractSummonCard {
    private static final String ID = "Hololive_CallMinatoAqua";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallMinatoAqua.png";
    private static final int COST = 2;

    public CallMinatoAqua(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 4;
        this.cardHP = 12;
        if(CardHelper.isBalance){
            this.cardATK = 2;
            this.cardHP = 6;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：海蓝宝石");
            this.keywords.add("组合：卍组");
            this.keywords.add("组合：二期生");
            this.keywords.add("组合：全员");

        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:あくあマリン");
            this.keywords.add("てぇてぇ:卍組");
            this.keywords.add("てぇてぇ:二期生");
            this.keywords.add("てぇてぇ:全員");

        } else {
            this.keywords.add("Combination:AquaMarine");
            this.keywords.add("Combination:Manji");
            this.keywords.add("Combination:Two");
            this.keywords.add("Combination:ALL MEMBER");

        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new MinatoAqua(this.upgraded),this.cardATK,this.cardHP));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractOrb o:AbstractDungeon.player.orbs){
            if(o instanceof EmptyOrbSlot){
                ++count;
            }
        }
        if(count == 0){
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return  false;
        }else {
            return super.canUse(p, m);
        }
    }

    public AbstractCard makeCopy(){
        return new CallMinatoAqua();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：海蓝宝石");
                this.keywords.add("组合：卍组");
                this.keywords.add("组合：二期生");
                this.keywords.add("组合：全员");

            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:あくあマリン");
                this.keywords.add("てぇてぇ:卍組");
                this.keywords.add("てぇてぇ:二期生");
                this.keywords.add("てぇてぇ:全員");

            } else {
                this.keywords.add("Combination:AquaMarine");
                this.keywords.add("Combination:Manji");
                this.keywords.add("Combination:Two");
                this.keywords.add("Combination:ALL MEMBER");

            }

            this.initializeDescription();
        }
    }
}
