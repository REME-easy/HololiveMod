package abilities;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractAbility {
    public String ID;
    public int MagicNum;
    public String[] description;
    public boolean upgraded;
    public AbstractAbility(String ID,int MagicNum,String[] description,boolean upgraded){
        this.ID = ID;
        this.MagicNum = MagicNum;
        this.description = description;
        this.upgraded = upgraded;
    }

    public abstract void Effect();

    public abstract String getDescription();
}
