package minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class Rosalyn extends AbstractMinion {
    private static final String ID = "Hololive_Rosalyn";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/Rosalyn.png";
    private static final int basePassiveAmount = 6;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 8;
    private static final int originalATK = 8;
    private int maxtime = 0;
    public Rosalyn(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new Rosalyn(this.upgraded);
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
    public void onStartOfTurn(){
        maxtime = 0;
    }

    @Override
    public void onEndOfTurn(){
        this.AttackEffect();
    }

    @Override
    public void AttackEffect(){
        if(AbstractDungeon.player.drawPile.size() > 0 && maxtime < 3){
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard().makeCopy();
            AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction(c,true,false,true));
            ++maxtime;
        }
        Trigger();
        super.AttackEffect();
    }

    @Override
    public void renderText(SpriteBatch sb){
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(maxtime) , this.cX - NUM_X_OFFSET * 2, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET * 2, new Color(0.2F,0.0F,1.0F,1.0F), this.fontScale);
        super.renderText(sb);
    }

    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
