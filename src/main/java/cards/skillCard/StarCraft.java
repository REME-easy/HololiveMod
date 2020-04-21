package cards.skillCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AbstractMinion;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;
import static patches.CardTagEnum.CHOOSE_CARD;

public class StarCraft extends AbstractChooseCard {
    private static final String ID = "Hololive_StarCraft";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/StarCraft.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    public StarCraft() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.tags.add(CHOOSE_CARD);
        this.exhaust = true;
    }


    //@Override
    //public void use(AbstractPlayer p, AbstractMonster m) {
    //    int count = 0;
    //    for(AbstractOrb orb:p.orbs){
    //        if(orb instanceof AbstractMinion)
    //            ++count;
    //    }
    //    if(count == 1){
    //        for(AbstractOrb orb:p.orbs){
    //            if(orb instanceof AbstractMinion) {
    //                ((AbstractMinion) orb).ChangeHP(this.magicNumber);
    //                ((AbstractMinion) orb).Taunt = true;
    //            }
    //        }
    //    }else {
    //        ArrayList<AbstractCard> choices = OrbHelper.ChooseOneMinionGroup(ID, this.magicNumber);
    //        AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(choices));
    //    }
    //}

    //public boolean canUse(AbstractPlayer p,AbstractMonster m){
    //    boolean canUse = super.canUse(p, m);
    //    int count = 0;
    //    for(AbstractOrb orb:p.orbs){
    //        if(orb instanceof AbstractMinion)
    //            ++count;
    //    }
    //    if (!canUse) {
    //        return false;
    //    } else if (count <= 0) {
    //        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
    //        return false;
    //    } else {
    //        return canUse;
    //    }
    //}

    @Override
    public void ChooseEffectAll(AbstractOrb orb){
        ((AbstractMinion) orb).ChangeHP(((AbstractMinion) orb).HP,false);
    }




    @Override
    public AbstractCard makeCopy() {
        return new StarCraft();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);

        }
    }
}