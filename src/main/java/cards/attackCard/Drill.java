package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import minions.MurasakiShion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class Drill extends CustomCard {
    private static final String ID = "Hololive_Drill";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/Drill.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 1;
    private static final int MAGIC_NUM = 2;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public Drill() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean HasShion = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof MurasakiShion) {
                HasShion = true;
                break;
            }
        }
        int i;
        for(i = 0 ; i < this.magicNumber ; ++i){
            this.addToBot(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        }
        this.addToBot(new DrawCardAction(1));
        if(HasShion)
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));

    }

    public void triggerOnGlowCheck() {
        boolean HasShion = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof MurasakiShion) {
                HasShion = true;
                break;
            }
        }
        if (HasShion) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Drill();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}
