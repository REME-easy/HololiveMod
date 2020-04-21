package minions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

public class TokoyamiTowa extends AbstractMinion {
    private static final String ID = "Hololive_TokoyamiTowa";
    private static final OrbStrings cardStrings = CardCrawlGame.languagePack.getOrbString(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String[] DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/Orbs/TokoyamiTowa.png";
    private static final int basePassiveAmount = 9;
    private static final int baseEvokeAmount = 3;
    private static final int originalHP = 10;
    private static final int originalATK = 10;
    public TokoyamiTowa(boolean upgraded){
        super(ID,NAME,basePassiveAmount,baseEvokeAmount,originalHP,IMG_PATH,DESCRIPTION[0],DESCRIPTION[1]);
        this.upgraded = upgraded;
        this.ATK = originalATK;
    }

    public AbstractMinion makeCopy(){
        return new TokoyamiTowa(this.upgraded);
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
        int akicount = 0;
        int akipluscount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                ++akipluscount;
            else if(orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                ++akicount;
        }
        int j;
        for(j = 0 ; j < akicount + 1 ; ++j) {
            if (akipluscount > 0) {
                Attack(AbstractGameAction.AttackEffect.FIRE);
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, ATK));
            } else {
                Random random = new Random();
                int i = random.random(0, 1);
                switch (i) {
                    case 0:
                        Attack(AbstractGameAction.AttackEffect.FIRE);
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, ATK));
                        break;
                    default:break;
                }
            }
        }
        Trigger();
        super.AttackEffect();
    }


    public void playChannelSFX(){
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
}
