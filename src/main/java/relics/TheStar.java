package relics;

import Char.HololiveCharacter;
import actions.ChooseAbilityAction;
import basemod.abstracts.CustomRelic;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import helper.CardHelper;
import helper.OrbHelper;
import minions.AbstractMinion;
import powers.FlarePower;

import java.util.ArrayList;

public class TheStar extends CustomRelic {
    public static final String ID = "Hololive_TheStar";
    public static boolean[] CombinationIndex = new boolean[27];
    public static ArrayList<AbstractCard> choices;
    private int ChooseChance = 0;
    private boolean RclickStart = false;
    private boolean Rclick = false;
    private boolean Idol = false;
    //private int dmg;
    public TheStar() {
        super(ID, ImageMaster.loadImage("img/relics/HololiveStarRelics.png"),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return GetDescription(); // DESCRIPTIONS pulls from your localization file
    }

    private String GetDescription() {
        String Description = DESCRIPTIONS[0];
        int i;
        for(i = 0 ; i < CombinationIndex.length ; ++i){
            if(CombinationIndex[i]){
                Description = Description.concat(DESCRIPTIONS[i + 1]);
            }
        }
        return Description;
    }
    //@Override
   //public int onAttacked(DamageInfo info, int damageAmount){
   //    dmg = damageAmount;
   //    int j;
   //    ArrayList<AbstractMinion> minions = new ArrayList<>();
   //    for (AbstractOrb orb : AbstractDungeon.player.orbs) {
   //        if (orb instanceof AbstractMinion)
   //            if (((AbstractMinion) orb).Taunt)
   //                minions.add((AbstractMinion) orb);
   //    }
   //    if (minions.size() != 0)
   //        onAttackAssitTaunt(minions);
   //    onAttackAssit();
   //    return dmg;
   //}

   //private void onAttackAssit(){
   //    int j;
   //    for (j = 0;j<AbstractDungeon.player.orbs.size() - 1; ++j) {
   //        AbstractOrb orb = AbstractDungeon.player.orbs.get(j);
   //        if(orb instanceof AbstractMinion){
   //            AbstractMinion student = (AbstractMinion) orb;
   //            if (student.HP > this.dmg){
   //                student.ChangeHP(-this.dmg);
   //                student.onDamaged(this.dmg);
   //                this.dmg = 0;
   //                break;
   //            } else {
   //                student.onDamaged(student.HP);
   //                this.dmg -= student.HP;
   //                student.HP = 0;
   //                student.onEvoke();

   //                AbstractOrb orbSlot = new EmptyOrbSlot();
   //                int i;
   //                for (i = 1; i < AbstractDungeon.player.orbs.size(); ++i) {
   //                    Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
   //                }
   //                AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
   //                for (i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
   //                    AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
   //                }
   //                onAttackAssit();
   //            }
   //        }
   //    }
   //}

   //private void onAttackAssitTaunt(ArrayList<AbstractMinion> minions) {
   //        int j;
   //        for (j = 0; j < minions.size() - 1; ++j) {
   //            AbstractMinion orb = minions.get(j);
   //            if (orb.HP > this.dmg) {
   //                orb.ChangeHP(-this.dmg);
   //                orb.onDamaged(this.dmg);
   //                this.dmg = 0;
   //                break;
   //            } else {
   //                orb.onDamaged(orb.HP);
   //                this.dmg -= orb.HP;
   //                orb.HP = 0;
   //                orb.onEvoke();

   //                AbstractOrb orbSlot = new EmptyOrbSlot();
   //                int i;
   //                for (i = 1; i < AbstractDungeon.player.orbs.size(); ++i) {
   //                    Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
   //                }
   //                AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
   //                for (i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
   //                    AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
   //                }
   //                onAttackAssitTaunt(minions);
   //            }
   //        }

   //}

   //public void onUnequip(){
   //    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new TheStar());
   //}

   //int deathnum = 0;
   //int j;
   //    for (j = 0;j<AbstractDungeon.player.orbs.size() - 1; ++j) {
   //    AbstractOrb orb = AbstractDungeon.player.orbs.get(j);
   //    if(orb instanceof AbstractMinion){
   //        AbstractMinion student = (AbstractMinion) orb;
   //        if (student.HP > damageAmount){
   //            student.HP -= damageAmount;
   //            student.onDamaged();
   //            damageAmount = 0;
   //            break;
   //        } else {
   //            damageAmount -= student.HP;
   //            student.HP = 0;
   //            student.onDamaged();
   //            student.onEvoke();
   //            ++deathnum;
   //            this.addToTop(new WaitAction(0.2F));
   //        }
   //    }
   //}

   //    for(j=0;j<deathnum;++j) {
   //    AbstractOrb orbSlot = new EmptyOrbSlot();
   //    int i;
   //    for (i = 1; i < AbstractDungeon.player.orbs.size(); ++i) {
   //        Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
   //    }

   //    AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);

   //    for (i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
   //        AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
   //    }
   //    this.addToTop(new WaitAction(0.1F));
   //}
   //    return damageAmount;

    @Override
    public void onPlayerEndTurn(){
        if(CombinationIndex[13]){
            AbstractMinion m = null;
            int minhp = 0;
            for(AbstractOrb orb:AbstractDungeon.player.orbs){
                if(orb instanceof AbstractMinion){
                    if(((AbstractMinion) orb).HP < minhp || minhp == 0){
                        minhp = ((AbstractMinion) orb).HP;
                        m = (AbstractMinion) orb;
                    }
                }
            }
            if(m != null){
                m.ChangeHP(3,false);
            }
        }
    }


    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(CombinationIndex[12])
        if(drawnCard instanceof AbstractSummonCard){
            ((AbstractSummonCard) drawnCard).cardHP = 7;
            ((AbstractSummonCard) drawnCard).cardATK = 7;
            drawnCard.setCostForTurn(1);
        }
        if(CombinationIndex[19] && Idol){
            if(drawnCard.baseDamage > 0){
                drawnCard.baseDamage *= 3;
            }
            if(drawnCard.baseBlock > 0){
                drawnCard.baseBlock *= 3;
            }
            if(drawnCard.baseMagicNumber > 0){
                drawnCard.baseMagicNumber *= 3;
                drawnCard.magicNumber = drawnCard.baseMagicNumber;
            }
            if(drawnCard instanceof AbstractSummonCard){
                ((AbstractSummonCard) drawnCard).cardATK *= 3;
                ((AbstractSummonCard) drawnCard).cardHP *= 3;
            }
            Idol = false;
        }
    }



    @Override
    public void atBattleStart() {
        Idol = true;
        int i;
        for(i = 0 ; i < CombinationIndex.length ; ++i){
                CombinationIndex[i] = false;
        }
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        choices = CardHelper.GetCombinationOptionCards();
        ChooseChance = choices.size() / 4 + 1;
        this.flash();
        if(choices.size() > 0){
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        }
        ChooseCombination();
    }

    public void ChooseCombination(){
        this.addToTop(new ChooseAbilityAction(ChooseChance));
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m){
        if(CombinationIndex[0] && c.type == AbstractCard.CardType.ATTACK ) {
            AbstractMinion minion = OrbHelper.GetRandomMinion();
            if (minion != null) {
                this.flash();
                minion.ChangeATK(1,false);
                minion.ChangeHP(1,false);
            }
        }
        if(CombinationIndex[4] && c instanceof AbstractSummonCard) {
            int i;
            for(i = AbstractDungeon.player.orbs.size() - 1 ; i > -1 ; --i){
                if(AbstractDungeon.player.orbs.get(i) instanceof AbstractMinion){
                    ((AbstractMinion) AbstractDungeon.player.orbs.get(i)).AttackEffect();
                    this.flash();
                    break;
                }
            }
        }
        if(CombinationIndex[11] && c.color == AbstractCard.CardColor.COLORLESS){
            this.addToBot(new GainEnergyAction(1));
            this.flash();
        }
    }

    public void onGainGold(){
            if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                if(CombinationIndex[2]){
                    AbstractMonster monster = null;
                this.flash();
                monster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if(monster != null)
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new PoisonPower(monster, AbstractDungeon.player, 1), 1));
            }
        }
    }

    private void onRightClick(){
        if(CombinationIndex[14] && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            this.flash();
            AbstractCard tmp;
            do{
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER);
            }while(!(tmp instanceof AbstractSummonCard) || !(AbstractDungeon.player instanceof HololiveCharacter));
            this.addToBot(new MakeTempCardInHandAction(tmp));
        }
    }


    @Override
    public void onSpawnMonster(AbstractMonster monster) {
        if(CombinationIndex[9])
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new FlarePower(monster, 4), 4));
    }

    @Override
    public int changeNumberOfCardsInReward(int numberOfCards) {
        if(CombinationIndex[15])
            return numberOfCards + 1;
        else
            return numberOfCards;
    }

    public void update() {
        super.update();
        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                this.Rclick = true;
            }
            this.RclickStart = false;
        }
        if (this.isObtained && this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
            this.RclickStart = true;
        }
        if (this.Rclick) {
            this.Rclick = false;
            this.onRightClick();
        }
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new TheStar();
    }

}
