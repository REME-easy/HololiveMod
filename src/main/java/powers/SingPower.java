package powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import minions.AbstractMinion;

public class SingPower extends AbstractPower {
    private static final String POWER_ID = "Hololive_SingPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public SingPower(AbstractCreature owner, int Amount){
        this.name =NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        this.type = PowerType.BUFF;
        String path128 = "img/powers/Sing84.png";
        String path48 = "img/powers/Sing32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void Effect(boolean Left){
        if(Left){
            if(AbstractDungeon.player.orbs.get(0) instanceof AbstractMinion){
                this.flash();
                AbstractMinion m = (AbstractMinion) AbstractDungeon.player.orbs.get(0);
                m.ChangeATK(this.amount,false);
                m.ChangeHP(this.amount,false);
            }
        } else {
            int i;
            for(i = AbstractDungeon.player.orbs.size() - 1 ; i > -1 ; --i){
                if(AbstractDungeon.player.orbs.get(i) instanceof AbstractMinion){
                    this.flash();
                    AbstractMinion m = (AbstractMinion) AbstractDungeon.player.orbs.get(i);
                    m.ChangeATK(this.amount,false);
                    m.ChangeHP(this.amount,false);
                    break;
                }
            }
        }

    }


    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + " #g" + this.amount + " " + DESCRIPTIONS[1];
    }
}
