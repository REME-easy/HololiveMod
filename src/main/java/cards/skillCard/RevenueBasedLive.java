package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class RevenueBasedLive extends CustomCard {
    private static final String ID = "Hololive_RevenueBasedLive";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/RevenueBasedLive.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    public RevenueBasedLive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.COMMON, CardTarget.NONE);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        AbstractDungeon.effectList.add(new RainingGoldEffect(200));
        for(i = 0 ; i < AbstractDungeon.getMonsters().monsters.size() ; ++i){
            if(!AbstractDungeon.getMonsters().monsters.get(i).isDead){
                for (AbstractOrb orb:AbstractDungeon.player.orbs) {
                    if(orb instanceof AbstractMinion){
                        AbstractDungeon.actionManager.addToBottom(new GainGoldAction(1));
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.getMonsters().monsters.get(i), "AWSL", 0.1F, 2.5F));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION;
        int ct = 0;
        int mct = 0;
        for (AbstractOrb orb:AbstractDungeon.player.orbs) {
            if(orb instanceof AbstractMinion){
                ++ct;
            }
        }
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
            if(!m.isDead){
                ++mct;
            }
        }
        if(AbstractDungeon.getMonsters().monsters != null)
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + ct * mct   + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new RevenueBasedLive();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}