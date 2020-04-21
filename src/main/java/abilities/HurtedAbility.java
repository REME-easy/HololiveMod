package abilities;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HurtedAbility extends AbstractAbility {
    private static final String ID = "Hololive_Hurted";
    private static final String[] description = CardCrawlGame.languagePack.getPowerStrings(ID).DESCRIPTIONS;
    public HurtedAbility(int MagicNum){
        super(ID,MagicNum,description,false);
    }

    public void Effect(){
        AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player,MagicNum, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    public String getDescription(){
        return description[0] + " #r" + this.MagicNum + " " + description[1];
    }
}
