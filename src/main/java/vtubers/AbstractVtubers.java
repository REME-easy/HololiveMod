package vtubers;

import abilities.AbstractAbility;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import minions.AbstractMinion;

public abstract class AbstractVtubers extends AbstractMinion {
    public int HP = 0;
    public int ATK = 0;
    public boolean Taunt = false;
    public boolean upgraded = false;
    public boolean isdead = false;
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    private boolean ClickStart = false;
    private boolean Ishovered = false;
    private float DrawScale = 1.0F;
    private float DamageTimer = 0.3F;

    public AbstractVtubers(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, int HP, String IMG_PATH, String DESCRIPTION, String UPGRADE_DESCRIPTION){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,HP,IMG_PATH,DESCRIPTION,UPGRADE_DESCRIPTION);
        this.HP = HP;
        this.DESCRIPTION = DESCRIPTION;
        this.UPGRADE_DESCRIPTION = UPGRADE_DESCRIPTION;

    }

    @Override
    public void update() {
        if(this.hb != null)
            if(this.hb.hovered && !Ishovered){
                this.DrawScale = 2.0F;
                Ishovered = true;
            } else {
                Ishovered = false;
            }
        if(this.DrawScale > 1.0F && !Ishovered){
            this.DrawScale -= Gdx.graphics.getDeltaTime() * this.DrawScale * this.DrawScale * 2;
        }
        if ( this.hb != null && this.hb.hovered && InputHelper.justClickedLeft) {
            this.ClickStart = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if(this.ClickStart){
            this.cX = InputHelper.mX;
            this.cY = InputHelper.mY;
        }


        super.update();
    }

    public void onDamaged(int dmg, boolean Effect){
        if(dmg != 0) {
            if(onDamageAbilities != null)
                for(AbstractAbility ability:onDamageAbilities) {
                    ability.Effect();
                }
            if(Effect)
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(this.cX, this.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            DamageTimer = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(DamageTimer < 0.3F) {
            sb.setColor(1.0F,0.1F,0.1F,1.0F);
            DamageTimer += Gdx.graphics.getDeltaTime();
        }else {
            sb.setColor(this.c);
        }
        sb.draw(this.img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * DrawScale, this.scale * DrawScale, 0.0F, 0, 0, 96, 96, false, false);

    }
}
