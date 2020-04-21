package minions;

import basemod.ReflectionHacks;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import helper.CardHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Roboco extends AbstractMinion {
    private static final String ID = "Hololive_Roboco";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/Roboco.png";
    private static final int basePassiveAmount = 3;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 9;
    private static final int originalATK = 9;
    private static final Logger logger =LogManager.getLogger(Roboco.class);
    public Roboco(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new Roboco(this.upgraded);
    }

    @Override
    public void onEvoke(){
        super.onEvoke();
    }

    @Override
    public void triggerEvokeAnimation(){
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }


    @Override
    public void onEndOfTurn(){
        this.AttackEffect();
    }

    @Override
    public void AttackEffect(){
        boolean IsDone = false;
        float[] score = {0,0,0};
        if(AbstractDungeon.getMonsters().monsters != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            for(AbstractMonster m1:AbstractDungeon.getMonsters().monsters){
                if(this.ATK >= m1.currentHealth && !m1.isDead && !AbstractDungeon.player.hasRelic("Hololive_Yo")){
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m1,new DamageInfo(AbstractDungeon.player,ATK, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    IsDone = true;
                    logger.info( "IsDone = true");
                    break;
                }
            }
        if(!IsDone){
            AbstractMonster m = null;
            if(AbstractDungeon.getMonsters().monsters != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                float tmp;
                for (AbstractMonster m1 : AbstractDungeon.getMonsters().monsters) {
                    if(!m1.isDead) {
                        tmp = (m1.maxHealth - m1.currentHealth) * 1.0F / m1.maxHealth * 100;
                        logger.info(m1.toString() + "的伤害计算分数：" + tmp);
                        if (tmp > score[0]) {
                            score[0] = tmp;
                            m = m1;
                        }
                    }
                }
                logger.info("伤害判断分数：" + score[0]);
            }

            int ct = 0;
            int hp = 0;
            int dmg = 0;
            int tempdmg = 0;
            if(AbstractDungeon.getMonsters().monsters != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                for(AbstractMonster m3:AbstractDungeon.getMonsters().monsters){
                    if(!m3.isDead && m3.intent == AbstractMonster.Intent.ATTACK || m3.intent == AbstractMonster.Intent.ATTACK_BUFF || m3.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m3.intent == AbstractMonster.Intent.ATTACK_DEFEND)
                        tempdmg = (Integer) ReflectionHacks.getPrivate(m3, AbstractMonster.class, "intentDmg");
                    if ((Boolean)ReflectionHacks.getPrivate(m3, AbstractMonster.class, "isMultiDmg")) {
                        tempdmg *= (Integer)ReflectionHacks.getPrivate(m3, AbstractMonster.class, "intentMultiAmt");
                    }
                    dmg += tempdmg;
                    logger.info( m3.toString() + "的格挡计算分数：" + tempdmg);
                }
            int mn = 0;
            for(AbstractOrb orb:AbstractDungeon.player.orbs){
                if(orb instanceof AbstractMinion){
                    ++mn;
                    hp += ((AbstractMinion) orb).HP;
                    if(dmg >= ((AbstractMinion) orb).HP){
                        dmg -= ((AbstractMinion) orb).HP;
                        ++ct;
                    }else{
                        break;
                    }
                }
            }
            score[1] = (ct / mn * 100.0F + dmg / hp * 100.0F) / 2;
            logger.info("格挡判断分数：" + score[1]);

            if(this.upgraded) {
                int cn = 0;
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if(c instanceof AbstractSummonCard){
                        ++cn;
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if(c instanceof AbstractSummonCard){
                        ++cn;
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if(c instanceof AbstractSummonCard){
                        ++cn;
                    }
                }
                score[2] = Integer.max(( 2 - cn ) * 50 , 20);
                logger.info("给牌判断分数：" + score[2]);

            }
            if(score[0] > score[1] && score[0] > score[2] && !AbstractDungeon.player.hasRelic("Hololive_Yo")){
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,ATK, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }else if (score[1] > score[0] && score[1] > score[2]){
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player,ATK));
            }else if (score[2] > score[0] && score[2] > score[1]){
                if(upgraded)
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(CardHelper.GetRandomProgramCard(),1,false,true,false));
                if(!AbstractDungeon.player.hasRelic("Hololive_Yo"))
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,ATK, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }else {
                if(!AbstractDungeon.player.hasRelic("Hololive_Yo"))
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,ATK, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }

        Trigger();
        super.AttackEffect();
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
