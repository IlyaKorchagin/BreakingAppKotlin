package com.example.goodfootbreaking.activities

import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubePlayer
import android.os.Bundle
import com.example.goodfootbreaking.R
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubeInitializationResult
import android.widget.Toast
import com.example.goodfootbreaking.databinding.ActivityDescriptionBinding
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener

//public class DescriptionActivity extends AppCompatActivity {
class DescriptionActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private var _binding: ActivityDescriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var youTubePlayerView: YouTubePlayerView

    var rating: Int? = null
    var element: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDescriptionBinding.inflate(layoutInflater)
        val view = binding.root
        binding.handler = this
        setContentView(view)

        val intent = intent

        binding.elementTitle.text = intent.getStringExtra(DESCRIPTION_MESSAGE)
        element = binding.elementTitle.text.toString()

        /** Initializing YouTube Player View  */
        youTubePlayerView = binding.youtubePlayer
        youTubePlayerView.initialize(API_KEY, this)

        rating = intent.getIntExtra(DESCRIPTION_RATING, 0)
        when(rating!!) {
            in 0..9   -> setElementStatus(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            in 10..19 -> setElementStatus(1, 0, 0, 0, 0, 0, 0, 0, 0, 0)
            in 20..29 -> setElementStatus(1, 1, 0, 0, 0, 0, 0, 0, 0, 0)
            in 30..39 -> setElementStatus(1, 1, 1, 0, 0, 0, 0, 0, 0, 0)
            in 40..49 -> setElementStatus(1, 1, 1, 1, 0, 0, 0, 0, 0, 0)
            in 50..59 -> setElementStatus(1, 1, 1, 1, 1, 0, 0, 0, 0, 0)
            in 60..69 -> setElementStatus(1, 1, 1, 1, 1, 1, 0, 0, 0, 0)
            in 70..79 -> setElementStatus(1, 1, 1, 1, 1, 1, 1, 0, 0, 0)
            in 80..89 -> setElementStatus(1, 1, 1, 1, 1, 1, 1, 1, 0, 0)
            in 90..99 -> setElementStatus(1, 1, 1, 1, 1, 1, 1, 1, 1, 0)
            100       -> setElementStatus(1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        }
        when (element) {
            "Baby Frezze" -> {
                setDescription(R.string.baby10percent,R.string.baby20percent,R.string.baby30percent,R.string.baby40percent,R.string.baby50percent,
                    R.string.baby60percent,R.string.baby70percent,R.string.baby80percent,R.string.baby90percent,R.string.baby100percent)
            }
            "Chair Frezze" -> {
                setDescription(R.string.chair10percent,R.string.chair20percent,R.string.chair30percent,R.string.chair40percent,R.string.chair50percent,
                    R.string.chair60percent,R.string.chair70percent,R.string.chair80percent,R.string.chair90percent,R.string.chair100percent)
            }
            "Turtel Frezze" -> {
                setDescription(R.string.turtelfrezze10percent,R.string.turtelfrezze20percent,R.string.turtelfrezze30percent,R.string.turtelfrezze40percent,R.string.turtelfrezze50percent,
                    R.string.turtelfrezze60percent,R.string.turtelfrezze70percent,R.string.turtelfrezze80percent,R.string.turtelfrezze90percent,R.string.turtelfrezze100percent)
            }
            "Shoulder Frezze" -> {
                setDescription(R.string.shoulderfrezze10percent,R.string.shoulderfrezze20percent,R.string.shoulderfrezze30percent,R.string.shoulderfrezze40percent,R.string.shoulderfrezze50percent,
                    R.string.shoulderfrezze60percent,R.string.shoulderfrezze70percent,R.string.shoulderfrezze80percent,R.string.shoulderfrezze90percent,R.string.shoulderfrezze100percent)
            }
            "Head Frezze" -> {
                setDescription(R.string.headfrezze10percent,R.string.headfrezze20percent,R.string.headfrezze30percent,R.string.headfrezze40percent,R.string.headfrezze50percent,
                    R.string.headfrezze60percent,R.string.headfrezze70percent,R.string.headfrezze80percent,R.string.headfrezze90percent,R.string.headfrezze100percent)
            }
            "Elbow Frezze" -> {
                setDescription(R.string.elbow10percent,R.string.elbow20percent,R.string.elbow30percent,R.string.elbow40percent,R.string.elbow50percent,
                    R.string.elbow60percent,R.string.elbow70percent,R.string.elbow80percent,R.string.elbow90percent,R.string.elbow100percent)
            }
            "HeadHollowback Frezze" -> {
                setDescription(R.string.headhollowback10percent,R.string.headhollowback20percent,R.string.headhollowback30percent,R.string.headhollowback40percent,R.string.headhollowback50percent,
                    R.string.headhollowback60percent,R.string.headhollowback70percent,R.string.headhollowback80percent,R.string.headhollowback90percent,R.string.headhollowback100percent)
            }
            "Onehand Frezze" -> {
                setDescription(R.string.onehand10percent,R.string.onehand20percent,R.string.onehand30percent,R.string.onehand40percent,R.string.onehand50percent,
                    R.string.onehand60percent,R.string.onehand70percent,R.string.onehand80percent,R.string.onehand90percent,R.string.onehand100percent)
            }
            "Invert Frezze" -> {
                setDescription(R.string.invert10percent,R.string.invert20percent,R.string.invert30percent,R.string.invert40percent,R.string.invert50percent,
                    R.string.invert60percent,R.string.invert70percent,R.string.invert80percent,R.string.invert90percent,R.string.invert100percent)
            }
            "Hollowback Frezze" -> {
                setDescription(R.string.hollowback10percent,R.string.hollowback20percent,R.string.hollowback30percent,R.string.hollowback40percent,R.string.hollowback50percent,
                    R.string.hollowback60percent,R.string.hollowback70percent,R.string.hollowback80percent,R.string.hollowback90percent,R.string.hollowback100percent)
            }
            "Backspin" -> {
                setDescription(R.string.backspin10percent,R.string.backspin20percent,R.string.backspin30percent,R.string.backspin40percent,R.string.backspin50percent,
                    R.string.backspin60percent,R.string.backspin70percent,R.string.backspin80percent,R.string.backspin90percent,R.string.backspin100percent)
            }
            "Swipes" -> {
                setDescription(R.string.swipes10percent,R.string.swipes20percent,R.string.swipes30percent,R.string.swipes40percent,R.string.swipes50percent,
                    R.string.swipes60percent,R.string.swipes70percent,R.string.swipes80percent,R.string.swipes90percent,R.string.swipes100percent)
            }
            "TurtelMove" -> {
                setDescription(R.string.turtel10percent,R.string.turtel20percent,R.string.turtel30percent,R.string.turtel40percent,R.string.turtel50percent,
                    R.string.turtel60percent,R.string.turtel70percent,R.string.turtel80percent,R.string.turtel90percent,R.string.turtel100percent)
            }
            "Windmill" -> {
                setDescription(R.string.windmill10percent,R.string.windmill20percent,R.string.windmill30percent,R.string.windmill40percent,R.string.windmill50percent,
                    R.string.windmill60percent,R.string.windmill70percent,R.string.windmill80percent,R.string.windmill90percent,R.string.windmill100percent)
            }
            "Headspin" -> {
                setDescription(R.string.headspin10percent,R.string.headspin20percent,R.string.headspin30percent,R.string.headspin40percent,R.string.headspin50percent,
                    R.string.headspin60percent,R.string.headspin70percent,R.string.headspin80percent,R.string.headspin90percent,R.string.headspin100percent)
            }
            "Cricket" -> {
                setDescription(R.string.cricket10percent,R.string.cricket20percent,R.string.cricket30percent,R.string.cricket40percent,R.string.cricket50percent,
                    R.string.cricket60percent,R.string.cricket70percent,R.string.cricket80percent,R.string.cricket90percent,R.string.cricket100percent)
            }
            "Web" -> {
                setDescription(R.string.web10percent,R.string.web20percent,R.string.web30percent,R.string.web40percent,R.string.web50percent,
                    R.string.web60percent,R.string.web70percent,R.string.web80percent,R.string.web90percent,R.string.web100percent)
            }
            "Wolf" -> {
                setDescription(R.string.wolf10percent,R.string.wolf20percent,R.string.wolf30percent,R.string.wolf40percent,R.string.wolf50percent,
                    R.string.wolf60percent,R.string.wolf70percent,R.string.wolf80percent,R.string.wolf90percent,R.string.wolf100percent)
            }
            "Munchmill" -> {
                setDescription(R.string.munchmill10percent,R.string.munchmill20percent,R.string.munchmill30percent,R.string.munchmill40percent,R.string.munchmill50percent,
                    R.string.munchmill60percent,R.string.munchmill70percent,R.string.munchmill80percent,R.string.munchmill90percent,R.string.munchmill100percent)
            }
            "Flare" -> {
                setDescription(R.string.flare10percent,R.string.flare20percent,R.string.flare30percent,R.string.flare40percent,R.string.flare50percent,
                    R.string.flare60percent,R.string.flare70percent,R.string.flare80percent,R.string.flare90percent,R.string.flare100percent)
            }
            "Ninety" -> {
                setDescription(R.string.ninety10percent,R.string.ninety20percent,R.string.ninety30percent,R.string.ninety40percent,R.string.ninety50percent,
                    R.string.ninety60percent,R.string.ninety70percent,R.string.ninety80percent,R.string.ninety90percent,R.string.ninety100percent)
            }
            "Halo" -> {
                setDescription(R.string.halo10percent,R.string.halo20percent,R.string.halo30percent,R.string.halo40percent,R.string.halo50percent,
                    R.string.halo60percent,R.string.halo70percent,R.string.halo80percent,R.string.halo90percent,R.string.halo100percent)
            }
            "Jackhammer" -> {
                setDescription(R.string.jackhammer10percent,R.string.jackhammer20percent,R.string.jackhammer30percent,R.string.jackhammer40percent,R.string.jackhammer50percent,
                    R.string.jackhammer60percent,R.string.jackhammer70percent,R.string.jackhammer80percent,R.string.jackhammer90percent,R.string.jackhammer100percent)
            }
            "Ufo" -> {
                setDescription(R.string.ufo10percent,R.string.ufo20percent,R.string.ufo30percent,R.string.ufo40percent,R.string.ufo50percent,
                    R.string.ufo60percent,R.string.ufo70percent,R.string.ufo80percent,R.string.ufo90percent,R.string.ufo100percent)
            }
            "ElbowAirflare" -> {
                setDescription(R.string.elbowairflare10percent,R.string.elbowairflare20percent,R.string.elbowairflare30percent,R.string.elbowairflare40percent,R.string.elbowairflare50percent,
                    R.string.elbowairflare60percent,R.string.elbowairflare70percent,R.string.elbowairflare80percent,R.string.elbowairflare90percent,R.string.elbowairflare100percent)
            }
            "Airflare" -> {
                setDescription(R.string.airflare10percent,R.string.airflare20percent,R.string.airflare30percent,R.string.airflare40percent,R.string.airflare50percent,
                    R.string.airflare60percent,R.string.airflare70percent,R.string.airflare80percent,R.string.airflare90percent,R.string.airflare100percent)
            }
            "Уголок" -> {
                setDescription(R.string.angle10percent,R.string.angle20percent,R.string.angle30percent,R.string.angle40percent,R.string.angle50percent,
                    R.string.angle60percent,R.string.angle70percent,R.string.angle80percent,R.string.angle90percent,R.string.angle100percent)
            }
            "Мостик" -> {
                setDescription(R.string.bridge10percent,R.string.bridge20percent,R.string.bridge30percent,R.string.bridge40percent,R.string.bridge50percent,
                    R.string.bridge60percent,R.string.bridge70percent,R.string.bridge80percent,R.string.bridge90percent,R.string.bridge100percent)
            }
            "Стойка на пальцах" -> {
                setDescription(R.string.fingers10percent,R.string.fingers20percent,R.string.fingers30percent,R.string.fingers40percent,R.string.fingers50percent,
                    R.string.fingers60percent,R.string.fingers70percent,R.string.fingers80percent,R.string.fingers90percent,R.string.fingers100percent)
            }
            "Стойка на руках" -> {
                setDescription(R.string.handstand10percent,R.string.handstand20percent,R.string.handstand30percent,R.string.handstand40percent,R.string.handstand50percent,
                    R.string.handstand60percent,R.string.handstand70percent,R.string.handstand80percent,R.string.handstand90percent,R.string.handstand100percent)
            }
            "Горизонт" -> {
                setDescription(R.string.horizont10percent,R.string.horizont20percent,R.string.horizont30percent,R.string.horizont40percent,R.string.horizont50percent,
                    R.string.horizont60percent,R.string.horizont70percent,R.string.horizont80percent,R.string.horizont90percent,R.string.horizont100percent)
            }
            "Отжимания" -> {
                setDescription(R.string.pushups10percent,R.string.pushups20percent,R.string.pushups30percent,R.string.pushups40percent,R.string.pushups50percent,
                    R.string.pushups60percent,R.string.pushups70percent,R.string.pushups80percent,R.string.pushups90percent,R.string.pushups100percent)
            }
            "Шпагат" -> {
                setDescription(R.string.twine10percent,R.string.twine20percent,R.string.twine30percent,R.string.twine40percent,R.string.twine50percent,
                    R.string.twine60percent,R.string.twine70percent,R.string.twine80percent,R.string.twine90percent,R.string.twine100percent)
            }
            "Складка" -> {
                setDescription(R.string.fold10percent,R.string.fold20percent,R.string.fold30percent,R.string.fold40percent,R.string.fold50percent,
                    R.string.fold60percent,R.string.fold70percent,R.string.fold80percent,R.string.fold90percent,R.string.fold100percent)
            }
            "Бабочка" -> {
                setDescription(R.string.butterfly10percent,R.string.butterfly20percent,R.string.butterfly30percent,R.string.butterfly40percent,R.string.butterfly50percent,
                    R.string.butterfly60percent,R.string.butterfly70percent,R.string.butterfly80percent,R.string.butterfly90percent,R.string.butterfly100percent)
            }
            "Плечи" -> {
                setDescription(R.string.shoulders10percent,R.string.shoulders20percent,R.string.shoulders30percent,R.string.shoulders40percent,R.string.shoulders50percent,
                    R.string.shoulders60percent,R.string.shoulders70percent,R.string.shoulders80percent,R.string.shoulders90percent,R.string.shoulders100percent)
            }
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        result: YouTubeInitializationResult
    ) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show()
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        /** add listeners to YouTubePlayer instance  */
        player.setPlayerStateChangeListener(playerStateChangeListener)
        player.setPlaybackEventListener(playbackEventListener)
        /** Start buffering  */
        if (!wasRestored) {
            when (element) {
                "Baby Frezze" -> player.cueVideo(BABY_FREZZE_VIDEO_ID)
                "Turtel Frezze" -> player.cueVideo(TURTLE_FREZZE_VIDEO_ID)
                "Shoulder Frezze" -> player.cueVideo(SHOULDER_FREZZE_VIDEO_ID)
                "Head Frezze" -> player.cueVideo(HEAD_FREZZE_VIDEO_ID)
                "Chair Frezze" -> player.cueVideo(CHAIR_FREZZE_VIDEO_ID)
                "Elbow Frezze" -> player.cueVideo(ELBOW_FREZZE_VIDEO_ID)
                "HeadHollowback Frezze" -> player.cueVideo(HEADHOLLOWBACK_FREZZE_VIDEO_ID)
                "Onehand Frezze" -> player.cueVideo(ONEHAND_FREZZE_VIDEO_ID)
                "Invert Frezze" -> player.cueVideo(INVERT_FREZZE_VIDEO_ID)
                "Hollowback Frezze" -> player.cueVideo(HOLLOWBACK_FREZZE_VIDEO_ID)
                "Backspin" -> player.cueVideo(BACKSPIN_VIDEO_ID)
                "Swipes" -> player.cueVideo(SWIPES_VIDEO_ID)
                "TurtelMove" -> player.cueVideo(TURTLE_VIDEO_ID)
                "Windmill" -> player.cueVideo(WINDMILL_VIDEO_ID)
                "Headspin" -> player.cueVideo(HEADSPIN_VIDEO_ID)
                "Cricket" -> player.cueVideo(CRICKET_VIDEO_ID)
                "Web" -> player.cueVideo(WEB_VIDEO_ID)
                "Wolf" -> player.cueVideo(WOLF_VIDEO_ID)
                "Munchmill" -> player.cueVideo(MUNCHMILL_VIDEO_ID)
                "Flare" -> player.cueVideo(FLARE_VIDEO_ID)
                "Ninety" -> player.cueVideo(NINETY_NINE_VIDEO_ID)
                "Halo" -> player.cueVideo(HALO_VIDEO_ID)
                "Jackhammer" -> player.cueVideo(JACKHAMMER_VIDEO_ID)
                "Ufo" -> player.cueVideo(UFO_VIDEO_ID)
                "ElbowAirflare" -> player.cueVideo(ELBOWAIRFLARE_VIDEO_ID)
                "Airflare" -> player.cueVideo(AIRFLARE_VIDEO_ID)
                "Уголок" -> player.cueVideo(ANGLE_VIDEO_ID)
                "Мостик" -> {
                }
                "Стойка на пальцах" -> player.cueVideo(FINGER_VIDEO_ID)
                "Стойка на руках" -> player.cueVideo(HANDSTAND_VIDEO_ID)
                "Горизонт" -> player.cueVideo(HORIZONT_VIDEO_ID)
                "Отжимания" -> player.cueVideo(PUSHUPS_VIDEO_ID)
                "Шпагат" -> {
                }
                "Складка" -> {
                }
                "Бабочка" -> {
                }
                "Плечи" -> {
                }
                else -> player.cueVideo(BABY_FREZZE_VIDEO_ID)
            }
        }
    }

    private val playbackEventListener: PlaybackEventListener = object : PlaybackEventListener {
        override fun onBuffering(arg0: Boolean) {}
        override fun onPaused() {}
        override fun onPlaying() {}
        override fun onSeekTo(arg0: Int) {}
        override fun onStopped() {}
    }
    private val playerStateChangeListener: PlayerStateChangeListener =
        object : PlayerStateChangeListener {
            override fun onAdStarted() {}
            override fun onError(arg0: YouTubePlayer.ErrorReason) {}
            override fun onLoaded(arg0: String) {}
            override fun onLoading() {}
            override fun onVideoEnded() {}
            override fun onVideoStarted() {}
        }
    private fun setDescription(text10:Int,text20:Int,text30:Int,text40:Int,text50:Int,text60:Int,text70:Int,text80:Int,text90:Int,text100:Int){
        binding.title10.setText(text10)
        binding.title20.setText(text20)
        binding.title30.setText(text30)
        binding.title40.setText(text40)
        binding.title50.setText(text50)
        binding.title60.setText(text60)
        binding.title70.setText(text70)
        binding.title80.setText(text80)
        binding.title90.setText(text90)
        binding.title100.setText(text100)
    }

    private fun setElementStatus(status10:Int,status20:Int,status30:Int,status40:Int,status50:Int,status60:Int,status70:Int,status80:Int,status90:Int,status100:Int){

        if (status10>0)binding.title10.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title10.setBackgroundResource(R.drawable.greencorner)

        if (status20>0)binding.title20.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title20.setBackgroundResource(R.drawable.greencorner)

        if (status30>0)binding.title30.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title30.setBackgroundResource(R.drawable.greencorner)

        if (status40>0)binding.title40.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title40.setBackgroundResource(R.drawable.greencorner)

        if (status50>0)binding.title50.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title50.setBackgroundResource(R.drawable.greencorner)

        if (status60>0)binding.title60.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title60.setBackgroundResource(R.drawable.greencorner)

        if (status70>0)binding.title70.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title70.setBackgroundResource(R.drawable.greencorner)

        if (status80>0)binding.title80.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title80.setBackgroundResource(R.drawable.greencorner)

        if (status90>0)binding.title90.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title90.setBackgroundResource(R.drawable.greencorner)

        if (status100>0)binding.title100.setBackgroundResource(R.drawable.redboldcorner)
        else binding.title100.setBackgroundResource(R.drawable.greencorner)

    }

    companion object {
        const val API_KEY = "AIzaSyBCk98WrUeAmbme_GslF3Ex6TziiwwfHRk"

        //  Ниже перечислены id видео (11 символов, располагающихся в конце ссылки на видео) с элементами
        // Например, https://www.youtube.com/watch?v=sVG4NQz4DJg - для обучалки по Baby Frezze.
        const val BABY_FREZZE_VIDEO_ID = "Hr5Ft0dQmtw"
        const val TURTLE_FREZZE_VIDEO_ID = "OMGPAqrSkyI"
        const val SHOULDER_FREZZE_VIDEO_ID = "nDJ5z-6ARxg"
        const val HEAD_FREZZE_VIDEO_ID = "LmZ3F373kGs"
        const val CHAIR_FREZZE_VIDEO_ID = "n7V7VAMsFGM"
        const val ELBOW_FREZZE_VIDEO_ID = "NWvzyqIpcSQ"
        const val HEADHOLLOWBACK_FREZZE_VIDEO_ID = "wq3QyjqABok"
        const val HOLLOWBACK_FREZZE_VIDEO_ID = "SE6Ko-OfnmA"
        const val INVERT_FREZZE_VIDEO_ID = "zU1NLgNXk1g"
        const val ONEHAND_FREZZE_VIDEO_ID = "emWWy8PcRAM"
        const val AIRFLARE_VIDEO_ID = "QZRmvool75U"
        const val BACKSPIN_VIDEO_ID = "VG5ABmzoe9w"
        const val CRICKET_VIDEO_ID = "Yn96WeJsUEk"
        const val ELBOWAIRFLARE_VIDEO_ID = "QZRmvool75U"
        const val FLARE_VIDEO_ID = "RNfeUYyYDK8"
        const val JACKHAMMER_VIDEO_ID = "4FwOffyb5Q8"
        const val HALO_VIDEO_ID = "jZTZjX7-R0I"
        const val HEADSPIN_VIDEO_ID = "LmZ3F373kGs"
        const val MUNCHMILL_VIDEO_ID = "IcJj4wKg-zc"
        const val NINETY_NINE_VIDEO_ID = "1keyrVS_yZI"
        const val SWIPES_VIDEO_ID = "QTiBa0lkooU"
        const val TURTLE_VIDEO_ID = "3SLoKvioGuM"
        const val UFO_VIDEO_ID = "JIpnq6CiC9c"
        const val WEB_VIDEO_ID = "dnlMuhDkc9g"
        const val WINDMILL_VIDEO_ID = "G9M2deEYljQ"
        const val WOLF_VIDEO_ID = "pLFHvNbYqQw"
        const val ANGLE_VIDEO_ID = "_rGXdZNFEb4"
        const val BRIDGE_VIDEO_ID = ""
        const val FINGER_VIDEO_ID = "wT1Xxq1oO-k"
        const val HANDSTAND_VIDEO_ID = "IrEo7qjlO7M"
        const val HORIZONT_VIDEO_ID = "c9s6hw1XRmw"
        const val PUSHUPS_VIDEO_ID = "xvTwFWYMzhA"
        const val BUTTERFLY_VIDEO_ID = ""
        const val FOLD_VIDEO_ID = ""
        const val SHOULDERS_VIDEO_ID = ""
        const val TWINE_VIDEO_ID = ""
        const val DESCRIPTION_MESSAGE = "element"
        const val DESCRIPTION_RATING = "rating"
    }
}