package helper;

import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import minions.AbstractMinion;
import minions.AkiRosenthal;

import java.util.ArrayList;

public class OrbHelper {
    public static int CallMinionsThisBattle = 0;
    public static ArrayList<AbstractMinion> DeadMinionsThisBattle = new ArrayList<>();
    public OrbHelper(){}

    public static ArrayList<AbstractCard> ChooseOneMinionGroup(String id,int magicnum1){
        ArrayList<AbstractCard> abstractCards = new ArrayList<>();
        int i;
        for(i = AbstractDungeon.player.orbs.size() - 1 ; i > -1 ; --i){
            if(AbstractDungeon.player.orbs.get(i) instanceof AbstractMinion){
                AbstractSummonCard card = GetCardInstance(i);
                if(((AbstractMinion) AbstractDungeon.player.orbs.get(i)).upgraded && card != null)
                    card.upgrade();
                if(card != null) {
                    card.cardATK = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).ATK;
                    card.cardHP = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).HP;
                }
                for(AbstractOrb orb:AbstractDungeon.player.orbs){
                    if(orb instanceof AbstractMinion)
                        if(orb instanceof AkiRosenthal && !((AkiRosenthal) orb).upgraded)
                            if(card != null)
                                card.EffectTimes += 1;
                }
                if(card != null) {
                    switch (id) {
                        case "Hololive_ILoveYou":
                            card.MagicNum = magicnum1;
                            card.Index = i;
                            card.Effectnum = 1;
                            break;
                        case "Hololive_CocoNews":
                            card.MagicNum = magicnum1;
                            card.Index = i;
                            card.Effectnum = 2;
                            break;
                        case "Hololive_Recruit":
                            card.Effectnum = 0;
                            break;
                        case "Hololive_RecruitS":
                            card.Effectnum = 0;
                            break;
                        case "Hololive_StarCraft":
                            card.Index = i;
                            card.Effectnum = 5;
                            break;
                        case "Hololive_Psychosis":
                            card.MagicNum = magicnum1;
                            card.Index = i;
                            card.Effectnum = 6;
                        default:
                    }
                }
                abstractCards.add(card);
            }
        }

        return abstractCards;
    }

    public static ArrayList<AbstractCard> ChooseOneMinionGroup(String id,boolean HasShion){
        ArrayList<AbstractCard> abstractCards = new ArrayList<>();
        int i;
        for(i = AbstractDungeon.player.orbs.size() - 1 ; i > -1 ; --i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof AbstractMinion) {
                AbstractSummonCard card = GetCardInstance(i);
                if(((AbstractMinion) AbstractDungeon.player.orbs.get(i)).upgraded && card != null)
                    card.upgrade();
                if (card != null) {
                    card.cardATK = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).ATK;
                    card.cardHP = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).HP;
                }
                if(card != null) {
                    card.MagicNum = HasShion?1:0;
                    card.Index = i;
                    switch (id) {
                        case "Hololive_Teleport":
                            card.Effectnum = 3;
                            break;
                        case "Hololive_Nanodesu":
                            card.Effectnum = 4;
                            break;
                        default:
                    }
                }
                abstractCards.add(card);
            }
        }
        return abstractCards;
    }

    public static ArrayList<AbstractCard> ChooseOneMinionGroup(String id,int ATK,int HP){
        ArrayList<AbstractCard> abstractCards = new ArrayList<>();
        int i;
        for(i = AbstractDungeon.player.orbs.size() - 1 ; i > -1 ; --i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof AbstractMinion) {
                AbstractSummonCard card = GetCardInstance(i);
                if(((AbstractMinion) AbstractDungeon.player.orbs.get(i)).upgraded && card != null)
                    card.upgrade();
                if (card != null) {
                    card.cardATK = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).ATK;
                    card.cardHP = ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).HP;
                }
                if(card != null) {
                    card.Index = i;
                    switch (id) {
                        case "Hololive_StrengthChampion":
                            card.Effectnum = 7;
                            card.MagicNum = ATK;
                            card.MagicNum2 = HP;
                            break;
                        default:
                    }
                }
                abstractCards.add(card);
            }
        }
        return abstractCards;
    }

    public static AbstractMinion GetRandomMinion(){
        ArrayList<AbstractMinion> minions = new ArrayList<>();
        for(AbstractOrb orb:AbstractDungeon.player.orbs){
            if(orb instanceof AbstractMinion){
                minions.add((AbstractMinion) orb);
            }
        }
        if(minions.size() == 0){
            return null;
        } else {
            return minions.get(AbstractDungeon.cardRng.random(0, minions.size() - 1));
        }
    }

    public static AbstractSummonCard GetCardInstance(int i){
        try {
            String id = AbstractDungeon.player.orbs.get(i).ID.replace("Hololive_","cards.summonCard.Call");
            return (AbstractSummonCard)(Class.forName(id)).newInstance();
        }catch (ClassNotFoundException var2){
            var2.printStackTrace();
        }catch (IllegalAccessException var3){
            var3.printStackTrace();
        }catch (InstantiationException var5){
            var5.printStackTrace();
        }
        return null;
    }

    public static AbstractSummonCard GetCardInstance(AbstractMinion m){
        try {
            String id = m.ID.replace("Hololive_","cards.summonCard.Call");
            return (AbstractSummonCard)(Class.forName(id)).newInstance();
        }catch (ClassNotFoundException var2){
            var2.printStackTrace();
        }catch (IllegalAccessException var3){
            var3.printStackTrace();
        }catch (InstantiationException var5){
            var5.printStackTrace();
        }
        return null;
    }
}
