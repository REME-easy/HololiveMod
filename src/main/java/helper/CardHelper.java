package helper;

import Char.HololiveCharacter;
import cards.optionCard.*;
import cards.skillCard.OverLoad;
import cards.summonCard.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import minions.AbstractMinion;
import relics.TheStar;

import java.util.ArrayList;
import java.util.Collections;

public class CardHelper {
    public static boolean isBalance = false;
    public static ArrayList<AbstractSummonCard> UsedSummonCardsThisBattle = new ArrayList<>();
    public CardHelper(){}

    public static void getBalanceMode(boolean b){
        isBalance = b;
    }

    public static ArrayList<AbstractCard> GenerateMinionGroup(boolean upgraded){
        ArrayList<AbstractCard> derp = new ArrayList<>();

        while(derp.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp;
            do{
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER);
            }while(!(tmp instanceof AbstractSummonCard) || !(AbstractDungeon.player instanceof HololiveCharacter));

            for(AbstractCard card:derp){
                if(card.cardID.equals(tmp.cardID)){
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        if(upgraded){
            for(AbstractCard c:derp){
                c.upgrade();
            }
        }

        return derp;
    }


    public static void OnChooseThisCard(AbstractCard card,int Index,int EffectTimes,int Effectnum,int MagicNum,int MagicNum2){
        AbstractOrb minion = AbstractDungeon.player.orbs.get(Index);
        int j;
        for(j = 0 ; j < EffectTimes ; ++j){
            switch (Effectnum) {
                case 3:
                    if(minion instanceof AbstractMinion) {
                        AbstractCard c = card.makeCopy();
                        if(((AbstractMinion) minion).upgraded){
                            c.upgrade();
                        }
                        c.setCostForTurn(0);
                        AbstractOrb orbSlot = new EmptyOrbSlot();
                        int i;
                        for (i = Index + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
                            Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
                        }
                        AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
                        for (i = Index; i < AbstractDungeon.player.orbs.size(); ++i) {
                            AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
                        }

                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c,MagicNum + 1));
                    }
                    break;
                case 4:
                    if(minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).isdead = true;
                        minion.onEvoke();
                        AbstractOrb orbSlot = new EmptyOrbSlot();
                        int i;
                        for (i = Index + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
                            Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
                        }
                        AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
                        for (i = Index; i < AbstractDungeon.player.orbs.size(); ++i) {
                            AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
                        }
                        for(AbstractOrb orb:AbstractDungeon.player.orbs){
                            if(orb instanceof AbstractMinion){
                                ((AbstractMinion) orb).AttackEffect();
                            }
                        }
                    }
                    break;
                case 0:
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
                    break;
                case 1:
                    if (minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).ChangeHP(MagicNum,false);
                        ((AbstractMinion) minion).Taunt = true;
                    }
                    break;
                case 2:
                    if (minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).ChangeHP(MagicNum,false);
                        ((AbstractMinion) minion).ChangeATK(MagicNum,false);
                        ((AbstractMinion) minion).AttackEffect();
                    }
                    break;
                case 5:
                    if(minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).ChangeHP(((AbstractMinion) minion).HP,false);
                    }
                    break;
                case 6:
                    if(minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).ChangeATK(MagicNum,false);
                    }
                    break;
                case 7:
                    if(minion instanceof AbstractMinion) {
                        ((AbstractMinion) minion).ChangeHP(MagicNum2,false);
                        ((AbstractMinion) minion).ChangeATK(MagicNum,false);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static AbstractCard GetRandomProgramCard(){
        //ArrayList<AbstractCard> cards = new ArrayList<>();
        //cards.add(new OverLoad());
        //cards.get(AbstractDungeon.cardRng.random(0,cards.size() - 1));
        return new OverLoad();
    }


    public static ArrayList<AbstractCard> GetCombinationOptionCards() {
        boolean[] hasMinion = new boolean[38];
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            {
                if(c instanceof AbstractSummonCard){
                    if (c instanceof CallAkaiHaato) {
                        hasMinion[0] = true;
                        continue;
                    }
                    if (c instanceof CallAkiRosenthal) {
                        hasMinion[1] = true;
                        continue;
                    }
                    if (c instanceof CallAmaneKanata) {
                        hasMinion[2] = true;
                        continue;
                    }
                    if (c instanceof CallAZKi) {
                        hasMinion[3] = true;
                        continue;
                    }
                    if (c instanceof CallCivia) {
                        hasMinion[4] = true;
                        continue;
                    }
                    if (c instanceof CallFriendA) {
                        hasMinion[5] = true;
                        continue;
                    }
                    if (c instanceof CallHimemoriLuna) {
                        hasMinion[6] = true;
                        continue;
                    }
                    if (c instanceof CallHoshimachiSuisei) {
                        hasMinion[7] = true;
                        continue;
                    }
                    if (c instanceof CallHoushouMarine) {
                        hasMinion[8] = true;
                        continue;
                    }
                    if (c instanceof CallInugamiKorone) {
                        hasMinion[9] = true;
                        continue;
                    }
                    if (c instanceof CallKiryuuCoco) {
                        hasMinion[10] = true;
                        continue;
                    }
                    if (c instanceof CallMinatoAqua) {
                        hasMinion[11] = true;
                        continue;
                    }
                    if (c instanceof CallMurasakiShion) {
                        hasMinion[12] = true;
                        continue;
                    }
                    if (c instanceof CallNakiriAyame) {
                        hasMinion[13] = true;
                        continue;
                    }
                    if (c instanceof CallNatsuiroMatsuri) {
                        hasMinion[14] = true;
                        continue;
                    }
                    if (c instanceof CallNekomataOkayu) {
                        hasMinion[15] = true;
                        continue;
                    }
                    if (c instanceof CallOkamiMio) {
                        hasMinion[16] = true;
                        continue;
                    }
                    if (c instanceof CallOzoraSubaru) {
                        hasMinion[17] = true;
                        continue;
                    }
                    if (c instanceof CallRoboco) {
                        hasMinion[18] = true;
                        continue;
                    }
                    if (c instanceof CallSakuraMiko) {
                        hasMinion[19] = true;
                        continue;
                    }
                    if (c instanceof CallShirakamiFubuki) {
                        hasMinion[20] = true;
                        continue;
                    }
                    if (c instanceof CallShiranuiFlare) {
                        hasMinion[21] = true;
                        continue;
                    }
                    if (c instanceof CallShiroganeNoel) {
                        hasMinion[22] = true;
                        continue;
                    }
                    if (c instanceof CallSpadeEcho) {
                        hasMinion[23] = true;
                        continue;
                    }
                    if (c instanceof CallTokinoSora) {
                        hasMinion[24] = true;
                        continue;
                    }
                    if (c instanceof CallTsunomakiWatame) {
                        hasMinion[25] = true;
                        continue;
                    }
                    if (c instanceof CallTokoyamiTowa) {
                        hasMinion[26] = true;
                    }
                    if (c instanceof CallUruhaRushia) {
                        hasMinion[27] = true;
                        continue;
                    }
                    if (c instanceof CallUsadaPekora) {
                        hasMinion[28] = true;
                        continue;
                    }
                    if (c instanceof CallYogiri) {
                        hasMinion[29] = true;
                        continue;
                    }
                    if (c instanceof CallYozoraMel) {
                        hasMinion[30] = true;
                        continue;
                    }
                    if (c instanceof CallYuzukiChoco) {
                        hasMinion[31] = true;
                        continue;
                    }
                    if (c instanceof CallRosalyn) {
                        hasMinion[32] = true;
                        continue;
                    }
                    if (c instanceof CallDoris) {
                        hasMinion[33] = true;
                        continue;
                    }
                    if (c instanceof CallArtia) {
                        hasMinion[34] = true;
                        continue;
                    }
                    if (c instanceof CallAyundaRisu) {
                        hasMinion[35] = true;
                        continue;
                    }
                    if (c instanceof CallAiraniIofifteen) {
                        hasMinion[36] = true;
                        continue;
                    }
                    if (c instanceof CallMoonaHoshinova) {
                        hasMinion[37] = true;
                    }
                }
            }
        }
        if(hasMinion[11] && hasMinion[12] && hasMinion[13] && !TheStar.CombinationIndex[0]){
            cards.add(new ManjiCombinationOption());
        }
        if(hasMinion[14] && hasMinion[20] && !TheStar.CombinationIndex[1]){
            cards.add(new NatsuiroFubukiCombinationOption());
        }
        if(hasMinion[19] && hasMinion[28] && !TheStar.CombinationIndex[2]){
            cards.add(new PekoMikoCombinationOption());
        }
        if(hasMinion[9] && hasMinion[15] && hasMinion[16] && hasMinion[20] && !TheStar.CombinationIndex[3]){
            cards.add(new GamersCombinationOption());
        }
        if(hasMinion[4] && hasMinion[23] && hasMinion[29] && !TheStar.CombinationIndex[4]){
            cards.add(new HappyFamilyCombinationOption());
        }
        if(hasMinion[0] && hasMinion[1] && hasMinion[21] && hasMinion[25] && hasMinion[30] && hasMinion[31] && !TheStar.CombinationIndex[5]){
            cards.add(new BlondHairCombinationOption());
        }
        if(hasMinion[13] && hasMinion[20] && hasMinion[17] && hasMinion[16] && !TheStar.CombinationIndex[7]){
            cards.add(new FamsCombinationOption());
        }
        if(hasMinion[20] && hasMinion[16] && !TheStar.CombinationIndex[8]){
            cards.add(new CatDogCombinationOption());
        }
        if(hasMinion[9] && hasMinion[15] && !TheStar.CombinationIndex[9]){
            cards.add(new NewCatDogCombinationOption());
        }
        if(hasMinion[8] && hasMinion[11] && !TheStar.CombinationIndex[10]){
            cards.add(new AquaMarineCombinationOption());
        }
        if(hasMinion[5] && hasMinion[24] && !TheStar.CombinationIndex[11]){
            cards.add(new SoraACombinationOption());
        }
        if(hasMinion[12] && hasMinion[14] && !TheStar.CombinationIndex[12]){
            cards.add(new MatsuriShionCombinationOption());
        }
        if(hasMinion[21] && hasMinion[22] && !TheStar.CombinationIndex[13]){
            cards.add(new NoelFlareCombinationOption());
        }
        if(hasMinion[5] && hasMinion[24] && hasMinion[18] && hasMinion[19] && hasMinion[7] && !TheStar.CombinationIndex[14]){
            cards.add(new ZeroCombinationOption());
        }
        if(hasMinion[30] && hasMinion[20] && hasMinion[14] && hasMinion[1] && hasMinion[0] && !TheStar.CombinationIndex[15]){
            cards.add(new OneCombinationOption());
        }
        if(hasMinion[11] && hasMinion[12] && hasMinion[13] && hasMinion[17] && hasMinion[31] && !TheStar.CombinationIndex[16]){
            cards.add(new TwoCombinationOption());
        }
        if(hasMinion[27] && hasMinion[28] && hasMinion[21] && hasMinion[22] && hasMinion[8] && !TheStar.CombinationIndex[17]){
            cards.add(new ThreeCombinationOption());
        }
        if(hasMinion[2] && hasMinion[6] && hasMinion[10] && hasMinion[25] && hasMinion[26] && !TheStar.CombinationIndex[18]){
            cards.add(new FourCombinationOption());
        }
        if(hasMinion[3] && hasMinion[7] && !TheStar.CombinationIndex[19]){
            cards.add(new IdolCombinationOption());
        }
        if(hasMinion[0] && hasMinion[25] && !TheStar.CombinationIndex[21]){
            cards.add(new WatameHaatoCombinationOption());
        }
        if(hasMinion[14] && hasMinion[6] && !TheStar.CombinationIndex[22]){
            cards.add(new MatsuriLunaCombinationOption());
        }
        if(hasMinion[10] && hasMinion[2] && !TheStar.CombinationIndex[23]){
            cards.add(new CocoKanataCombinationOption());
        }
        if(hasMinion[32] && hasMinion[33] && hasMinion[34] && !TheStar.CombinationIndex[24]) {
            cards.add(new CNTwoCombinationOption());
        }
        if(hasMinion[35] && hasMinion[36] && hasMinion[37] && !TheStar.CombinationIndex[25]) {
            cards.add(new IDCombinationOption());
        }
        int hasall = 0;
        for(boolean b:hasMinion){
            if(b)
                ++hasall;
            else
                break;
        }
        if(hasall == hasMinion.length && !TheStar.CombinationIndex[20]){
            cards.add(new AllCombinationOption());
        }
        return cards;
    }


}
