package cards.skillCard;

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
import minions.MurasakiShion;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class Teleport extends CustomCard {
    private static final String ID = "Hololive_Teleport";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/Teleport.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    public Teleport() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        boolean HasShion = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if(orb instanceof AbstractMinion){
                if (orb instanceof MurasakiShion) {
                    HasShion = true;
                    break;
                }
            }
        }
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AbstractMinion) {
                ++count;
            }
        }
        if(count > 0) {
            ArrayList<AbstractCard> choices = OrbHelper.ChooseOneMinionGroup(ID, HasShion);
            if (choices != null)
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

    public void triggerOnGlowCheck() {
        boolean HasShion = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof MurasakiShion) {
                HasShion = true;
                break;
            }
        }
        if (HasShion) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Teleport();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}