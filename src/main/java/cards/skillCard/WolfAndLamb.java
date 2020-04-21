package cards.skillCard;

import abilities.LambAbility;
import basemod.abstracts.CustomCard;
import cards.summonCard.CallLamb;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class WolfAndLamb extends CustomCard {
    private static final String ID = "Hololive_WolfAndLamb";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/WolfAndLamb.png";
    private static final int COST = 1;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_MAGIC_NUM = 1;
    public WolfAndLamb() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
        this.cardsToPreview = new CallLamb();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof AbstractMinion){
                ((AbstractMinion) orb).onEvokeAbilities.add(new LambAbility(magicNumber,upgraded));
                ((AbstractMinion) orb).Trigger();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WolfAndLamb();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}