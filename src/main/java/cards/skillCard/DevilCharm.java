package cards.skillCard;

import actions.SpawnMateAction;
import basemod.abstracts.CustomCard;
import cards.summonCard.CallTokoyamiTowa;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.CardHelper;
import minions.AkiRosenthal;
import minions.TokoyamiTowa;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class DevilCharm extends CustomCard {
    private static final String ID = "Hololive_DevilCharm";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/DevilCharm.png";
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = -6;
    public DevilCharm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.UNCOMMON, CardTarget.NONE);
            this.cardsToPreview = new CallTokoyamiTowa();
            this.baseBlock = this.block = BLOCK;
        if(CardHelper.isBalance){
            this.baseBlock = this.block = BLOCK - 4;
        }
            this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int akicount = 0;
        int akipluscount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                ++akipluscount;
            else if(orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                ++akicount;
        }
        int j;
        for(j = 0 ; j < akicount + 1 ; ++j) {
            if (akipluscount > 0) {
                this.addToBot(new SpawnMateAction(new TokoyamiTowa(false),CardHelper.isBalance?10:6,CardHelper.isBalance?10:6));
                this.addToBot(new GainBlockAction(p,block));
            } else {
                int i = AbstractDungeon.cardRandomRng.random(0, 1);
                switch (i) {
                    case 0:
                        this.addToBot(new SpawnMateAction(new TokoyamiTowa(false),CardHelper.isBalance?10:6,CardHelper.isBalance?10:6));
                        break;
                    case 1:
                        this.addToBot(new GainBlockAction(p,block));
                        break;
                        default:break;
                }
            }
        }
    }

    public void triggerOnGlowCheck() {
        boolean akiplus = false;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                akiplus = true;
        }
        if (akiplus) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new DevilCharm();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.upgradeBlock(UPGRADE_BLOCK);
        }
    }
}