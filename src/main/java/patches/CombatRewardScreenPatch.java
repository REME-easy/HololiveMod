package patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import relics.TheStar;

public class CombatRewardScreenPatch {



    @SpirePatch(
            clz = CombatRewardScreen.class,
            method = "setupItemReward"
    )
    public static class SetUpItemPatch{
        public SetUpItemPatch(){}

        @SpireInsertPatch(
                rloc = 53
        )
        public static void Insert(CombatRewardScreen _inst){
            if(TheStar.CombinationIndex[15]){
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoom){
                    RewardItem cardReward = new RewardItem();
                    if (cardReward.cards.size() > 0) {
                        _inst.rewards.add(cardReward);
                    }
                }
            }
        }
    }
}
