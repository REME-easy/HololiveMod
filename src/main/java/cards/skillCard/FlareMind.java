package cards.skillCard;

import actions.SpawnFlareAction;
import basemod.abstracts.CustomCard;
import cards.summonCard.CallFlare;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import minions.Flare;
import powers.FlarePower;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class FlareMind extends CustomCard {
    private static final String ID = "Hololive_FlareMind";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/FlareMind.png";
    private static final int COST = 1;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_MAGIC_NUM = 2;
    public FlareMind() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
        this.cardsToPreview = new CallFlare();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m,p,new FlarePower(m,magicNumber),magicNumber));
        this.addToBot(new SpawnFlareAction(new Flare(this.upgraded),m));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlareMind();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}