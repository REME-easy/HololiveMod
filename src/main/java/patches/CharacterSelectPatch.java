package patches;


import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import static patches.MyPlayerClassEnum.Hololive_HololiveCharacter;


public class CharacterSelectPatch {
    private static Hitbox CombLeftHb;
    private static Hitbox CombRightHb;
    public static int DeckIndex = -1;
    private static final String[] DeckName = CardCrawlGame.languagePack.getUIString("DeckName").TEXT;
    private static String Intro;
    static {
        if(Settings.language == Settings.GameLanguage.ZHS){
            Intro = "选择初始卡组";
        } else if(Settings.language == Settings.GameLanguage.JPN) {
            Intro = "初期デッキを選択";
        } else {
            Intro = "Select initial deck";
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "update"
    )
    public static class NewButtonPatch{
        public NewButtonPatch(){}

        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst){
            if(CardCrawlGame.chosenCharacter == Hololive_HololiveCharacter){
                if(CombLeftHb != null && CombRightHb != null) {
                    CombLeftHb.move((float) Settings.WIDTH / 2.0F - 200.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F);
                    CombRightHb.move((float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F);
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "updateAscensionToggle"
    )
    public static class UpdateButtonPatch{
        public UpdateButtonPatch(){}

        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst){
            if(CardCrawlGame.chosenCharacter == Hololive_HololiveCharacter){
                CombLeftHb.update();
                CombRightHb.update();
                if(CombLeftHb.clicked){
                    CombLeftHb.clicked = false;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    if(DeckIndex == 0){
                        DeckIndex = DeckName.length - 1;
                    } else {
                        --DeckIndex;
                    }
                }

                if(CombRightHb.clicked){
                    CombRightHb.clicked = false;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                    if(DeckIndex == DeckName.length - 1){
                        DeckIndex = 0;
                    } else {
                        ++DeckIndex;
                    }
                }

                if (InputHelper.justClickedLeft) {
                    if (CombLeftHb.hovered) {
                        CombLeftHb.clickStarted = true;
                    }
                    if (CombRightHb.hovered) {
                        CombRightHb.clickStarted = true;
                    }
                }
            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "renderAscensionMode"
    )
    public static class RenderButtonPatch{
        public RenderButtonPatch(){}

        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst, SpriteBatch sb){
            if(CardCrawlGame.chosenCharacter == Hololive_HololiveCharacter && (Boolean)( ReflectionHacks.getPrivate(_inst,CharacterSelectScreen.class,"anySelected"))){
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont,Intro , (float)Settings.WIDTH / 2.0F , (float)Settings.HEIGHT / 2.0F + 75.0F, Color.WHITE,1.5F);
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont,DeckName[DeckIndex] , (float)Settings.WIDTH / 2.0F , (float)Settings.HEIGHT / 2.0F, Settings.GOLD_COLOR);
                sb.draw(ImageMaster.CF_LEFT_ARROW, CombLeftHb.cX - 24.0F, CombLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                sb.draw(ImageMaster.CF_RIGHT_ARROW, CombRightHb.cX - 24.0F, CombRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                if (CombLeftHb.hovered) {
                    sb.setColor(Color.LIGHT_GRAY);
                } else {
                    sb.setColor(Color.WHITE);
                }
                if (CombRightHb.hovered) {
                    sb.setColor(Color.LIGHT_GRAY);
                } else {
                    sb.setColor(Color.WHITE);
                }
                CombLeftHb.render(sb);
                CombRightHb.render(sb);
            }
            return SpireReturn.Continue();
        }
    }





    @SpirePatch(
            clz = CharacterSelectScreen.class,
            method = "initialize"
    )
    public static class AddButtonPatch{
        public AddButtonPatch(){}

        public static SpireReturn<Void> Prefix(CharacterSelectScreen _inst){
            DeckIndex = 0;
            CombLeftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
            CombRightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
            return SpireReturn.Continue();
        }
    }

}
