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
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class AWSL extends CustomCard {
    private static final String ID = "Hololive_AWSL";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/AWSL.png";
    private static final int COST = -2;
    public AWSL() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL,Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new AWSL();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade(){

    }

}