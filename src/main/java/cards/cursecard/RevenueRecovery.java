package cards.cursecard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;

public class RevenueRecovery extends CustomCard {
    private static final String ID = "Hololive_RevenueRecovery";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/cursecards/RevenueRecovery.png";
    private static final int COST = -2;
    private static final int MAGIC_NUM = 8;
    private static final int UPGRADE_MAGIC_NUM = 3;
    public RevenueRecovery() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CURSE, CardColor.CURSE,
                CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
    }


    @Override
    public void use(AbstractPlayer p,AbstractMonster m){
    }

    public void triggerWhenDrawn() {
        AbstractDungeon.player.loseGold(magicNumber);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;

    }



    @Override
    public AbstractCard makeCopy() {
        return new RevenueRecovery();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-UPGRADE_MAGIC_NUM);
        }
    }
}