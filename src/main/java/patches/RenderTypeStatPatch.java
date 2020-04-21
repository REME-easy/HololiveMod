package patches;


import cards.skillCard.BeCareful;
import cards.summonCard.AbstractSummonCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
public class RenderTypeStatPatch{
    private static final Texture atkimg = ImageMaster.loadImage("img/UI/students/attacktexture_164.png");
    private static final Texture hpimg = ImageMaster.loadImage("img/UI/students/healthtexture_164.png");
    private static final Texture Danger = ImageMaster.loadImage("img/UI/students/Danger.png");
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderPortraitFrame"
    )
    public static class renderTypeStatPatch {

        public renderTypeStatPatch() {
        }

        public static SpireReturn<Void> Prefix(AbstractCard _inst, SpriteBatch sb, float x, float y) {
            if (_inst instanceof AbstractSummonCard) {
                sb.draw(atkimg, _inst.current_x - (170.0F + 120.0F * (_inst.drawScale - 0.7F)) * Settings.scale, _inst.current_y - (178.0F + 193.0F * (_inst.drawScale - 0.7F)) * Settings.scale, 82.0F * Settings.scale, 82.0F * Settings.scale, 164.0F * Settings.scale, 164.0F * Settings.scale, _inst.drawScale * Settings.scale, _inst.drawScale * Settings.scale, _inst.angle, 0, 0, 164, 164, false, false);
                sb.draw(hpimg, _inst.current_x + (22.0F + 120.0F * (_inst.drawScale - 0.7F)) * Settings.scale, _inst.current_y - (178.0F + 193.0F * (_inst.drawScale - 0.7F)) * Settings.scale, 82.0F * Settings.scale, 82.0F * Settings.scale, 164.0F * Settings.scale, 164.0F * Settings.scale, _inst.drawScale * Settings.scale, _inst.drawScale * Settings.scale, _inst.angle, 0, 0, 164, 164, false, false);
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSummonCard) _inst).cardATK), _inst.current_x - (86.0F + 120.0F * (_inst.drawScale - 0.7F)) * Settings.scale, _inst.current_y - (105.0F + 193.0F * (_inst.drawScale - 0.7F)) * Settings.scale, new Color(1.0F, 1.0F, 1.0F, 1.0F), 1.6F * _inst.drawScale);
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(((AbstractSummonCard) _inst).cardHP), _inst.current_x + (106.0F + 120.0F * (_inst.drawScale - 0.7F)) * Settings.scale, _inst.current_y - (105.0F + 193.0F * (_inst.drawScale - 0.7F)) * Settings.scale, new Color(1.0F, 1.0F, 1.0F, 1.0F), 1.6F * _inst.drawScale);
            }
            if (_inst instanceof BeCareful) {
                sb.draw(Danger, _inst.current_x - 150.0F * Settings.scale, _inst.current_y - 210.0F * Settings.scale, 150.0F * Settings.scale, 210.0F * Settings.scale, 300.0F * Settings.scale, 420.0F * Settings.scale, _inst.drawScale * Settings.scale, _inst.drawScale * Settings.scale, _inst.angle, 0, 0, 300, 420, false, false);
            }

            return SpireReturn.Continue();
        }
    }
}

