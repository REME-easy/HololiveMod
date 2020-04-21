package patches;

import cards.summonCard.AbstractSummonCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static patches.CardTagEnum.SUMMON_CARD;


@SpirePatch(
        clz = AbstractCard.class,
        method = "renderType"
)
public class RenderTypePatch {
    private static final UIStrings uiStrings;
    private static final String[] TEXT;
    public RenderTypePatch(){}
    static{
        uiStrings = CardCrawlGame.languagePack.getUIString("CardType");
        TEXT = uiStrings.TEXT;
    }

    public static SpireReturn<Void> Prefix(AbstractCard abstractCard_instance, SpriteBatch sb){
        if(abstractCard_instance instanceof AbstractSummonCard && abstractCard_instance.hasTag(SUMMON_CARD)){
            BitmapFont font = FontHelper.cardTypeFont;
            font.getData().setScale(abstractCard_instance.drawScale);
            FontHelper.renderRotatedText(sb, font, TEXT[0], abstractCard_instance.current_x, abstractCard_instance.current_y - 22.0F * abstractCard_instance.drawScale * Settings.scale, 0.0F, -1.0F * abstractCard_instance.drawScale * Settings.scale, abstractCard_instance.angle, false,new Color(0.35F, 0.35F, 0.35F, 1.0F));
            return SpireReturn.Return(null);
        }else{
            return  SpireReturn.Continue();
        }
    }

}
