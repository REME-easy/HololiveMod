package relics;

import basemod.abstracts.CustomRelic;
import cards.summonCard.AbstractSummonCard;
import cards.summonCard.CallOkamiMio;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class Kindergarten extends CustomRelic {
    public static final String ID = "Hololive_Kindergarten";
    public Kindergarten() {
        super(ID, ImageMaster.loadImage("img/relics/Kindergarten.png"),
                RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }


    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(drawnCard instanceof AbstractSummonCard && !(drawnCard instanceof CallOkamiMio)){
            drawnCard.setCostForTurn(1);
            ((AbstractSummonCard) drawnCard).cardATK = 1;
            ((AbstractSummonCard) drawnCard).cardHP = 1;
        }
    }


    @Override
    public void onEnterRoom(AbstractRoom room){
        if(AbstractDungeon.floorNum >= 50)
            if(!CardLibrary.getCard("Hololive_ChallengeKindergarten").isSeen)
                UnlockTracker.markCardAsSeen("Hololive_ChallengeKindergarten");
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new Kindergarten();
    }
}
