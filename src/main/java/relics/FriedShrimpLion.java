package relics;

import basemod.abstracts.CustomRelic;
import cards.summonCard.CallNatsuiroMatsuri;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FriedShrimpLion extends CustomRelic {
    public static final String ID = "Hololive_FriedShrimpLion";
    public FriedShrimpLion() {
        super(ID, ImageMaster.loadImage("img/relics/FriedShrimpLion.png"),
                RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void atBattleStart() {
        boolean HasMatsuri = false;
        for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if(c instanceof CallNatsuiroMatsuri){
                HasMatsuri = true;
                break;
            }
        }
        if(!HasMatsuri){
            this.addToBot(new MakeTempCardInHandAction(new CallNatsuiroMatsuri()));
        }
    }


    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new FriedShrimpLion();
    }
}
