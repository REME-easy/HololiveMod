package cards.optionCard;

import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Achievements;

public abstract class AbstractCombinatiionCard extends CustomCard {
    public AbstractCombinatiionCard(String ID,String NAME,String IMG_PATH,String DESCRIPTION){
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION,
                SKILL, Achievements,
                CardRarity.SPECIAL, CardTarget.ENEMY);
    }

    public void CombinationEffect(){}
}
