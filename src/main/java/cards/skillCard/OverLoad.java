package cards.skillCard;

import cards.attackCard.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.random.Random;
import minions.*;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;
import static patches.AbstractCardEnum.Hololive_BLUE;

public class OverLoad extends AbstractProgramCard {
    private static final String ID = "Hololive_OverLoad";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "img/card/skillcards/OverLoad.png";
    private static final int COST = -2;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_MAGIC_NUM = 1;
    public OverLoad() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                SKILL, Hololive_BLUE,
                CardRarity.SPECIAL, CardTarget.NONE);
        this.baseMagicNumber = this.magicNumber = MAGIC_NUM;
    }


    public void Effect(){
        if (this.HasRoboco()){
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for(AbstractOrb orb:AbstractDungeon.player.orbs){
                if(orb instanceof AbstractMinion) {
                    if (orb instanceof MinatoAqua) {
                        cards.add(new Sequa());
                        cards.add(new Recruit());
                        continue;
                    }
                    if (orb instanceof UsadaPekora) {
                        cards.add(new LuckPotion());
                        cards.add(new Trident());
                        continue;
                    }
                    if (orb instanceof MurasakiShion) {
                        cards.add(new Drill());
                        cards.add(new Teleport());
                        continue;
                    }
                    if (orb instanceof FriendA) {
                        cards.add(new OvertimeWork());
                        continue;
                    }
                    if (orb instanceof SakuraMiko) {
                        cards.add(new DiamondStrike());
                        continue;
                    }
                    if (orb instanceof KiryuuCoco) {
                        cards.add(new CocoNews());
                        continue;
                    }
                    if (orb instanceof InugamiKorone) {
                        cards.add(new MaximGun());
                        continue;
                    }
                    if (orb instanceof UruhaRushia) {
                        cards.add(new Nanodesu());
                        cards.add(new Rebirth());
                        continue;
                    }
                    if (orb instanceof ShirakamiFubuki) {
                        cards.add(new SummonFox());
                        cards.add(new AWSL());
                        continue;
                    }
                    if (orb instanceof NatsuiroMatsuri) {
                        cards.add(new Breathe());
                        cards.add(new HighAtkLowDef());
                        cards.add(new BeCareful());
                        continue;
                    }
                    if (orb instanceof AkaiHaato) {
                        cards.add(new ILoveYou());
                        continue;
                    }
                    if (orb instanceof TokinoSora) {
                        cards.add(new Rebound());
                        continue;
                    }
                    if (orb instanceof Civia) {
                        cards.add(new Watashimo());
                        continue;
                    }
                    if (orb instanceof HoushouMarine) {
                        cards.add(new HuntDinosaur());
                        continue;
                    }
                    if (orb instanceof NekomataOkayu) {
                        cards.add(new BringToTrial());
                        continue;
                    }
                    if (orb instanceof HoshimachiSuisei) {
                        cards.add(new Psychosis());
                        continue;
                    }
                    if (orb instanceof OzoraSubaru) {
                        cards.add(new Yoholoon());
                        continue;
                    }
                    if (orb instanceof AZKi) {
                        cards.add(new OpenUp());
                        continue;
                    }
                    if (orb instanceof AkiRosenthal) {
                        cards.add(new FlyingPonyTail());
                        continue;
                    }
                    if (orb instanceof AmaneKanata) {
                        cards.add(new ScarfAndHalo());
                        continue;
                    }
                    if (orb instanceof HimemoriLuna) {
                        cards.add(new CandyCastle());
                        continue;
                    }
                    if (orb instanceof ShiroganeNoel) {
                        cards.add(new StrengthChampion());
                        continue;
                    }
                    if (orb instanceof TokoyamiTowa) {
                        cards.add(new DevilCharm());
                        continue;
                    }
                    if (orb instanceof YuzukiChoco) {
                        cards.add(new Spy());
                        continue;
                    }
                    if (orb instanceof YozoraMel) {
                        cards.add(new ASMR());
                        continue;
                    }
                    if (orb instanceof SpadeEcho) {
                        cards.add(new Thief());
                        continue;
                    }
                    if (orb instanceof Yogiri) {
                        cards.add(new DurableLive());
                    }
                    if(orb instanceof ShiranuiFlare) {
                        cards.add(new FlareMind());
                    }
                }
            }
            int i;
            Random random = new Random();
            if(cards.size() > 0)
                for(i = 0 ; i < this.magicNumber ; ++i){
                    this.addToBot(new MakeTempCardInHandAction(cards.get(random.random(0,cards.size() - 1))));
                }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;

    }

    @Override
    public AbstractCard makeCopy() {
        return new OverLoad();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC_NUM);
        }
    }
}