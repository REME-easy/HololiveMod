package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class HighAtkLowDef extends CustomCard {
    private static final String ID = "Hololive_HighAtkLowDef";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/HighAtkLowDef.png";
    private static final int COST = 0;
    public HighAtkLowDef() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractOrb orb:AbstractDungeon.player.orbs){
            if(orb instanceof AbstractMinion){
                int tmp = ((AbstractMinion) orb).HP;
                ((AbstractMinion) orb).ChangeHP(((AbstractMinion) orb).ATK - ((AbstractMinion) orb).HP,false);
                ((AbstractMinion) orb).ChangeATK(tmp - ((AbstractMinion) orb).ATK,false);
            }
        }
        if(upgraded){
            this.addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HighAtkLowDef();
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