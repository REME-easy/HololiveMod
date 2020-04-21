package minions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import helper.CardHelper;

public class Mariner extends AbstractMinion {
    private static final String ID = "Hololive_Mariner";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/Mariner.png";
    private static final int basePassiveAmount = 2;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 3;
    private static final int originalATK = 3;
    private int Damage;
    public Mariner(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
        this.Damage = upgraded?5:3;
        if(CardHelper.isBalance){
            this.Damage = upgraded?3:2;
        }
    }

    public AbstractMinion makeCopy(){
        return new Mariner(this.upgraded);
    }

    @Override
    public void onEvoke(){
        super.onEvoke();
    }


    @Override
    public void onEndOfTurn(){
        this.AttackEffect();
    }


    public void onCardUseEffect(){
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,Damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        Trigger();
    }

    public void onCardUse(AbstractCard c){
        onCardUseEffect();
    }

    @Override
    public void AttackEffect(){
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,ATK, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        Trigger();
        super.AttackEffect();
    }

    @Override
    public void triggerEvokeAnimation(){
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
