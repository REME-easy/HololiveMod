package cards.skillCard;

import basemod.abstracts.CustomCard;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.CardHelper;
import minions.AkiRosenthal;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class RecruitS extends CustomCard {
    private static final String ID = "Hololive_RecruitS";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/Recruit.png";
    private static final int COST = 0;
    public RecruitS() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.BASIC, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m);
    }

    public void use(AbstractPlayer p,AbstractMonster m){

        int akicount = 0;
        int akipluscount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                ++akipluscount;
            if (orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                ++akicount;
        }
        ArrayList<AbstractCard> cards = CardHelper.GenerateMinionGroup(upgraded);
        if(akipluscount == 0) {
            for (AbstractCard c : cards) {
                if (c instanceof AbstractSummonCard) {
                    ((AbstractSummonCard) c).Effectnum = 0;
                    ((AbstractSummonCard) c).EffectTimes += akicount;
                }
            }
                AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(cards));
        } else {
            int i;
            for(i = 0 ; i < akicount + 1 ; ++i)
                for(AbstractCard c : cards){
                    this.addToBot(new MakeTempCardInHandAction(c));
                }
        }
    }

    public void triggerOnGlowCheck() {
        int akipluscount = 0;
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                ++akipluscount;
        }
        if (akipluscount > 0) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new RecruitS();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}