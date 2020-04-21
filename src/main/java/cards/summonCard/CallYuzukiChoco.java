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
import com.megacrit.cardcrawl.random.Random;
import helper.CardHelper;
import minions.AbstractMinion;
import minions.YuzukiChoco;

import java.util.ArrayList;

import static patches.AbstractCardEnum.Hololive_BLUE;

public class CallYuzukiChoco extends AbstractSummonCard {
    private static final String ID = "Hololive_CallYuzukiChoco";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/summoncards/CallYuzukiChoco.png";
    private static final int COST = 1;

    public CallYuzukiChoco(){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.cardATK = 6;
        this.cardHP = 6;
        if(CardHelper.isBalance){
            this.cardATK = 3;
            this.cardHP = 3;
        }
        if(Settings.language == Settings.GameLanguage.ZHS){
            this.keywords.add("组合：金发组");
            this.keywords.add("组合：二期生");
            this.keywords.add("组合：全员");

        } else if(Settings.language == Settings.GameLanguage.JPN){
            this.keywords.add("てぇてぇ:金髪組");
            this.keywords.add("てぇてぇ:二期生");
            this.keywords.add("てぇてぇ:全員");
        } else {
            this.keywords.add("Combination:BlondHair");
            this.keywords.add("Combination:Two");
            this.keywords.add("Combination:ALL MEMBER");
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            ArrayList<AbstractMinion> abstractMinionArrayList = new ArrayList<>();
            Random random = new Random();
            for (AbstractOrb orb:AbstractDungeon.player.orbs) {
                if(orb instanceof AbstractMinion)
                    abstractMinionArrayList.add((AbstractMinion) orb);
            }
            if(abstractMinionArrayList.size() != 0) {
                AbstractMinion abstractMinion = abstractMinionArrayList.get(random.random(0, abstractMinionArrayList.size() - 1));
                abstractMinion.ChangeHP(2,false);
                abstractMinion.ChangeATK(2,false);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new YuzukiChoco(this.upgraded),this.cardATK,this.cardHP));
    }

    public AbstractCard makeCopy(){
        return new CallYuzukiChoco();
    }

    public void upgrade(){
        if(!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            if(Settings.language == Settings.GameLanguage.ZHS){
                this.keywords.add("组合：金发组");
                this.keywords.add("组合：二期生");
                this.keywords.add("组合：全员");

            } else if(Settings.language == Settings.GameLanguage.JPN){
                this.keywords.add("てぇてぇ:金髪組");
                this.keywords.add("てぇてぇ:二期生");
                this.keywords.add("てぇてぇ:全員");
            } else {
                this.keywords.add("Combination:BlondHair");
                this.keywords.add("Combination:Two");
                this.keywords.add("Combination:ALL MEMBER");
            }

            this.initializeDescription();
        }
    }
}
