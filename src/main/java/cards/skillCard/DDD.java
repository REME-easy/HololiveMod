package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class DDD extends CustomCard {
    private static final String ID = "Hololive_DDD";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/DDD.png";
    private static final int COST = 2;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_MAGIC_NUM = 1;
    public DDD() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new IncreaseMaxOrbAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DDD();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}