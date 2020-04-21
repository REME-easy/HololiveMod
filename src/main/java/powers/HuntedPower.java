package powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HuntedPower extends AbstractPower {
    private static final String POWER_ID = "Hololive_HuntedPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public HuntedPower(AbstractCreature owner, int Amount){
        this.name =NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        this.type = PowerType.DEBUFF;
        String path128 = "img/powers/Hunted84.png";
        String path48 = "img/powers/Hunted32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void atEndOfTurn(boolean isplayer){
        if(!isplayer){
            int amt = 0;
            for(AbstractPower abstractPower:owner.powers){
                if(abstractPower.type == PowerType.DEBUFF){
                    amt ++;
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner,new DamageInfo(owner,amt * 5 * this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + " #r" + this.amount * 5 + " " + DESCRIPTIONS[1];
    }
}
