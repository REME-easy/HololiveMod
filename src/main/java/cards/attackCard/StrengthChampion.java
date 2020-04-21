package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.OrbHelper;
import minions.AbstractMinion;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class StrengthChampion extends CustomCard {
    private static final String ID = "Hololive_StrengthChampion";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/StrengthChampion.png";
    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 1;
    public StrengthChampion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.COMMON, CardTarget.NONE);
        this.baseBlock = this.block = BLOCK;
        this.baseDamage = this.damage = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for (AbstractOrb orb : p.orbs) {
            if (orb instanceof AbstractMinion)
                ++count;
        }
        if(count > 0)
            if(count == 1){
                for (AbstractOrb orb : p.orbs) {
                    if (orb instanceof AbstractMinion){
                        ((AbstractMinion) orb).ChangeATK(damage,false);
                        ((AbstractMinion) orb).ChangeHP(block,false);
                        break;
                    }
                }
            } else {
                ArrayList<AbstractCard> choices = OrbHelper.ChooseOneMinionGroup(ID,damage,block);
                if(choices != null)
                    AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(choices));
            }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        int count = 0;
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof AbstractMinion)
                ++count;
        }
        if (!canUse) {
            return false;
        } else if (count <= 0) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new StrengthChampion();
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(UPGRADE_DAMAGE );
        this.upgradeBlock(UPGRADE_BLOCK );
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }
}