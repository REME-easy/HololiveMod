package minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class SakuraMiko extends AbstractMinion {
    private static final String ID = "Hololive_SakuraMiko";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/SakuraMiko.png";
    private static final int basePassiveAmount = 3;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 5;
    private static final int originalATK = 4;
    public SakuraMiko(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new SakuraMiko(this.upgraded);
    }

    @Override
    public void onEvoke(){
        if(upgraded)
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,AbstractDungeon.player.gold / 25, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
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
        Attack(AbstractGameAction.AttackEffect.FIRE);
        Trigger();
        super.AttackEffect();
    }

    @Override
    public void renderText(SpriteBatch sb){
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(AbstractDungeon.player.gold / 25) , this.cX - NUM_X_OFFSET * 2, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET * 2, new Color(1.0F,0.7F,0.1F,1.0F), this.fontScale);
        super.renderText(sb);
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
