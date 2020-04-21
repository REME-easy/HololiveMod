package patches;

import Char.HololiveCharacter;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

@SpirePatch(
        clz = AbstractOrb.class,
        method = "setSlot",
        paramtypez = {int.class, int.class}
)
public class SetSlotPatch {
    public SetSlotPatch(){
    }


    public static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {
        if (AbstractDungeon.player instanceof HololiveCharacter) {
            if(maxOrbs > 1){
                abstractOrb_instance.tX = AbstractDungeon.player.drawX + (700.0F - 25.0F * maxOrbs) * Settings.scale - slotNum * (125.0F - (maxOrbs - 7) * 10.0F) * Settings.scale;
                abstractOrb_instance.tY = AbstractDungeon.player.drawY + 350.0F * Settings.scale;
                abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            } else {
                abstractOrb_instance.tX = AbstractDungeon.player.drawX ;
                abstractOrb_instance.tY = AbstractDungeon.player.drawY + 350.0F * Settings.scale;
                abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);

            }

            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }
}
