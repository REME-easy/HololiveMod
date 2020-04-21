package relics;

import basemod.abstracts.CustomRelic;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class Chaos extends CustomRelic {
    public static final String ID = "Hololive_Chaos";
    public Chaos() {
        super(ID, ImageMaster.loadImage("img/relics/Chaos.png"),
                RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }


    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        drawnCard.setCostForTurn(AbstractDungeon.cardRng.random(0,Integer.max(AbstractDungeon.player.energy.energy,1)));
        if(drawnCard.type != AbstractCard.CardType.POWER){
            int tmp;
            if(drawnCard.baseDamage > 0){
                tmp = drawnCard.baseDamage;
                drawnCard.baseDamage = AbstractDungeon.cardRng.random(0,tmp * 2);
            }
            if(drawnCard.baseBlock > 0){
                tmp = drawnCard.baseBlock;
                drawnCard.baseBlock = AbstractDungeon.cardRng.random(0,tmp * 2);
            }
            if(drawnCard.baseMagicNumber > 0){
                tmp = drawnCard.baseMagicNumber;
                drawnCard.baseMagicNumber = AbstractDungeon.cardRng.random(1,tmp * 2);
                drawnCard.magicNumber = drawnCard.baseMagicNumber;
            }
        }
        if(drawnCard instanceof AbstractSummonCard){
            int tmp = ((AbstractSummonCard) drawnCard).cardATK + ((AbstractSummonCard) drawnCard).cardHP;
            ((AbstractSummonCard) drawnCard).cardATK = AbstractDungeon.cardRng.random(1,tmp - 1);
            ((AbstractSummonCard) drawnCard).cardHP = AbstractDungeon.cardRng.random(1,tmp - 1);
        }
        drawnCard.initializeDescription();
    }


    @Override
    public void onEnterRoom(AbstractRoom room){
        if(AbstractDungeon.floorNum >= 50)
            if(!CardLibrary.getCard("Hololive_ChallengeChaos").isSeen)
                UnlockTracker.markCardAsSeen("Hololive_ChallengeChaos");
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new Chaos();
    }
}
