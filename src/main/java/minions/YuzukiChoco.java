package minions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

import java.util.ArrayList;

public class YuzukiChoco extends AbstractMinion {
    private static final String ID = "Hololive_YuzukiChoco";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/YuzukiChoco.png";
    private static final int basePassiveAmount = 6;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 6;
    private static final int originalATK = 6;
    public YuzukiChoco(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new YuzukiChoco(this.upgraded);
    }

    @Override
    public void onEvoke(){
        ArrayList<AbstractMinion> abstractMinionArrayList = new ArrayList<>();
        Random random = new Random();
        for (AbstractOrb orb:AbstractDungeon.player.orbs) {
            if(orb instanceof AbstractMinion && orb != this)
                abstractMinionArrayList.add((AbstractMinion) orb);
        }
        if(abstractMinionArrayList.size() != 0){
        AbstractMinion abstractMinion = abstractMinionArrayList.get(random.random(0, abstractMinionArrayList.size()-1));
        abstractMinion.ChangeHP(2,false);
        abstractMinion.ChangeATK(2,false);
        }
        super.onEvoke();
    }

    @Override
    public void onEndOfTurn(){
        this.AttackEffect();
    }

    @Override
    public void AttackEffect(){
        Attack(AbstractGameAction.AttackEffect.BLUNT_LIGHT);
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
