package minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class MinatoAqua extends AbstractMinion {
    private static final String ID = "Hololive_MinatoAqua";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/MinatoAqua.png";
    private static final int basePassiveAmount = 3;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 12;
    private static final int originalATK = 4;
    private int Attack = 0;
    public MinatoAqua(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new MinatoAqua(this.upgraded);
    }

    @Override
    public void onEvoke(){
        if(this.upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,2)));
        }
        super.onEvoke();
    }

    public void onCardUseEffect(){
        AttackEffect();
    }

    public void onCardUse(AbstractCard c){
        if (c.type == AbstractCard.CardType.ATTACK) {
            ++Attack;
            if(Attack == 2){
                onCardUseEffect();
                Attack = 0;
            }
        }
    }


    @Override
    public void onEndOfTurn(){
       this.AttackEffect();
    }

    @Override
    public void AttackEffect(){
        Attack(AbstractGameAction.AttackEffect.SLASH_HEAVY);
        Trigger();
        super.AttackEffect();
    }


    @Override
    public void renderText(SpriteBatch sb){
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, (Attack > 0)?"rua!":"x" , this.cX - NUM_X_OFFSET * 2, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET * 2, new Color(0.6F,0.0F,1.0F,1.0F), this.fontScale);
        super.renderText(sb);
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
