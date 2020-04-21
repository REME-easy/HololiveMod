package patches;

import cards.summonCard.AbstractSummonCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;


@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "renderCardTypeText"
)
public class RenderTypePPatch {
    private static final UIStrings uiStrings;
    private static final String[] TEXT;
    public RenderTypePPatch(){}
    static{
        uiStrings = CardCrawlGame.languagePack.getUIString("CardType");
        TEXT = uiStrings.TEXT;
    }

    public static SpireReturn<Void> Prefix(SingleCardViewPopup singleCardViewPopup_instance, SpriteBatch sb)throws NoSuchFieldException, IllegalAccessException {
        Field card = singleCardViewPopup_instance.getClass().getDeclaredField("card");
        card.setAccessible(true);
        if(card.get(singleCardViewPopup_instance) instanceof AbstractSummonCard){
            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTypeFont, TEXT[0], (float)Settings.WIDTH / 2.0F + 3.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F - 40.0F * Settings.scale, new Color(0.35F, 0.35F, 0.35F, 1.0F));
            return SpireReturn.Return(null);
        }else{
            return  SpireReturn.Continue();
        }
    }

}
