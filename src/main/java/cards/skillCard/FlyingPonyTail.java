package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.CardHelper;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class FlyingPonyTail extends CustomCard {
    private static final String ID = "Hololive_FlyingPonyTail";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/FlyingPonyTail.png";
    private static final int COST = 0;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;
    private static final int MAGIC_NUM = 1;
    public FlyingPonyTail() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.COMMON, CardTarget.SELF);
        this.exhaust= true;
        this.baseBlock = this.block = BLOCK;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
        if(CardHelper.isBalance){
            this.baseBlock = this.block = BLOCK - 2;
        }
        this.isInnate = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p,block));
        this.addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlyingPonyTail();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);

        }
    }
}