package cards.attackCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import minions.AbstractMinion;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class MaximGun extends CustomCard {
    private static final String ID = "Hololive_MaximGun";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/attackcards/MaximGun.png";
    private static final int COST = 1;
    private static final int MAGIC_NUM = 3;
    private static final int UPGRADE_MAGIC_NUM = 1;

    public MaximGun() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                ATTACK, Hololive_BLUE,
                CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = MAGIC_NUM;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMinion> abstractMinions = new ArrayList<>();
        for(AbstractOrb abstractOrb:AbstractDungeon.player.orbs){
            if(abstractOrb instanceof AbstractMinion){
                abstractMinions.add(((AbstractMinion)abstractOrb));
            }
        }
        if(abstractMinions.size() != 0) {
            Random random = new Random();
            int i;
            AbstractMinion abstractMinion;
            for (i = 0; i < magicNumber; ++i) {
                abstractMinion = abstractMinions.get(random.random(0, abstractMinions.size() - 1));
                abstractMinion.onEndOfTurn();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MaximGun();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}
