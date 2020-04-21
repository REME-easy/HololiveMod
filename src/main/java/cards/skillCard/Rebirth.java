package cards.skillCard;

import actions.CallPigeonManAction;
import actions.SpawnMateAction;
import basemod.abstracts.CustomCard;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import helper.OrbHelper;
import minions.AbstractMinion;
import minions.PigeonMan;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class Rebirth extends CustomCard {
    private static final String ID = "Hololive_Rebirth";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/Rebirth.png";
    private static final int COST = 3;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_COST = 2;
    public Rebirth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMinion> minions = new ArrayList<>();
        if(OrbHelper.DeadMinionsThisBattle.size() < this.magicNumber){
            for(AbstractMinion minion2:OrbHelper.DeadMinionsThisBattle){
                minions.add((AbstractMinion) minion2.makeCopy());
            }
        } else {
            Random random = new Random();
            int i;
            while (minions.size() != magicNumber) {
                boolean sameone = false;
                AbstractMinion minion = OrbHelper.DeadMinionsThisBattle.get(random.random(0, OrbHelper.DeadMinionsThisBattle.size() - 1));
                    for (AbstractMinion minion1 : minions) {
                        if (minion1 == minion){
                            sameone = true;
                            break;
                        }
                    }
                    if(!sameone){
                        minions.add((AbstractMinion) minion.makeCopy());
                    }
            }
        }
        for(AbstractMinion minion:minions){
            AbstractSummonCard summonCard = OrbHelper.GetCardInstance(minion);
            if(summonCard != null) {
                if(minion.upgraded){
                    summonCard.upgrade();
                }
                if(minion instanceof PigeonMan){
                    this.addToBot(new CallPigeonManAction(minion, summonCard.cardATK, summonCard.cardHP));
                } else {
                    this.addToBot(new SpawnMateAction(minion, summonCard.cardATK, summonCard.cardHP));
                }
                minion.onDamageAbilities.clear();
                minion.onEvokeAbilities.clear();
                minion.onAttackAbilities.clear();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rebirth();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}