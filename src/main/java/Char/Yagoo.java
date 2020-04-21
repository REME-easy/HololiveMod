package Char;

import basemod.abstracts.CustomPlayer;
import cards.attackCard.OvertimeWork;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import patches.AbstractCardEnum;
import patches.CharacterSelectPatch;
import patches.MyPlayerClassEnum;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS;

public class Yagoo extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
    private static final String MY_CHARACTER_SHOULDER_2 = "img/char/shoulder2.png"; // campfire pose
    private static final String MY_CHARACTER_SHOULDER_1 = "img/char/shoulder.png"; // another campfire pose
    private static final String MY_CHARACTER_CORPSE = "img/char/corpse.png"; // dead corpse
    private static final String MY_CHARACTER_PIC = "img/char/Hololive.png";
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    private static final String[] ORB_TEXTURES = new String[]{"img/UI/orb/layer5.png", "img/UI/orb/layer4.png", "img/UI/orb/layer3.png", "img/UI/orb/layer2.png", "img/UI/orb/layer1.png", "img/UI/orb/layer6.png", "img/UI/orb/layer5d.png", "img/UI/orb/layer4d.png", "img/UI/orb/layer3d.png", "img/UI/orb/layer2d.png", "img/UI/orb/layer1d.png"};
    public Yagoo(String name) {
        super(name, MyPlayerClassEnum.Hololive_HololiveCharacter, ORB_TEXTURES, "img/UI/orb/vfx.png", LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

        this.initializeClass(MY_CHARACTER_PIC, MY_CHARACTER_SHOULDER_2, // required call to load textures and setup energy loadout
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                this.getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
    }

    public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
        ArrayList<String> retVal = new ArrayList<>();
        switch (CharacterSelectPatch.DeckIndex){
            case 1:
                retVal.add("Hololive_CallMinatoAqua");
                retVal.add("Hololive_CallUsadaPekora");
                retVal.add("Hololive_CallMurasakiShion");
                retVal.add("Hololive_CallShiranuiFlare");
                retVal.add("Hololive_CallSpadeEcho");
                retVal.add("Hololive_Breathe");
                retVal.add("Hololive_ManjiGathering");
                retVal.add("Hololive_Yoholoon");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                break;
            case 2:
                retVal.add("Hololive_CallNatsuiroMatsuri");
                retVal.add("Hololive_CallAkaiHaato");
                retVal.add("Hololive_CallNekomataOkayu");
                retVal.add("Hololive_CallSpadeEcho");
                retVal.add("Hololive_CallYogiri");
                retVal.add("Hololive_FlyingPonyTail");
                retVal.add("Hololive_ILoveYou");
                retVal.add("Hololive_CandyCastle");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                break;
            case 3:
                retVal.add("Hololive_CallHoushouMarine");
                retVal.add("Hololive_CallShiroganeNoel");
                retVal.add("Hololive_CallUruhaRushia");
                retVal.add("Hololive_CallShiranuiFlare");
                retVal.add("Hololive_CallUsadaPekora");
                retVal.add("Hololive_StrengthChampion");
                retVal.add("Hololive_CocoNews");
                retVal.add("Hololive_LuckPotion");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                break;
            case 4:
                retVal.add("Hololive_RecruitS");
                retVal.add("Hololive_RecruitS");
                retVal.add("Hololive_RecruitS");
                retVal.add("Hololive_RecruitS");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                break;
            case 5:
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Sequa");
                retVal.add("Hololive_Rebound");
                break;
            case 6:
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_Defend");
                retVal.add("Hololive_CallSakuraMiko");
                retVal.add("Hololive_CallSakuraMiko");
                retVal.add("Hololive_CallSakuraMiko");
                retVal.add("Hololive_CallSakuraMiko");
                retVal.add("Hololive_CallSakuraMiko");
                break;
            case 0:
                retVal.add("Hololive_Strike");
                retVal.add("Hololive_Defend");
                int i;
                Random random = new Random();
                String[] cc = this.GetAllCommonCards();
                String[] sc = this.GetAllSummonCards();
                for(i = 0;i < 3;++i){
                    retVal.add(cc[random.random(0,cc.length - 1)]);
                }
                for(i = 0;i < 5;++i){
                    retVal.add(sc[random.random(0,sc.length - 1)]);
                }
                break;
            default:
                retVal.add("Hololive_Trident");
                retVal.add("Hololive_Trident");
                retVal.add("Hololive_CallSakuraMiko");
                retVal.add("Hololive_CallUsadaPekora");

                break;
        }

        return retVal;
    }

    public ArrayList<String> getStartingRelics() { // starting relics - also simple
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Hololive_TheStar");
        UnlockTracker.markRelicAsSeen("Hololive_TheStar");
        return retVal;
    }

    private String[] GetAllCommonCards(){
        String[] commoncards = {
                "CocoNews","OvertimeWork","OvertimeWork","OvertimeWork","Sequa","Sequa","Sequa","SoulCatcher","Breathe","HuntDinosaur",
                "ILoveYou","LuckPotion","Rebound","RevenueBasedLive","Teleport","Drill","ManjiGathering",
                "Psychosis","Yoholoon","FlyingPonyTail","CandyCastle","StrengthChampion","DevilCharm","Trident"
        };
        int i;
        for(i = 0;i < commoncards.length;++i){
            commoncards[i] = "Hololive_".concat(commoncards[i]);
        }
        return commoncards;
    }

    private String[] GetAllSummonCards(){
        String[] summoncards = {
                "AkaiHaato","AkiRosenthal","AmaneKanata","AZKi","Civia","FriendA","HimemoriLuna","HoshimachiSuisei","HoushouMarine","InugamiKorone",
                "KiryuuCoco","MinatoAqua","MurasakiShion","NakiriAyame","NatsuiroMatsuri","NekomataOkayu","OkamiMio","OzoraSubaru","Roboco",
                "SakuraMiko","ShirakamiFubuki","ShiranuiFlare","ShiroganeNoel","SpadeEcho","TokinoSora","TokoyamiTowa","TsunomakiWatame",
                "UruhaRushia","UsadaPekora","Yogiri","YozoraMel","YuzukiChoco"
        };
        int i;
        for(i = 0;i < summoncards.length;++i){
            summoncards[i] = "Hololive_Call".concat(summoncards[i]);
        }
        return summoncards;
    }

    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int ORB_SLOTS = 4;
    private static final int HAND_SIZE = 5;

    public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
        return new CharSelectInfo("Yagoo", Description(),
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, this.getStartingRelics(), this.getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "Yagoo";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.Hololive_BLUE;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new OvertimeWork();
    }

    @Override
    public Color getCardTrailColor() {
        return getColor(100.0f,225.0f,240.0f);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("GOLD_GAIN_5", MathUtils.random(-0.1F, 0.1F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "GOLD_GAIN_5";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "Yagoo";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Yagoo(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return ".......";
    }

    @Override
    public Color getSlashAttackColor() {
        return getColor(100.0F,225.0F,240.0F);
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public Color getCardRenderColor() {
        return getColor(100.0F,225.0F,240.0F);
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    private static Color getColor(float r, float g, float b) {
        return new Color(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
    }

    private static String Description(){
        if(Settings.language == ZHS){
            return "为了实现偶像梦，yagoo来到了尖塔…… NL （有什么关系吗喂）";
        } else {
            return "To realize his idol dream,yagoo come here... NL (What?";
        }
    }
}
