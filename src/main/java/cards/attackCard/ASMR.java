package cards.attackCard;

import basemod.abstracts.CustomCard;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.OrbHelper;
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class ASMR extends CustomCard {
    private static final String ID = "Hololive_ASMR";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/ASMR.png";
    private static final int COST = 2;
    public ASMR() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = 0;
        for(AbstractOrb orb:p.orbs) {
            if(orb instanceof AbstractMinion) {
                AbstractSummonCard card = OrbHelper.GetCardInstance((AbstractMinion)orb);
                if(card != null) {
                    dmg += Math.abs(((AbstractMinion) orb).ATK - card.cardATK) ;
                    dmg += Math.abs(((AbstractMinion) orb).HP - card.cardHP);
                }
            }
        }
        this.addToBot(new DamageAction(m,new DamageInfo(p,dmg,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }


    @Override
    public AbstractCard makeCopy() {
        return new ASMR();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
