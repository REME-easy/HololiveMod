package abilities;

import actions.SpawnMateAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import minions.Lamb;

public class LambAbility extends AbstractAbility {
    private static final String ID = "Hololive_Lamb";
    private static final String[] description = CardCrawlGame.languagePack.getPowerStrings(ID).DESCRIPTIONS;
    public LambAbility(int MagicNum,boolean upgraded){
        super(ID,MagicNum,description,upgraded);
    }

    public void Effect(){
        AbstractDungeon.actionManager.addToTop(new SpawnMateAction(new Lamb(this.upgraded),MagicNum,MagicNum));
    }

    public String getDescription(){
        return description[0] + " #g" + this.MagicNum + " " + description[1] + " #g" + this.MagicNum + " " + description[2];
    }
}
