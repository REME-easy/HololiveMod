package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.MinatoAqua;
import minions.MurasakiShion;
import minions.NakiriAyame;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class ManjiGathering extends CustomCard {
    private static final String ID = "Hololive_ManjiGathering";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/ManjiGathering.png";
    private static final int COST = 1;
    private static final int MAGIC_NUM = 2;
    private static final int UPGRADE_MAGIC_NUM = 1;
    public ManjiGathering() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK,Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
            this.cardsToPreview = new Execute();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ct = 0;
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof MinatoAqua || orb instanceof NakiriAyame || orb instanceof MurasakiShion){
                ++ct;
            }
        }
            AbstractCard c = new Execute();
            c.baseDamage += ct;
            this.addToBot(new MakeTempCardInHandAction(c,magicNumber));

    }

    @Override
    public AbstractCard makeCopy() {
        return new ManjiGathering();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}