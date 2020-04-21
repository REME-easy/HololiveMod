package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.Roboco;

import static patches.CardTagEnum.PROGRAM_CARD;


public class AbstractProgramCard extends CustomCard {
    public AbstractProgramCard(String ID,String NAME,String IMG_PATH,int COST,String DESCRIPTION,CardType CARDTYPE,CardColor CARDCOLOR,CardRarity CARDRARITY,CardTarget CARDTARGET){
        super(ID,NAME,IMG_PATH,COST,DESCRIPTION,CARDTYPE,CARDCOLOR,CARDRARITY,CARDTARGET);
        this.tags.add(PROGRAM_CARD);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public boolean HasRoboco(){
        for(AbstractOrb orb: AbstractDungeon.player.orbs){
            if(orb instanceof Roboco){
                return true;
            }
        }
        return false;
    }

    public void Effect(){}


    public void triggerWhenDrawn() {
        Effect();
        this.addToBot(new WaitAction(0.1F));
        this.addToBot(new ExhaustSpecificCardAction(this,AbstractDungeon.player.hand));
            this.addToBot(new DrawCardAction(1));
    }


    public void upgrade(){}
}
