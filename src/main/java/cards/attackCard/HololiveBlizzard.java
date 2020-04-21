package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import helper.CardHelper;
import helper.OrbHelper;
import minions.MurasakiShion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class HololiveBlizzard extends CustomCard {
    private static final String ID = "Hololive_Blizzard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/Blizzard.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public HololiveBlizzard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
        if(CardHelper.isBalance){
            this.baseMagicNumber = this.magicNumber = MAGIC_NUM - 1;
        }
        this.isMultiDamage = true;
        this.exhaust = true;
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
        int ct = OrbHelper.CallMinionsThisBattle;
        this.baseDamage = ct * this.magicNumber;
        this.calculateCardDamage((AbstractMonster)null);
        this.addToBot(new VFXAction(new BlizzardEffect(ct, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
        if(HasShion)
            this.addToBot(new GainEnergyAction(1));
    }

    public void applyPowers() {
        int ct = OrbHelper.CallMinionsThisBattle;
        if (ct > 0) {
            this.baseDamage = ct * this.magicNumber;
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

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

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new HololiveBlizzard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}
