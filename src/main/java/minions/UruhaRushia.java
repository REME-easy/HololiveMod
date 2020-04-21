package minions;

import actions.CallPigeonManAction;
import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import helper.OrbHelper;

import java.util.ArrayList;

public class UruhaRushia extends AbstractMinion {
    private static final String ID = "Hololive_UruhaRushia";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/UruhaRushia.png";
    private static final int basePassiveAmount = 6;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 8;
    private static final int originalATK = 6;
    public UruhaRushia(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new UruhaRushia(this.upgraded);
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
        Attack(AbstractGameAction.AttackEffect.POISON);
        if(OrbHelper.DeadMinionsThisBattle.size()  != 0){
            ArrayList<AbstractMinion> minions = OrbHelper.DeadMinionsThisBattle;
            Random random = new Random();
            int i = upgraded?6:4;
            AbstractMinion m = (AbstractMinion) (minions.get(random.random(0,minions.size() - 1)).makeCopy());
            m.onEvokeAbilities.clear();
            m.onDamageAbilities.clear();
            m.onAttackAbilities.clear();
            if(m instanceof PigeonMan){
                AbstractDungeon.actionManager.addToBottom(new CallPigeonManAction(m,i,i));
            } else {
                AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(m,i,i));
            }
        }
        Trigger();
        super.AttackEffect();
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
