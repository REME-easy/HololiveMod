package cards.attackCard;

import basemod.abstracts.CustomCard;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class OpenUp extends CustomCard {
    private static final String ID = "Hololive_OpenUp";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/OpenUp.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    public OpenUp() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ct = 0;
        for(AbstractCard card:p.hand.group){
            if(card instanceof AbstractSummonCard){
                this.addToBot(new ExhaustSpecificCardAction(card,p.hand));
                ++ct;
            }
        }
        int i;
        for(i = 0 ; i < ct ; ++i){
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(ATTACK).makeCopy();
            c.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(c));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new OpenUp();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}