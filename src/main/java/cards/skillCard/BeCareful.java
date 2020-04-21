package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class BeCareful extends CustomCard {
    private static final String ID = "Hololive_BeCareful";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/BeCareful.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    public BeCareful() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractPower power:p.powers){
            if(power.amount != -1) {
                this.addToBot(new ApplyPowerAction(p,p,power,power.amount));
            }
        }
        for(AbstractMonster monster:AbstractDungeon.getMonsters().monsters) {
            for(AbstractPower power:monster.powers){
                if(power.ID.equals("Time Warp")){
                    if(power.amount > 5){
                        this.addToBot(new ApplyPowerAction(monster,p,power,11 - power.amount));
                    } else {
                        this.addToBot(new ApplyPowerAction(monster,p,power,power.amount));
                    }
                    continue;
                }
                if(power.amount != -1) {
                    this.addToBot(new ApplyPowerAction(monster,p,power,power.amount));
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeCareful();
    }



    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(UPGRADE_COST);
    }
}