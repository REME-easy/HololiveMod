package actions;

import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import helper.OrbHelper;
import minions.AbstractMinion;
import powers.SingPower;

import java.util.Iterator;

public class SpawnMateAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings("Hololive_CantCallMinions").EXTENDED_DESCRIPTION;
    private AbstractMinion orb;
    private int ATK;
    private int HP;
    public SpawnMateAction(AbstractMinion orb,int ATK,int HP){
        this.orb = orb;
        this.ATK = ATK;
        this.HP = HP;
        this.duration = 2.0F;
    }

    public void update() {
        if (this.duration == 2.0F) {
            int Emptycount = 0;
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof EmptyOrbSlot)
                    Emptycount++;
            }
            if (Emptycount == 0) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, TEXT[0], 0.75F, 2.5F));
            } else {
                orb.ATK = this.ATK;
                orb.HP = this.HP;
                int index = -1;

                int plasmaCount;
                for (plasmaCount = 0; plasmaCount < AbstractDungeon.player.orbs.size(); ++plasmaCount) {
                    if (AbstractDungeon.player.orbs.get(plasmaCount) instanceof EmptyOrbSlot) {
                        index = plasmaCount;
                        break;
                    }
                }

                if (index != -1) {
                    orb.cX = AbstractDungeon.player.orbs.get(index).cX;
                    ((AbstractOrb) orb).cY = AbstractDungeon.player.orbs.get(index).cY;
                    AbstractDungeon.player.orbs.set(index, orb);
                    AbstractDungeon.player.orbs.get(index).setSlot(index, AbstractDungeon.player.maxOrbs);
                    orb.playChannelSFX();
                    Iterator var6 = AbstractDungeon.player.powers.iterator();
                    while (var6.hasNext()) {
                        AbstractPower p = (AbstractPower) var6.next();
                        p.onChannel(orb);
                    }

                    OrbHelper.CallMinionsThisBattle += 1;

                    AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orb);
                    AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orb);
                    for (AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
                        if (abstractCard instanceof AbstractSummonCard) {
                            ((AbstractSummonCard) abstractCard).onCallMinion();
                        }
                    }
                    for (AbstractCard abstractCard : AbstractDungeon.player.drawPile.group) {
                        if (abstractCard instanceof AbstractSummonCard) {
                            ((AbstractSummonCard) abstractCard).onCallMinion();
                        }
                    }
                    for (AbstractCard abstractCard : AbstractDungeon.player.discardPile.group) {
                        if (abstractCard instanceof AbstractSummonCard) {
                            ((AbstractSummonCard) abstractCard).onCallMinion();
                        }
                    }
                    for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                        if (orb instanceof AbstractMinion)
                            ((AbstractMinion) orb).onCallMinion(true);
                    }
                    for (AbstractPower power : AbstractDungeon.player.powers) {
                        if (power instanceof SingPower) {
                            ((SingPower) power).Effect(false);
                        }
                    }
                }
            }
            } else{
                this.isDone = true;
            }
            this.tickDuration();
        }
    }
