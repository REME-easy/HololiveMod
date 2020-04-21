package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import minions.AbstractMinion;

public class SpawnFlareAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings("Hololive_CantCallMinions").EXTENDED_DESCRIPTION;
    private AbstractMinion orb;
    private AbstractMonster m;
    public SpawnFlareAction(AbstractMinion orb,AbstractMonster m){
        this.orb = orb;
        this.m = m;
        this.duration = 2.0F;
    }

    public void update() {
        if (this.duration == 2.0F) {
            int stat = 1;
            if(m != null && !m.isDeadOrEscaped() && !m.halfDead && !m.isDead)
                if(m.hasPower("Hololive_FlarePower"))
                    stat = m.getPower("Hololive_FlarePower").amount;
            AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(orb,stat,stat));
        } else {
            this.isDone = true;
        }
        this.tickDuration();
    }
}
