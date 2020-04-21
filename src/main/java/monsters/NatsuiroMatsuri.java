package monsters;

import cards.cursecard.RevenueRecovery;
import cards.summonCard.AbstractSummonCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class NatsuiroMatsuri extends AbstractMonster {
    private static final String ID = "Hololive_MNatsuiroMatsuri";
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    private static final String NAME = monsterStrings.NAME;
    private static final String IMG_PATH = "img/Monsters/NatsuiroMatsuri.png";
    private boolean firstMove = true;
    public NatsuiroMatsuri(){
        super(NAME,ID,2500,0.0F,0.0F,300.0F,483.0F,IMG_PATH);
        this.damage.add(new DamageInfo(this,1));
    }


    public void takeTurn(){
        switch (this.nextMove){
            case 1:
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, monsterStrings.DIALOG[0]));
                int j = 0;
                for(AbstractCard c:AbstractDungeon.player.drawPile.group){
                    if(c instanceof AbstractSummonCard){
                        this.addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.drawPile,true));
                        ++j;
                    }
                    if(j > 8){
                        break;
                    }
                }
                this.firstMove = false;
                break;
            case 2:
                int i;
                for(i = 0 ; i < 5 ; ++i){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    this.addToBot(new HealAction(this,this,this.damage.get(0).base));
                }
                this.addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,1),1));
                break;
            case 3:
                int i1;
                for(i1 = 0 ; i1 < 5 ; ++i1){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
                this.addToBot(new MakeTempCardInHandAction(new RevenueRecovery(),AbstractDungeon.cardRandomRng.random(1,4)));
                for(AbstractPower power:AbstractDungeon.player.powers){
                    if(power.amount != -1) {
                        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,power,power.amount));
                    }
                }
                for(AbstractMonster monster:AbstractDungeon.getMonsters().monsters) {
                    for(AbstractPower power:monster.powers){
                        if(power.ID.equals("Time Warp")){
                            if(power.amount > 5){
                                this.addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,power,11 - power.amount));
                            } else {
                                this.addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,power,power.amount));
                            }
                            continue;
                        }
                        if(power.amount != -1) {
                            this.addToBot(new ApplyPowerAction(monster,AbstractDungeon.player,power,power.amount));
                        }
                    }
                }
                break;
        }
        this.rollMove();
    }



    public void getMove(int num){
        if(this.firstMove){
            this.setMove((byte)1,Intent.DEBUFF);
        } else {
            if(num > 60){
                this.setMove((byte)2,Intent.ATTACK_BUFF,this.damage.get(0).base,5,true);
            }else {
                this.setMove((byte)3,Intent.ATTACK_DEBUFF,this.damage.get(0).base,5,true);
            }
        }
    }


    public void die(){
        this.useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
    }
}
