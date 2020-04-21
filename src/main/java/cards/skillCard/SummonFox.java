package cards.skillCard;

import actions.SpawnMateAction;
import basemod.abstracts.CustomCard;
import cards.summonCard.CallUfubuki;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.CardHelper;
import minions.ShirakamiFubuki;
import minions.Ufubuki;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class SummonFox extends CustomCard {
    private static final String ID = "Hololive_SummonFox";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/SummonFox.png";
    private static final int COST = 1;
    public SummonFox() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
            this.cardsToPreview = new CallUfubuki();
            this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean HasFubuki = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof ShirakamiFubuki) {
                HasFubuki = true;
                break;
            }
        }
        if(!HasFubuki)
            AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new ShirakamiFubuki(upgraded), CardHelper.isBalance?2:4,CardHelper.isBalance?3:6));
        else
            AbstractDungeon.actionManager.addToBottom(new SpawnMateAction(new Ufubuki(upgraded),CardHelper.isBalance?2:4,CardHelper.isBalance?3:6));
    }

    public void triggerOnGlowCheck() {
        boolean HasFubuki = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof ShirakamiFubuki) {
                HasFubuki = true;
                break;
            }
        }
        if (HasFubuki) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new SummonFox();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }
}