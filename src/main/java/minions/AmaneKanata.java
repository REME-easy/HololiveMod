package minions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class AmaneKanata extends AbstractMinion {
    private static final String ID = "Hololive_AmaneKanata";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/AmaneKanata.png";
    private static final int basePassiveAmount = 3;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 6;
    private static final int originalATK = 6;
    public int DeltaATK = 0;
    public int DeltaHP = 0;
    public AmaneKanata(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new AmaneKanata(this.upgraded);
    }

    public void ChangeHP(int deltaHP,boolean isDamage){
        if(!isDamage) {
            this.DeltaHP += deltaHP;
            if (DeltaHP < 0) DeltaHP = 0;
        }
        super.ChangeHP(deltaHP,isDamage);
    }

    public void ChangeATK(int deltaATK,boolean isDamage){
        if(!isDamage) {
            this.DeltaATK += deltaATK;
            if (DeltaATK < 0) DeltaATK = 0;
        }
        super.ChangeATK(deltaATK,isDamage);
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
        Attack(AbstractGameAction.AttackEffect.SMASH);
        Trigger();
        super.AttackEffect();
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
