package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import helper.CardHelper;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class Trident extends CustomCard {
    private static final String ID = "Hololive_Trident";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/Trident.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_MAGIC_NUM = 1;
    private boolean[] Enchant = new boolean[4];
    public Trident() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.COMMON, CardTarget.ENEMY);
        this.damage=this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
        if(CardHelper.isBalance){
            this.damage=this.baseDamage = ATTACK_DMG - 1;
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(Enchant[0]){
            this.addToBot(new SFXAction("ATTACK_HEAVY"));
            this.addToBot(new VFXAction(new CleaveEffect()));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        } else {
            this.addToBot(new DamageAction(m,new DamageInfo(p,damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        if(Enchant[2]){
            this.addToBot(new DrawCardAction(magicNumber));
        }
        if(Enchant[3]){
            AbstractMonster m1 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if(m1 != null && !m1.isDead) {
                this.addToTop(new VFXAction(new LightningEffect(m1.drawX, m1.drawY), 0.1F));
                this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
                this.addToBot(new DamageAction(m1,new DamageInfo(p,damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            }
        }
        if(this.costForTurn == 0 || this.cost == 0){
            this.costForTurn = 1;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Trident();
    }

    @Override
    public void triggerWhenDrawn() {
            int Index;
            if(!Enchant[0] || !Enchant[1] || !Enchant[2] || !Enchant[3]) {
                do {
                    Index = AbstractDungeon.cardRng.random(0, 3);
                } while (Enchant[Index]);
                Enchant[Index] = true;
                if(Enchant[1] && !returnToHand){
                    returnToHand  =true;
                }
                if(Enchant[0]){
                    this.target = CardTarget.ALL_ENEMY;
                }
                this.name = NAME;
                this.rawDescription = DESCRIPTION;
                int i;
                for(i = 0 ; i < 4 ; ++i){
                    if(Enchant[i]){
                        this.rawDescription = this.rawDescription.concat(EXTENDED_DESCRIPTION[i]);
                        this.name = this.name.concat(EXTENDED_DESCRIPTION[i + 4]);
                    }
                }
                if(upgraded)
                    this.upgradeName();
                this.initializeDescription();
                this.initializeTitle();
            }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}
