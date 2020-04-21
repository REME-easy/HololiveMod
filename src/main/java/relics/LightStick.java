package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LightStick extends CustomRelic {
    public static final String ID = "Hololive_LightStick";
    public static final String[] allmates;
    static {
        String[] mates = {
                "AkaiHaato","AkiRosenthal","AmaneKanata","AZKi","Civia","FriendA","HimemoriLuna","HoshimachiSuisei","HoushouMarine","InugamiKorone",
                "KiryuuCoco","MinatoAqua","MurasakiShion","NakiriAyame","NatsuiroMatsuri","NekomataOkayu","OkamiMio","OzoraSubaru","Roboco",
                "SakuraMiko","ShirakamiFubuki","ShiranuiFlare","ShiroganeNoel","SpadeEcho","TokinoSora","TokoyamiTowa","TsunomakiWatame",
                "UruhaRushia","UsadaPekora","Yogiri","YozoraMel","YuzukiChoco","Doris","Artia","Rosalyn","AiraniIofifteen","AyundaRisu","MoonaHoshinova"
        };
        int i;
        for(i = 0 ; i < mates.length ; ++i){
            mates[i] = "cards.summonCard.Call".concat(mates[i]);
        }
        allmates = mates;
    }
    public LightStick() {
        super(ID, ImageMaster.loadImage("img/relics/LightStick.png"),
                RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void atBattleStart() {
        if(AbstractDungeon.player.maxOrbs <= 0){
            this.addToBot(new IncreaseMaxOrbAction(1));
        }
        AbstractCard card = null;
        try{
            card = (AbstractCard) Class.forName(allmates[AbstractDungeon.cardRandomRng.random(0,allmates.length - 1)]).newInstance();
        }catch (ClassNotFoundException var2){
            var2.printStackTrace();
        }catch (IllegalAccessException var3){
            var3.printStackTrace();
        }catch (InstantiationException var5){
            var5.printStackTrace();
        }
        if(card != null){
            card.use(AbstractDungeon.player,null);
        }
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new LightStick();
    }
}
