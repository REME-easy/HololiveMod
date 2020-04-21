package cards.skillCard;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import helper.OrbHelper;
import minions.AbstractMinion;
import minions.AkiRosenthal;

import java.util.ArrayList;

public class AbstractChooseCard extends CustomCard {
    public final String ID;
    public final CardStrings cardStrings;
    public AbstractChooseCard(String ID,String NAME,String IMG_PATH,int COST,String DESCRIPTION,CardType CARDTYPE,CardColor CARDCOLOR,CardRarity CARDRARITY,CardTarget CARDTARGET){
        super(ID,NAME,IMG_PATH,COST,DESCRIPTION,CARDTYPE,CARDCOLOR,CARDRARITY,CARDTARGET);
        this.ID = ID;
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public void upgrade(){}

    public AbstractCard makeCopy(){
        return null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        int akicount = 0;
        int akipluscount = 0;
        for (AbstractOrb orb : p.orbs) {
            if (orb instanceof AbstractMinion)
                ++count;
            if (orb instanceof AkiRosenthal && ((AkiRosenthal) orb).upgraded)
                ++akipluscount;
            else if(orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                ++akicount;
        }
        if(count > 0){
            if (count == 1 || akipluscount > 0) {
                int i;
                for(i = 0;i <= akicount ;++i)
                    for (AbstractOrb orb : p.orbs) {
                        if (orb instanceof AbstractMinion) {
                            ChooseEffectAll(orb);
                        }
                    }
            } else{
                ArrayList<AbstractCard> choices = OrbHelper.ChooseOneMinionGroup(ID, this.magicNumber);
                if(choices != null)
                    AbstractDungeon.actionManager.addToBottom(new ChooseOneAction(choices));
            }
        }
        }


    public void ChooseEffectAll(AbstractOrb orb){

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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        int count = 0;
        for(AbstractOrb orb:p.orbs){
            if(orb instanceof AbstractMinion)
                ++count;
        }
        if (!canUse) {
            return false;
        } else if (count <= 0) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            return canUse;
        }
    }
}
