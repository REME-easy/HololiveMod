package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class Yo extends CustomRelic {
    public static final String ID = "Hololive_Yo";
    public Yo() {
        super(ID, ImageMaster.loadImage("img/relics/Yo.png"),
                RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onEnterRoom(AbstractRoom room){
        if(AbstractDungeon.floorNum >= 50)
            if(!CardLibrary.getCard("Hololive_ChallengeYo").isSeen)
                UnlockTracker.markCardAsSeen("Hololive_ChallengeYo");
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new Yo();
    }
}
