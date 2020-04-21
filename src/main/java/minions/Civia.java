package minions;

import com.badlogic.gdx.Gdx;
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

public class Civia extends AbstractMinion {
    private static final String ID = "Hololive_Civia";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/Civia.png";
    private static final int basePassiveAmount = 8;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 9;
    private static final int originalATK = 9;
    private int Energy = 0;
    private float EnergyFontScale = 0.7F;
    public Civia(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new Civia(this.upgraded);
    }

    @Override
    public void onEvoke(){
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,this.Energy, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
        super.onEvoke();
    }

    @Override
    public void onDamaged(int dmg,boolean Effect){
        if(dmg != 0) {
            if(this.upgraded)
                ChangeEnergy(dmg);
        }
        super.onDamaged(dmg,Effect);
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

    private void ChangeEnergy(int energy){
        Energy += energy;
        this.EnergyFontScale += 2.3F;
    }

    @Override
    public void AttackEffect(){
        ChangeEnergy(ATK);
        Trigger();
        super.AttackEffect();
    }

    @Override
    public void render(SpriteBatch sb){
        if(EnergyFontScale > 0.7F){
            EnergyFontScale -= Gdx.graphics.getDeltaTime() * 5;
        }
        super.render(sb);
    }


    @Override
    public void renderText(SpriteBatch sb){
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.Energy), this.cX - NUM_X_OFFSET * 2, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET , new Color(0.6F,0.6F,1.0F,1.0F), this.fontScale);
        super.renderText(sb);
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
