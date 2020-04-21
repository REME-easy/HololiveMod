package minions;


import abilities.AbstractAbility;
import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import helper.OrbHelper;
import powers.FlarePower;

import java.util.ArrayList;

public abstract class AbstractMinion extends CustomOrb {
    public int HP = 0;
    public int ATK = 0;
    public boolean Taunt = false;
    public boolean upgraded = false;
    public boolean isdead = false;
    public ArrayList<AbstractAbility> onDamageAbilities = new ArrayList<>();
    public ArrayList<AbstractAbility> onEvokeAbilities = new ArrayList<>();
    public ArrayList<AbstractAbility> onAttackAbilities  =new ArrayList<>();
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    private float DamageTimer = 0.3F;
    private float HPFontScale;
    private float ATKFontScale;
    private float TriggerScale = 1.0F;
    private static String Atk;
    private static String Hp;
    static {
        if(Settings.language == Settings.GameLanguage.ZHS){
            Atk = "攻击：";
            Hp = "生命：";
        } else {
            Atk = "ATK:";
            Hp = "     HP:";
        }
    }
    private static Texture TauntImg = ImageMaster.loadImage("img/UI/students/TauntImg.png");
    public AbstractMinion(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, int HP, String IMG_PATH, String DESCRIPTION, String UPGRADE_DESCRIPTION){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,null,null,IMG_PATH);
        this.HP = HP;
        this.DESCRIPTION = DESCRIPTION;
        this.UPGRADE_DESCRIPTION = UPGRADE_DESCRIPTION;
        this.HPFontScale = this.fontScale;
        this.ATKFontScale = this.fontScale;
        this.updateDescription();
    }

    public void onEvoke(){

        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
            for(AbstractPower p:m.powers){
                if(p instanceof FlarePower && isdead)
                    ((FlarePower) p).Effect();
            }
        }
        if(onEvokeAbilities != null) {
            for(AbstractAbility ability:onEvokeAbilities){
                ability.Effect();
            }
        }
        if(isdead){
            OrbHelper.DeadMinionsThisBattle.add(this);
            for(AbstractOrb orb:AbstractDungeon.player.orbs){
                if(orb instanceof OkamiMio){
                    if(!((OkamiMio) orb).upgraded){
                        ((OkamiMio) orb).ChangeATK(1,false);
                        ((OkamiMio) orb).ChangeHP(2,false);
                    } else {
                        ((OkamiMio) orb).ChangeATK(2,false);
                        ((OkamiMio) orb).ChangeHP(3,false);
                    }
                }
            }
        }
        if(AbstractDungeon.player.hasRelic("Hololive_TheOnlyOne")){
            AbstractCard card = OrbHelper.GetCardInstance(this);
            if (card != null){
                AbstractDungeon.player.masterDeck.removeCard(card.cardID);
            }
        }
    }

    public void onCardUse(AbstractCard c){

    }

    public void onCardUseEffect(){}

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

    public void ChangeHP(int DeltaHP,boolean isDamage){
        this.HP += DeltaHP;
        if(DeltaHP > 0 && HPFontScale < 4.0F)
            HPFontScale += 2.3F;
    }

    public void ChangeATK(int DeltaATK,boolean isDamage){
        this.ATK += DeltaATK;
        if(DeltaATK > 0 && ATKFontScale < 4.0F)
            ATKFontScale += 2.3F;
    }

    public void onCallMinion(boolean Left){}


    public void Trigger(){
        if(TriggerScale < 2.5F)
            TriggerScale += 0.5F;
    }

    public void onEndOfTurnEffect(){}


    public void AttackEffect(){
        if(onAttackAbilities != null)
            for(AbstractAbility ability:onAttackAbilities) {
                ability.Effect();
            }
        for(AbstractOrb orb:AbstractDungeon.player.orbs){
            if(orb instanceof AbstractMinion)
                if(orb instanceof SakuraMiko){
                    AbstractDungeon.actionManager.addToBottom(new GainGoldAction(1));
                }
        }
    }

    public void Attack(AbstractGameAction.AttackEffect effect){
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead)
            Attack(m,effect);
    }

    public void Attack(AbstractMonster m, AbstractGameAction.AttackEffect effect){
        float dmg = ATK;
        if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead)
            for(AbstractPower p:m.powers){
                dmg = p.atDamageReceive(dmg, DamageInfo.DamageType.NORMAL);
            }
        for(AbstractPower p:AbstractDungeon.player.powers){
            if(!(p instanceof VigorPower) && !(p instanceof PenNibPower))
                dmg = p.atDamageGive(dmg, DamageInfo.DamageType.NORMAL);
        }
        if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead && !AbstractDungeon.player.hasRelic("Hololive_Yo"))
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(AbstractDungeon.player,(int)dmg, DamageInfo.DamageType.NORMAL),effect));
    }


    public void render(SpriteBatch sb){
        if(DamageTimer < 0.3F) {
            sb.setColor(1.0F,0.1F,0.1F,1.0F);
            DamageTimer += Gdx.graphics.getDeltaTime();
        }else {
            sb.setColor(this.c);
        }
        if(HPFontScale > 0.7F){
            HPFontScale -= Gdx.graphics.getDeltaTime() * HPFontScale * 2;
        }
        if(ATKFontScale > 0.7F){
            ATKFontScale -= Gdx.graphics.getDeltaTime() * ATKFontScale * 2;
        }
        if(TriggerScale > 1.0F){
            TriggerScale -= Gdx.graphics.getDeltaTime() * TriggerScale * 1.5F;
        }
        sb.draw(this.img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * TriggerScale, this.scale * TriggerScale, 0.0F, 0, 0, 96, 96, false, false);
        if(this.Taunt){
            sb.draw(TauntImg, this.cX - 75.0F + this.bobEffect.y / 4.0F, this.cY - 75.0F + this.bobEffect.y / 4.0F, 75.0F, 75.0F, 150.0F, 150.0F, this.scale * TriggerScale, this.scale * TriggerScale, 0.0F, 0, 0, 150, 150, false, false);
        }
        renderText(sb);
        updateDescription();
        hb.render(sb);
    }

    public void updateDescription() {
        this.applyFocus();
        String abilitydescription = "";
        if(this.onDamageAbilities != null)
            for(AbstractAbility ability:this.onDamageAbilities){
                abilitydescription = " NL " + abilitydescription.concat(ability.getDescription());
            }
        if(this.onEvokeAbilities != null)
            for(AbstractAbility ability:this.onEvokeAbilities){
                abilitydescription = " NL " + abilitydescription.concat(ability.getDescription());
            }
        if(this.onAttackAbilities != null)
            for(AbstractAbility ability:this.onAttackAbilities){
                abilitydescription = " NL " + abilitydescription.concat(ability.getDescription());
            }
        this.description = Atk + this.ATK + Hp + this.HP + " NL " + ((!upgraded)?DESCRIPTION:UPGRADE_DESCRIPTION) + abilitydescription;

    }

    public void renderText(SpriteBatch sb){
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.ATK), this.cX - NUM_X_OFFSET * 1.8F, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 30.0F,new Color(1.0F,1.0F,0.4F,1.0F), this.ATKFontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "/", this.cX , this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 30.0F,new Color(1.0F,1.0F,1.0F,1.0F), this.fontScale);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.HP), this.cX + NUM_X_OFFSET * 1.8F, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 30.0F,new Color(1.0F,0.5F,0.5F,1.0F), this.HPFontScale);
        if(this.upgraded)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, "+", this.cX , this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + 70.0F * Settings.scale,new Color(1.0F,1.0F,1.0F,1.0F), this.fontScale * 1.6F);

    }

}
