package cards.attackCard;

import actions.SpawnMateAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import helper.OrbHelper;
import minions.AbstractMinion;
import minions.AmaneKanata;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class ScarfAndHalo extends CustomCard {
    private static final String ID = "Hololive_ScarfAndHalo";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/ScarfAndHalo.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 13;
    private static final int UPGRADE_PLUS_DMG = 5;

    public ScarfAndHalo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.RARE, CardTarget.ENEMY);
        this.damage=this.baseDamage = ATTACK_DMG;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SMASH));
        for(AbstractMinion minion: OrbHelper.DeadMinionsThisBattle){
            if(minion instanceof AmaneKanata) {
                AbstractMinion minion1 = ((AmaneKanata) minion).makeCopy();
                minion1.Taunt = minion.Taunt;
                minion1.onDamageAbilities = minion.onDamageAbilities;
                minion1.onEvokeAbilities = minion.onEvokeAbilities;
                minion1.onAttackAbilities = minion.onAttackAbilities;
                minion1.ChangeATK(((AmaneKanata) minion).DeltaATK,true);
                minion1.ChangeHP(((AmaneKanata) minion).DeltaHP,true);
                this.addToBot(new SpawnMateAction(minion1,minion1.ATK,minion1.HP));
                break;
            }
        }


    }

    @Override
    public AbstractCard makeCopy() {
        return new ScarfAndHalo();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
