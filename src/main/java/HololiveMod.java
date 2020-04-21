import Char.HololiveCharacter;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.attackCard.*;
import cards.cursecard.RevenueRecovery;
import cards.optionCard.*;
import cards.skillCard.*;
import cards.summonCard.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RestartForChangesEffect;
import helper.CardHelper;
import helper.OrbHelper;
import minions.AbstractMinion;
import minions.AiraniIofifteen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patches.CharacterSelectPatch;
import relics.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.language;
import static patches.AbstractCardEnum.Achievements;
import static patches.AbstractCardEnum.Hololive_BLUE;
import static patches.MyPlayerClassEnum.Hololive_HololiveCharacter;


@SpireInitializer
public class HololiveMod implements  EditCharactersSubscriber,EditStringsSubscriber,EditCardsSubscriber,EditRelicsSubscriber,EditKeywordsSubscriber,OnCardUseSubscriber,OnPlayerDamagedSubscriber,OnStartBattleSubscriber,PostBattleSubscriber,PostInitializeSubscriber,PostDungeonInitializeSubscriber{
    private static final Logger logger = LogManager.getLogger(HololiveMod.class);
    private static  final String MY_CHARACTER_BUTTON = "img/char/hololiveCharacterButton.png";
    private static  final String MY_CHARACTER_PORTRAIT = "img/char/hololiveCharacterPortrait.png";
    private static final  String BG_ATTACK_512 = "img/512/bg_attack_default_gray.png";
    private static final  String BG_POWER_512 = "img/512/bg_power_default_gray.png";
    private static final  String BG_SKILL_512 = "img/512/bg_skill_default_gray.png";
    private static final  String small_orb = "img/512/card_default_gray_orb.png";
    private static final  String BG_ATTACK_1024 = "img/1024/bg_attack_default_gray.png";
    private static final  String BG_POWER_1024 = "img/1024/bg_power_default_gray.png";
    private static final  String BG_skill_1024 = "img/1024/bg_skill_default_gray.png";
    private static final  String big_orb = "img/1024/card_default_gray_orb.png";
    private static final  String energy_orb = "img/512/card_small_orb.png";
    private static final Color light_blue = getColor(100.0f,225.0f,240.0f);

    public static boolean isBalance = false;

    private int dmg;
    private boolean CanAWSL = true;

    public HololiveMod() {
            BaseMod.subscribe(this);
            BaseMod.addColor(Hololive_BLUE,light_blue,light_blue,light_blue,light_blue,light_blue,light_blue,light_blue,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,small_orb,BG_ATTACK_1024,BG_skill_1024,BG_POWER_1024,big_orb,energy_orb);
            BaseMod.addColor(Achievements,light_blue,light_blue,light_blue,light_blue,light_blue,light_blue,light_blue,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,small_orb,BG_ATTACK_1024,BG_skill_1024,BG_POWER_1024,big_orb,energy_orb);
    }

    public static void initialize() {
        new HololiveMod();
        try{
            Properties defaults = new Properties();
            defaults.setProperty("BalanceMode", "false");
            SpireConfig config = new SpireConfig("HololiveMod", "Common", defaults);

            isBalance = config.getBool("BalanceMode");

            CardHelper.getBalanceMode(isBalance);
        }catch (IOException var2){
            var2.printStackTrace();
        }
    }


    @Override
    public void receiveEditCharacters() {
        logger.info("开始加载vtuber");
        logger.info("add " + Hololive_HololiveCharacter.toString());
        BaseMod.addCharacter(new HololiveCharacter(CardCrawlGame.playerName),
                MY_CHARACTER_BUTTON,
                MY_CHARACTER_PORTRAIT,
                Hololive_HololiveCharacter);
        logger.info("b（￣▽￣）d　");
    }
    public void receiveEditStrings() {
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zh";
        } else if(language == Settings.GameLanguage.JPN){
            lang = "jp";
        } else {
            lang = "eng";
        }
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/REMERelics_" + lang + ".json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/REMECards_" + lang + ".json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/REMEPowers_" + lang + ".json");
        //BaseMod.loadCustomStringsFile(EventStrings.class, "localization/REMEEvent_" + lang + ".json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class,"localization/REMEMonsters_"+ lang +".json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,"localization/REMEOrbs_" + lang + ".json");
        BaseMod.loadCustomStringsFile(UIStrings.class,"localization/REMEUI_" + lang + ".json");
    }

    public void receiveEditCards() {
        BaseMod.addCard(new OvertimeWork());
        BaseMod.addCard(new Rebound());
        BaseMod.addCard(new Sequa());
        BaseMod.addCard(new IdolExercise());
        BaseMod.addCard(new HuntDinosaur());
        BaseMod.addCard(new MaximGun());
        BaseMod.addCard(new LuckPotion());
        BaseMod.addCard(new Breathe());
        BaseMod.addCard(new Yeah());
        BaseMod.addCard(new Recruit());
        BaseMod.addCard(new RecruitS());
        BaseMod.addCard(new ILoveYou());
        BaseMod.addCard(new Teleport());
        BaseMod.addCard(new CocoNews());
        BaseMod.addCard(new RevenueBasedLive());
        BaseMod.addCard(new SoulCatcher());
        BaseMod.addCard(new Nanodesu());
        BaseMod.addCard(new DiamondStrike());
        BaseMod.addCard(new SummonFox());
        BaseMod.addCard(new ManjiGathering());
        BaseMod.addCard(new Rebirth());
        BaseMod.addCard(new Watashimo());
        BaseMod.addCard(new Drill());
        BaseMod.addCard(new StarCraft());
        BaseMod.addCard(new BringToTrial());
        BaseMod.addCard(new Psychosis());
        BaseMod.addCard(new HighAtkLowDef());
        BaseMod.addCard(new Yoholoon());
        BaseMod.addCard(new WolfAndLamb());
        BaseMod.addCard(new OpenUp());
        BaseMod.addCard(new FlyingPonyTail());
        BaseMod.addCard(new CandyCastle());
        BaseMod.addCard(new StrengthChampion());
        BaseMod.addCard(new DevilCharm());
        BaseMod.addCard(new ScarfAndHalo());
        BaseMod.addCard(new Spy());
        BaseMod.addCard(new ASMR());
        BaseMod.addCard(new Thief());
        BaseMod.addCard(new AWSL());
        BaseMod.addCard(new DDD());
        BaseMod.addCard(new DurableLive());
        BaseMod.addCard(new BeCareful());
        BaseMod.addCard(new Trident());
        BaseMod.addCard(new FlareMind());
        BaseMod.addCard(new HololiveBlizzard());
        BaseMod.addCard(new CallFriendA());
        BaseMod.addCard(new CallMinatoAqua());
        BaseMod.addCard(new CallTokinoSora());
        BaseMod.addCard(new CallYuzukiChoco());
        BaseMod.addCard(new CallInugamiKorone());
        BaseMod.addCard(new CallHoushouMarine());
        BaseMod.addCard(new CallUsadaPekora());
        BaseMod.addCard(new CallNatsuiroMatsuri());
        BaseMod.addCard(new CallKiryuuCoco());
        BaseMod.addCard(new CallMurasakiShion());
        BaseMod.addCard(new CallShiroganeNoel());
        BaseMod.addCard(new CallAkaiHaato());
        BaseMod.addCard(new CallShirakamiFubuki());
        BaseMod.addCard(new CallRoboco());
        BaseMod.addCard(new CallAkiRosenthal());
        BaseMod.addCard(new CallNakiriAyame());
        BaseMod.addCard(new CallOzoraSubaru());
        BaseMod.addCard(new CallSakuraMiko());
        BaseMod.addCard(new CallShiranuiFlare());
        BaseMod.addCard(new CallShiroganeNoel());
        BaseMod.addCard(new CallUruhaRushia());
        BaseMod.addCard(new CallYozoraMel());
        BaseMod.addCard(new CallHimemoriLuna());
        BaseMod.addCard(new CallTsunomakiWatame());
        BaseMod.addCard(new CallTokoyamiTowa());
        BaseMod.addCard(new CallAmaneKanata());
        BaseMod.addCard(new CallAZKi());
        BaseMod.addCard(new CallSpadeEcho());
        BaseMod.addCard(new CallCivia());
        BaseMod.addCard(new CallYogiri());
        BaseMod.addCard(new CallOkamiMio());
        BaseMod.addCard(new CallHoshimachiSuisei());
        BaseMod.addCard(new CallNekomataOkayu());
        BaseMod.addCard(new CallRosalyn());
        BaseMod.addCard(new CallArtia());
        BaseMod.addCard(new CallDoris());
        BaseMod.addCard(new CallAyundaRisu());
        BaseMod.addCard(new CallMoonaHoshinova());
        BaseMod.addCard(new CallAiraniIofifteen());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Strike());

        BaseMod.addCard(new test());

        BaseMod.addCard(new BlondHairCombinationOption());
        BaseMod.addCard(new CatDogCombinationOption());
        BaseMod.addCard(new FamsCombinationOption());
        BaseMod.addCard(new GamersCombinationOption());
        BaseMod.addCard(new HappyFamilyCombinationOption());
        BaseMod.addCard(new ManjiCombinationOption());
        BaseMod.addCard(new NatsuiroFubukiCombinationOption());
        BaseMod.addCard(new NewCatDogCombinationOption());
        BaseMod.addCard(new PekoMikoCombinationOption());
        BaseMod.addCard(new AquaMarineCombinationOption());
        BaseMod.addCard(new SoraACombinationOption());
        BaseMod.addCard(new NoelFlareCombinationOption());
        BaseMod.addCard(new MatsuriShionCombinationOption());
        BaseMod.addCard(new ZeroCombinationOption());
        BaseMod.addCard(new OneCombinationOption());
        BaseMod.addCard(new TwoCombinationOption());
        BaseMod.addCard(new ThreeCombinationOption());
        BaseMod.addCard(new FourCombinationOption());
        BaseMod.addCard(new IdolCombinationOption());
        BaseMod.addCard(new AllCombinationOption());
        BaseMod.addCard(new WatameHaatoCombinationOption());
        BaseMod.addCard(new MatsuriLunaCombinationOption());
        BaseMod.addCard(new CocoKanataCombinationOption());
        BaseMod.addCard(new CNTwoCombinationOption());
        BaseMod.addCard(new IDCombinationOption());
        BaseMod.addCard(new ChallengeYagooBigFamily());
        BaseMod.addCard(new ChallengeOneManArmy());
        BaseMod.addCard(new ChallengeTopUpMoney());
        BaseMod.addCard(new ChallengeYo());
        BaseMod.addCard(new ChallengeChaos());
        BaseMod.addCard(new ChallengeKindergarten());
        BaseMod.addCard(new ChallengeTheOnlyOne());


        BaseMod.addCard(new RevenueRecovery());
    }

    public void receiveEditRelics(){
        BaseMod.addRelicToCustomPool(new TheStar(),Hololive_BLUE);
        BaseMod.addRelic(new OneManArmy(), RelicType.SHARED);
        BaseMod.addRelic(new YagooBigFamily(), RelicType.SHARED);
        BaseMod.addRelic(new TopUpMoney(), RelicType.SHARED);
        BaseMod.addRelic(new Yo(), RelicType.SHARED);
        BaseMod.addRelic(new Chaos(), RelicType.SHARED);
        BaseMod.addRelic(new Kindergarten(), RelicType.SHARED);
        BaseMod.addRelic(new TheOnlyOne(), RelicType.SHARED);


        BaseMod.addRelic(new TopUpMoneyS(), RelicType.SHARED);
        BaseMod.addRelic(new ChaosS(), RelicType.SHARED);

        BaseMod.addRelic(new LightStick(),RelicType.SHARED);
        BaseMod.addRelic(new Finger(),RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new FriedShrimpLion(),Hololive_BLUE);

    }

    public void receiveEditKeywords() {
        if (language == Settings.GameLanguage.ZHS) {
            BaseMod.addKeyword(new String[]{"队友"},"队友会在你回合结束时攻击，并且你受伤时队友代替你受伤。");
            BaseMod.addKeyword(new String[]{"攻击"},"你的回合结束时，所有队友会发动攻击。");
            BaseMod.addKeyword(new String[]{"追击"}, "在该队友攻击时会产生一些效果。");
            BaseMod.addKeyword(new String[]{"结尾"}, "在该队友死亡时会产生一些效果。");
            BaseMod.addKeyword(new String[]{"开幕"}, "在该队友从手牌入场时会产生一些效果。");
            BaseMod.addKeyword(new String[]{"受伤"}, "每当该队友受到攻击，产生一些效果。");
            BaseMod.addKeyword(new String[]{"选择"}, "从几张卡牌中选择一张，产生一些效果。默认为3张。");
            BaseMod.addKeyword(new String[]{"魔法"}, "选择并产生效果，诗音在场效果增强。不受aki影响。");
            BaseMod.addKeyword(new String[]{"程序"}, "抽到时消耗，如果抽牌堆不为空则抽一张牌。");
            BaseMod.addKeyword(new String[]{"嘲讽"}, "具有嘲讽的队友成为优先攻击的对象之一。");
            BaseMod.addKeyword(new String[]{"燃烧"}, "每死亡一个队友，每有1层受到1点伤害。");
            BaseMod.addKeyword(new String[]{"被追猎"}, "回合结束时受到等同于负面效果种数5倍的伤害。");
            BaseMod.addKeyword(new String[]{"组合"}, "当你卡组拥有特定队友时，产生特殊效果。");
            BaseMod.addKeyword(new String[]{"卍组成员"}, "湊阿库娅，百鬼绫目，紫咲诗音");
            BaseMod.addKeyword(new String[]{"索兰歌声"}, "你每召唤一个队友，每有一层使其+1/+1。");
            BaseMod.addKeyword(new String[]{"耐久直播"}, "你回合结束时，每有一层所有队友+1生命值。");
            BaseMod.addKeyword(new String[]{"组合：卍组"}, " #b湊阿库娅 + #b紫咲诗音 + #b百鬼绫目 ");
            BaseMod.addKeyword(new String[]{"组合：夏色吹雪"}, " #b夏色祭 + #b白上吹雪 ");
            BaseMod.addKeyword(new String[]{"组合：兔龙"}, " #b兔田佩克拉 + #b樱巫女 ");
            BaseMod.addKeyword(new String[]{"组合：Gamers"}, " #b白上吹雪 + #b大神澪 + #b猫又小粥 + #b戌神沁音 ");
            BaseMod.addKeyword(new String[]{"组合：吉祥三宝"}, " #b夜雾 + #b希薇娅 + #b黑桃影 ");
            BaseMod.addKeyword(new String[]{"组合：金发组"}, " #b赤井心 + #b亚奇罗森 + #b愈月巧可 + #b夜空梅露 + #b不知火芙蕾雅 + #b角卷绵芽 ");
            BaseMod.addKeyword(new String[]{"组合：FAMS"}, " #b白上吹雪 + #b大神澪 + #b大空昴 + #b百鬼绫目 ");
            BaseMod.addKeyword(new String[]{"组合：旧猫狗"}, " #b白上吹雪 + #b大神澪 ");
            BaseMod.addKeyword(new String[]{"组合：新猫狗"}, " #b猫又小粥 + #b戌神沁音 ");
            BaseMod.addKeyword(new String[]{"组合：海蓝宝石"}, " #b湊阿库娅 + #b宝钟玛琳 ");
            BaseMod.addKeyword(new String[]{"组合：空A"}, " #b时乃空 + #b友人A ");
            BaseMod.addKeyword(new String[]{"组合：银火"}, " #b白银诺艾尔 + #b不知火芙蕾雅 ");
            BaseMod.addKeyword(new String[]{"组合：夏紫"}, " #b夏色祭 + #b紫咲诗音 ");
            BaseMod.addKeyword(new String[]{"组合：零期生"}, " #b时乃空 + #b友人A + #b萝卜子 + #b樱巫女 + #b星街彗星 ");
            BaseMod.addKeyword(new String[]{"组合：一期生"}, " #b夜空梅露 + #b赤井心 + #b亚奇罗森 + #b白上吹雪 + #b夏色祭 ");
            BaseMod.addKeyword(new String[]{"组合：二期生"}, " #b大空昴 + #b百鬼绫目 + #b湊阿库娅 + #b紫咲诗音 + #b愈月巧可 ");
            BaseMod.addKeyword(new String[]{"组合：三期生"}, " #b兔田佩克拉 + #b润羽露西娅 + #b不知火芙蕾雅 + #b宝钟玛琳 + #b白银诺艾尔 ");
            BaseMod.addKeyword(new String[]{"组合：四期生"}, " #b角卷绵芽 + #b常暗永远 + #b桐生可可 + #b天音彼方 + #b姬森璐娜 ");
            BaseMod.addKeyword(new String[]{"组合：MUSIC"}, " #bAZKi + #b星街彗星 ");
            BaseMod.addKeyword(new String[]{"组合：全员"}, " #y需要每一个！暂时没效果 ");
            BaseMod.addKeyword(new String[]{"组合：心羊"}, " #b赤井心 + #b角卷绵芽 ");
            BaseMod.addKeyword(new String[]{"组合：夏娜"}, " #b夏色祭 + #b姬森璐娜 ");
            BaseMod.addKeyword(new String[]{"组合：天龙"}, " #b天音彼方 + #b桐生可可 ");
            BaseMod.addKeyword(new String[]{"组合：CN二期生"}, " #b阿媂娅 + #b朵莉丝 + #b罗莎琳 ");
            BaseMod.addKeyword(new String[]{"组合：Hololive-ID"}, " #bAyundaRisu + #bMoonaHoshinova + #bAiraniIofifteen ");
        } else if(language == Settings.GameLanguage.JPN){
            BaseMod.addKeyword(new String[]{"Vtuver","vtuver"},"Vtuversはあなたのターンの終わりに攻撃し、あなたのためにダメージを取る。");
            BaseMod.addKeyword(new String[]{"追撃","pursuit"}, "Vtuverが攻撃するとき、何かをトリガーします。");
            BaseMod.addKeyword(new String[]{"終了","endlive"}, "vtuverが退場する時、何かをトリガーします。");
            BaseMod.addKeyword(new String[]{"開始","startlive"}, "手札から出る時、何かをトリガーします。");
            BaseMod.addKeyword(new String[]{"反撃","hurt"}, "攻撃するとき、何かをトリガーします。");
            BaseMod.addKeyword(new String[]{"選択","choose"}, "いくつかのカードから選択し、何かをトリガします。");
            BaseMod.addKeyword(new String[]{"魔術","magic"}, "効果を選択します。詩音を持つと効果が高まる。akiに影響されない。");
            BaseMod.addKeyword(new String[]{"プログラム","program"}, "引き出したら消耗します。カードの山を空ければ、カードを一枚引く。");
            BaseMod.addKeyword(new String[]{"挑発","taunt"}, "最初に損害を受けます。");
            BaseMod.addKeyword(new String[]{"炎上","flare"}, "vtuverが退場する時、1ダメージを受ける。");
            BaseMod.addKeyword(new String[]{"狩り対象","hunted"}, "ターン終了時に、あなたが持っている各デバフをに対して5ダメージを受ける。");
            BaseMod.addKeyword(new String[]{"てぇてぇ","combination"}, "デッキにある特定の組み合わせ。");
            BaseMod.addKeyword(new String[]{"卍組","manjimember"}, "湊あくあ、百鬼あやめ、紫咲シオン");
            BaseMod.addKeyword(new String[]{"歌唱","singing"}, "召喚するたびに+ 1 /+ 1を与える。");
            BaseMod.addKeyword(new String[]{"耐久配信","durablelive"}, "あなたのターンの終わりに、すべてのvtuversに+ 1体力を与える。");
            BaseMod.addKeyword(new String[]{"てぇてぇ:卍組"}, " #b湊あくあ + #b紫咲シオン + #b百鬼あやめ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:夏色吹雪"}, " #b夏色まつり + #b白上フブキ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ぺこみこ"}, " #b兎田ぺこら + #bさくらみこ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ゲーマーズ"}, " #b白上フブキ + #b大神ミオ + #b戌神ころね + #b猫又おかゆ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ラッキートリオ"}, " #b夜霧 + #bCivia + #b黒桃影 ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:金髪組"}, " #b赤井はあと + #bアキロゼ + #b癒月ちょこ + #b夜空メル + #b角巻わため + #b不知火フレア ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:FAMS"}, " #b白上フブキ + #b大神ミオ + #b百鬼あやめ + #b大空スバル ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:フブミオ"}, " #b白上フブキ + #b大神ミオ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:おかころ"}, " #b戌神ころね + #b猫又おかゆ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:あくあマリン"}, " #b湊あくあ + #b宝鐘マリン ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:そらえー"}, " #bときのそら + #b友人A ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ノエフレ"}, " #b白銀ノエル + #b不知火フレア ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:シオり"}, " #b夏色まつり + #b紫咲シオン ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:零期生"}, " #bときのそら + #b友人A + #bロボ子さん + #bさくらみこ + #b星街すいせい ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:一期生"}, " #b夜空メル + #b赤井はあと + #bアキロゼ + #b白上フブキ + #b夏色まつり ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:二期生"}, " #b大空スバル + #b百鬼あやめ + #b湊あくあ + #b紫咲シオン + #b癒月ちょこ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:三期生"}, " #b兎田ぺこら + #b潤羽るしあ + #b不知火フレア + #b宝鐘マリン + #b白銀ノエル ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:四期生"}, " #b角巻わため + #b常闇トワ + #b桐生ココ + #b天音かなた + #b姫森ルーナ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:MUSIC"}, " #bAZKi + #b星街すいせい ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:全員"}, " #yEveryone!no #yeffect #ycurrently ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ラムポーク"}, " #b角巻わため + #b赤井はあと ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:フェスティバルーナ"}, " #b夏色まつり + #b姫森ルーナ ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:収益化NG組"}, " #b桐生ココ + #b天音かなた ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ホロ中国2期"}, " #bアティア + #bドリス + #bロサリン ");
            BaseMod.addKeyword(new String[]{"てぇてぇ:ホロライブID"}, " #bリス + #bムーナ + #bイオフィ ");
        } else {
            BaseMod.addKeyword(new String[]{"Vtuber","vtuber"},"Vtubers will attack at the end of your turn,and take damage for you.");
            BaseMod.addKeyword(new String[]{"Pursuit","pursuit"}, "When vtuber attacks,trigger something.");
            BaseMod.addKeyword(new String[]{"EndLive","endlive"}, "When vtuber died,trigger something.");
            BaseMod.addKeyword(new String[]{"StartLive","startlive"}, "When played from hand,trigger something.");
            BaseMod.addKeyword(new String[]{"Hurt","hurt"}, "When attacked,trigger something.");
            BaseMod.addKeyword(new String[]{"Choose","choose"}, "Choose from some cards and trigger something.");
            BaseMod.addKeyword(new String[]{"Magic","magic"}, "Choose and enhanced when you control Shion.Cant be affected by Aki. ");
            BaseMod.addKeyword(new String[]{"Program","program"}, "Exhaust when drawn,and draw 1 card if draw pile is not empty.");
            BaseMod.addKeyword(new String[]{"Taunt","taunt"}, "Vtubers with taunt receive damage first.");
            BaseMod.addKeyword(new String[]{"Flare","flare"}, "Whenever a vtuber died,receive 1 damage.");
            BaseMod.addKeyword(new String[]{"Hunted","hunted"}, "At the end of turn,receive 5 damage for each debuff you have.");
            BaseMod.addKeyword(new String[]{"Combination","combination"}, "When you have specific cards in your master deck,trigger something.");
            BaseMod.addKeyword(new String[]{"ManjiMember","manjimember"}, "aqua,ayame,shion");
            BaseMod.addKeyword(new String[]{"Singing","singing"}, "Whenever you summon,give it +1/+1.");
            BaseMod.addKeyword(new String[]{"DurableLive","durablelive"}, "At the end of your turn,give +1 health to all vtubers.");
            BaseMod.addKeyword(new String[]{"Combination:Manji"}, " #bMinatoAqua + #bMurasakiShion + #bNakiriAyame ");
            BaseMod.addKeyword(new String[]{"Combination:NatsuiroFubuki"}, " #bNatsuiroMatsuri + #bShirakamiFubuki ");
            BaseMod.addKeyword(new String[]{"Combination:PekoMiko"}, " #bUsadaPekora + #bSakuraMiko ");
            BaseMod.addKeyword(new String[]{"Combination:Gamers"}, " #bShirakamiFubuki + #bOkamiMio + #bInugamiKorone + #bNekomataOkayu ");
            BaseMod.addKeyword(new String[]{"Combination:HappyFamily"}, " #bYogiri + #bCivia + #bSpadeEcho ");
            BaseMod.addKeyword(new String[]{"Combination:BlondHair"}, " #bAkaiHaato + #bAkiRosenthal + #bYuzukiChoco + #bYzoraMel + #bTsunomakiWatame + #bShiranuiFlare ");
            BaseMod.addKeyword(new String[]{"Combination:FAMS"}, " #bShirakamiFubuki + #bOkamiMio + #bNakiriAyame + #bOzoraSubaru ");
            BaseMod.addKeyword(new String[]{"Combination:CatDog"}, " #bShirakamiFubuki + #bOkamiMio ");
            BaseMod.addKeyword(new String[]{"Combination:NewCatDog"}, " #bInugamiKorone + #bNekomataOkayu ");
            BaseMod.addKeyword(new String[]{"Combination:AquaMarine"}, " #bMinatoAqua + #bHoushouMarine ");
            BaseMod.addKeyword(new String[]{"Combination:SoraA"}, " #bTokinoSora + #bFriendA ");
            BaseMod.addKeyword(new String[]{"Combination:NoelFlare"}, " #bShiroganeNoel + #bShiranuiFlare ");
            BaseMod.addKeyword(new String[]{"Combination:MatsuriShion"}, " #bNatsuiroMatsuri + #bMurasakiShion ");
            BaseMod.addKeyword(new String[]{"Combination:Zero"}, " #bTokinoSora + #bFriendA + #bRoboco + #bSakuraMiko + #bHoshimachiSuisei ");
            BaseMod.addKeyword(new String[]{"Combination:One"}, " #bYozoraMel + #bAkaiHaato + #bAkiRosenthal + #bShirakamiFubuki + #bNatsuiroMatsuri ");
            BaseMod.addKeyword(new String[]{"Combination:Two"}, " #bOzoraSubaru + #bNakiriAyame + #bMinatoAqua + #bMurasakiShion + #bYuzukiChoco ");
            BaseMod.addKeyword(new String[]{"Combination:Three"}, " #bUsadaPekora + #bUruhaRushia + #bShiranuiFlare + #bHoushouMarine + #bShirogameNoel ");
            BaseMod.addKeyword(new String[]{"Combination:Four"}, " #bTsunomakiWatame + #bTokoyamiTowa + #bKiryuuCoco + #bAmaneKanata + #bHimemoriLuna ");
            BaseMod.addKeyword(new String[]{"Combination:MUSIC"}, " #bAZKi + #bHoshimachiSuisei ");
            BaseMod.addKeyword(new String[]{"ALL MEMBER"}, " #yEveryone!no #yeffect #ycurrently ");
            BaseMod.addKeyword(new String[]{"Combination:Watame&Haato"}, " #bTsunomakiWatame + #bAkaiHaato ");
            BaseMod.addKeyword(new String[]{"Combination:Matsuri&Luna"}, " #bNatsuiroMatsuri + #bHimemoriLuna ");
            BaseMod.addKeyword(new String[]{"Combination:Coco&Kanata"}, " #bKiryuuCoco + #bAmaneKanata ");
            BaseMod.addKeyword(new String[]{"Combination:CN Two"}, " #bArtia + #bDoris + #bRosalyn ");
            BaseMod.addKeyword(new String[]{"Combination:Hololive-ID"}, " #bAyundaRisu + #bMoonaHoshinova + #bAiraniIofifteen ");

        }

    }



    public int receiveOnPlayerDamaged(int amount, DamageInfo info) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.orbs.size() > 0 ) {
            boolean overBlock = false;
            dmg = amount;
            if(AbstractDungeon.player.currentBlock > 0){
                if(dmg - AbstractDungeon.player.currentBlock <= 0) {
                    AbstractDungeon.player.loseBlock(dmg);
                    return 0;
                } else {
                    dmg -= AbstractDungeon.player.currentBlock;
                    if(AbstractDungeon.player.hasPower("Plated Armor"))
                        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player,AbstractDungeon.player, "Plated Armor", 1));
                    overBlock = true;
                }
            }
            logger.info("先攻击嘲讽队友");
            onAttackAssitTaunt();
            onAttackAssit();
            try{
                return dmg;
            }finally {
                if(overBlock)
                    AbstractDungeon.player.loseBlock(AbstractDungeon.player.currentBlock);
            }

        }
        return amount;
    }
    private void onAttackAssit(){
        logger.info("onAttackAssit");
        int j = 0;
            AbstractOrb orb = AbstractDungeon.player.orbs.get(j);
            logger.info("onAttackAssit：选取" + orb.name);
            if(orb instanceof AbstractMinion){
                AbstractMinion student = (AbstractMinion) orb;
                logger.info("onAttackAssit：现在受到伤害的是" + student.name);
                if (student.HP > this.dmg){
                    student.ChangeHP(-this.dmg,true);
                    student.onDamaged(this.dmg,true);
                    this.dmg = 0;
                    logger.info("onAttackAssit:队友扛下了伤害");
                } else {
                    student.onDamaged(student.HP,true);
                    this.dmg -= student.HP;
                    student.HP = 0;
                    student.isdead = true;
                    if(TheStar.CombinationIndex[24])
                        student.AttackEffect();
                    if(student instanceof AiraniIofifteen){
                        dmg = 0;
                    }
                    student.onEvoke();
                    logger.info("onAttackAssit:队友没扛下伤害，死了");
                    AbstractOrb orbSlot = new EmptyOrbSlot();
                    int i;
                    for (i = 1; i < AbstractDungeon.player.orbs.size(); ++i) {
                        Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
                    }
                    AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
                    for (i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
                        AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
                    }
                    onAttackAssit();
                }
            }

    }
    private void onAttackAssitTaunt() {
        logger.info("onAttackAssitTaunt");
        int j;
        for(j = 0;j<AbstractDungeon.player.orbs.size();++j) {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(j);
            logger.info("onAttackAssitTaunt：选取" + orb.name);
            if (orb instanceof AbstractMinion &&  ((AbstractMinion) orb).Taunt) {
                logger.info("onAttackAssitTaunt：现在受到伤害的是" + orb.name);
                if (((AbstractMinion) orb).HP > this.dmg) {
                    ((AbstractMinion) orb).ChangeHP(-this.dmg,true);
                    ((AbstractMinion) orb).onDamaged(this.dmg,true);
                    this.dmg = 0;
                    logger.info("onAttackAssitTaunt:队友扛下了伤害");
                    return;
                } else {
                    ((AbstractMinion) orb).onDamaged(((AbstractMinion) orb).HP,true);
                    this.dmg -= ((AbstractMinion) orb).HP;
                    ((AbstractMinion) orb).HP = 0;
                    ((AbstractMinion) orb).isdead = true;
                    if(TheStar.CombinationIndex[24])
                        ((AbstractMinion) orb).AttackEffect();
                    if(orb instanceof AiraniIofifteen){
                        dmg = 0;
                    }
                    orb.onEvoke();
                    logger.info("onAttackAssitTaunt:队友没扛下伤害，死了");
                    AbstractOrb orbSlot = new EmptyOrbSlot();
                    int i;
                    for (i = j + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
                        Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
                    }
                    AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);
                    for (i = j; i < AbstractDungeon.player.orbs.size(); ++i) {
                        AbstractDungeon.player.orbs.get(i).setSlot(i, AbstractDungeon.player.maxOrbs);
                    }
                    onAttackAssitTaunt();
                }
                break;
            }
        }
    }

    public void receiveCardUsed(AbstractCard c) {
            if (AbstractDungeon.player instanceof HololiveCharacter) {
                if (c instanceof AbstractSummonCard) {
                    CardHelper.UsedSummonCardsThisBattle.add((AbstractSummonCard) c.makeCopy());
                    }
                }
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof AbstractMinion) {
                    ((AbstractMinion) orb).onCardUse(c);
                }
            }
        }

    public void receiveOnBattleStart(AbstractRoom room){
        OrbHelper.CallMinionsThisBattle = 0;
        OrbHelper.DeadMinionsThisBattle.clear();
        CardHelper.UsedSummonCardsThisBattle.clear();
        if(CanAWSL)
            for(AbstractCard card:AbstractDungeon.player.masterDeck.group) {
                if(card instanceof AWSL ) {
                    AbstractDungeon.player.increaseMaxHp(1,true);
                }
            }
        this.CanAWSL = false;
    }

    public void receivePostBattle(AbstractRoom var1){
        this.CanAWSL = true;
    }

    public void receivePostInitialize() {
        try {
            this.CreatePanel();
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public void receivePostDungeonInitialize(){
        if(AbstractDungeon.player instanceof HololiveCharacter){
            if(CharacterSelectPatch.DeckIndex == 11){
                obtain(AbstractDungeon.player,new Peko(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 10){
                obtain(AbstractDungeon.player,new TheOnlyOne(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 9){
                obtain(AbstractDungeon.player,new Kindergarten(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 8){
                obtain(AbstractDungeon.player,new Chaos(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 7){
                obtain(AbstractDungeon.player,new Yo(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 6){
                obtain(AbstractDungeon.player,new TopUpMoney(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 5){
                obtain(AbstractDungeon.player,new OneManArmy(),false);
            }
            if(CharacterSelectPatch.DeckIndex == 4){
                obtain(AbstractDungeon.player,new YagooBigFamily(),false);
            }
        }
        AbstractDungeon.uncommonRelicPool.remove("Mummified Hand");
        AbstractDungeon.bossRelicPool.remove("Hololive_TopUpMoneyS");
        AbstractDungeon.bossRelicPool.remove("Hololive_ChaosS");
        if(UnlockTracker.isCardSeen("Hololive_ChallengeTopUpMoney")){
            AbstractDungeon.bossRelicPool.add("Hololive_TopUpMoneyS");
        }
        if(UnlockTracker.isCardSeen("Hololive_ChallengeChaos")){
            AbstractDungeon.bossRelicPool.add("Hololive_ChaosS");
        }

    }

    private void CreatePanel() throws IOException {
        Texture badgeTexture = new Texture(Gdx.files.internal("img/Hololive.png"));
        SpireConfig spireConfig = new SpireConfig("HololiveMod", "Common");
        ModPanel settingsPanel = new ModPanel();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BALANCE_MODE");
        String[] TEXT = uiStrings.TEXT;
        ModLabeledToggleButton BalanceMode = new ModLabeledToggleButton(TEXT[0], 500.0F, 500.0F, Settings.BLUE_TEXT_COLOR, FontHelper.charDescFont, isBalance, settingsPanel, (label) -> {}
        ,(button) -> {
            spireConfig.setBool("BalanceMode", isBalance = button.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());

            try {
                spireConfig.save();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        });
        settingsPanel.addUIElement(BalanceMode);
        BaseMod.registerModBadge(badgeTexture, "HololiveMod", "REME", TEXT[1], settingsPanel);
    }

    public static boolean obtain(AbstractPlayer p, AbstractRelic r, boolean canDuplicate) {
        if (r == null) {
            return false;
        } else if (p.hasRelic(r.relicId) && !canDuplicate) {
            return false;
        } else {
            int slot = p.relics.size();
            r.makeCopy().instantObtain(p, slot, true);
            return true;
        }
    }

    private static Color getColor(float r, float g, float b) {
        return new Color(r / 255.0F, g / 255.0F, b / 255.0F, 1.0F);
    }

}