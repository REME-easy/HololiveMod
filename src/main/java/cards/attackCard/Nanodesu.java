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

public class Nanodesu extends CustomCard {
    private static final String ID = "Hololive_Nanodesu";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/Nanodesu.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    public Nanodesu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof AbstractMinion)
                ++count;
        }
        if(count > 0){
            ArrayList<AbstractCard> choices = OrbHelper.ChooseOneMinionGroup(ID,false);
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
        return new Nanodesu();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}