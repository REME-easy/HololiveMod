package minions;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import helper.CardHelper;

public class HoushouMarine extends AbstractMinion {
    private static final String ID = "Hololive_HoushouMarine";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/HoushouMarine.png";
    private static final int basePassiveAmount = 2;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 6;
    private static final int originalATK = 3;
    public HoushouMarine(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new HoushouMarine(this.upgraded);
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
    public void AttackEffect() {
        Attack(AbstractGameAction.AttackEffect.LIGHTNING);
        AbstractMinion mariner = new Mariner(this.upgraded);
        int stats;
        stats = CardHelper.isBalance?(2 + (upgraded?1:0)):(3 + (upgraded?2:0));
        AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(mariner,stats,stats));
        Trigger();
        super.AttackEffect();
    }

    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
