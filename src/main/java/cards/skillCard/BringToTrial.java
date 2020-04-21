package cards.skillCard;

import abilities.HurtedAbility;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AbstractMinion;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class BringToTrial extends CustomCard {
    private static final String ID = "Hololive_BringToTrial";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/BringToTrial.png";
    private static final int COST = 1;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    public BringToTrial() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.damage = this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof AbstractMinion){
                ((AbstractMinion) orb).onDamageAbilities.add(new HurtedAbility(damage));
                ((AbstractMinion) orb).Trigger();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BringToTrial();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}