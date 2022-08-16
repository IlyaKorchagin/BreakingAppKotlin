package com.example.goodfootbreaking.fragments

import com.example.goodfootbreaking.util.CustomProgressBar.setProgress
import android.widget.ImageButton
import android.widget.TextView
import com.kofigyan.stateprogressbar.StateProgressBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.util.CustomProgressBar
import android.graphics.Typeface
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.goodfootbreaking.activities.DescriptionActivity
import com.example.goodfootbreaking.databinding.FragmentMyOfpresultsBinding
import com.example.goodfootbreaking.databinding.FragmentMyPowerMoveResultsBinding
import com.example.goodfootbreaking.model.Pupils

class MyPowerMoveResults : Fragment(), View.OnClickListener {
    private var _binding: FragmentMyPowerMoveResultsBinding? = null
    private val binding get() = _binding!!

    var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPowerMoveResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.handler = this

        binding.backspinProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageBackspin.setOnClickListener(this)

        binding.airflareProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageAirflare.setOnClickListener(this)

        binding.cricketProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageCricket.setOnClickListener(this)

        binding.elbowAirflareProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageElbowAirflare.setOnClickListener(this)

        binding.flareProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageFlare.setOnClickListener(this)

        binding.jackhammerProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageJackhammer.setOnClickListener(this)

        binding.haloProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHalo.setOnClickListener(this)

        binding.headspinProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHeadspin.setOnClickListener(this)

        binding.muchmillProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageMunchmill.setOnClickListener(this)

        binding.ninetyNineProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageNinety.setOnClickListener(this)

        binding.swipeProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageSwipes.setOnClickListener(this)

        binding.turtleMoveProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageTurtelMove.setOnClickListener(this)

        binding.ufoProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageUfo.setOnClickListener(this)

        binding.webProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageWeb.setOnClickListener(this)

        binding.windmillProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageWindmill.setOnClickListener(this)

        binding.wolfProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageWolf.setOnClickListener(this)

        if (pupil!!.subscription > 0) {
            if ((pupil!!.flare >= 80) and (pupil!!.handstand >= 80)) {
                binding.airflareTitle.text =
                    "Airflare - " + pupil!!.airflare.toString() + "%"
                setProgress(pupil!!.airflare, binding.airflareProgressBar)
            } else {
                binding.imageAirflare.setImageResource(R.drawable.locked)
                binding.airflareProgressBar.visibility = View.INVISIBLE
                binding.airflareTitle.textSize = 12f
                binding.airflareTitle.text = "Airflare\nТребуется Flare > 80 и Handstand > 80"
            }
        } else {
            binding.imageAirflare.setImageResource(R.drawable.locked)
            binding.airflareProgressBar.visibility = View.INVISIBLE
            binding.airflareTitle.textSize = 12f
            binding.airflareTitle.text = "Airflare - Заблокировано"
        }

        binding.backspinTitle.text =
            "Backspin - " + pupil!!.backspin.toString() + "%"
        setProgress(pupil!!.backspin, binding.backspinProgressBar)

        if (pupil!!.subscription > 0) {
            if (pupil!!.turtle >= 65) {
                binding.cricketTitle.text =
                    "Cricket - " + pupil!!.cricket.toString() + "%"
                setProgress(pupil!!.cricket, binding.cricketProgressBar)
            } else {
                binding.imageCricket.setImageResource(R.drawable.locked)
                binding.cricketProgressBar.visibility = View.INVISIBLE
                binding.cricketTitle.textSize = 12f
                binding.cricketTitle.text = "Cricket\nТребуется Turtle > 65"
            }
        } else {
            binding.imageCricket.setImageResource(R.drawable.locked)
            binding.cricketProgressBar.visibility = View.INVISIBLE
            binding.cricketTitle.textSize = 12f
            binding.cricketTitle.text = "Cricket - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.elbowfrezze >= 80) and (pupil!!.handstand >= 60)) {
                binding.elbowAirflareTitle.text =
                    "Elbow Airflare - " + pupil!!.elbowairflare.toString() + "%"
                setProgress(pupil!!.elbowairflare, binding.elbowAirflareProgressBar)
            } else {
                binding.imageElbowAirflare.setImageResource(R.drawable.locked)
                binding.elbowAirflareProgressBar.visibility = View.INVISIBLE
                binding.elbowAirflareTitle.textSize = 12f
                binding.elbowAirflareTitle.text =
                    "Elbow Airflare\nТребуется ElbowFrezze > 80 и Руки  60"
            }
        } else {
            binding.imageElbowAirflare.setImageResource(R.drawable.locked)
            binding.elbowAirflareProgressBar.visibility = View.INVISIBLE
            binding.elbowAirflareTitle.textSize = 12f
            binding.elbowAirflareTitle.text = "Elbow Airflare - Заблокировано "
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.handstand >= 30) and (pupil!!.angle > 40) and (pupil!!.horizont >= 45)) {
                binding.flareTitle.text = "Flare - " + pupil!!.flare.toString() + "%"
                setProgress(pupil!!.flare, binding.flareProgressBar)
            } else {
                binding.imageFlare.setImageResource(R.drawable.locked)
                binding.flareProgressBar.visibility = View.INVISIBLE
                binding.flareTitle.textSize = 12f
                binding.flareTitle.text = "Flare\nТребуется Уголок > 40, Горизонт > 45, Руки > 30"
            }
        } else {
            binding.imageFlare.setImageResource(R.drawable.locked)
            binding.flareProgressBar.visibility = View.INVISIBLE
            binding.flareTitle.textSize = 12f
            binding.flareTitle.text = "Flare - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.cricket >= 90) {
                binding.jackhammerTitle.text =
                    "Jackhammer - " + pupil!!.jackhammer.toString() + "%"
                setProgress(pupil!!.jackhammer, binding.jackhammerProgressBar)
            } else {
                binding.imageJackhammer.setImageResource(R.drawable.locked)
                binding.jackhammerProgressBar.visibility = View.INVISIBLE
                binding.jackhammerTitle.textSize = 12f
                binding.jackhammerTitle.text = "Jackhammer\nТребуется Cricket > 90"
            }
        } else {
            binding.imageJackhammer.setImageResource(R.drawable.locked)
            binding.jackhammerProgressBar.visibility = View.INVISIBLE
            binding.jackhammerTitle.textSize = 12f
            binding.jackhammerTitle.text = "Jackhammer - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.windmill >= 80) and (pupil!!.chairfrezze >= 50)) {
                binding.haloTitle.text = "Halo - " + pupil!!.halo.toString() + "%"
                setProgress(pupil!!.halo, binding.haloProgressBar)
            } else {
                binding.imageHalo.setImageResource(R.drawable.locked)
                binding.haloProgressBar.visibility = View.INVISIBLE
                binding.haloTitle.textSize = 12f
                binding.haloTitle.text = "Halo\nТребуется Windmill > 80, ChairFrezze > 50"
            }
        } else {
            binding.imageHalo.setImageResource(R.drawable.locked)
            binding.haloProgressBar.visibility = View.INVISIBLE
            binding.haloTitle.textSize = 12f
            binding.haloTitle.text = "Halo - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.headfrezze >= 60) {
                binding.headspinTitle.text = "Headspin - " + pupil!!.headspin.toString() + "%"
                setProgress(pupil!!.headspin, binding.headspinProgressBar)
            } else {
                binding.imageHeadspin.setImageResource(R.drawable.locked)
                binding.headspinProgressBar.visibility = View.INVISIBLE
                binding.headspinTitle.textSize = 12f
                binding.headspinTitle.text = "Headspin\nТребуется HeadFrezze > 60"
            }
        } else {
            binding.imageHeadspin.setImageResource(R.drawable.locked)
            binding.headspinProgressBar.visibility = View.INVISIBLE
            binding.headspinTitle.textSize = 12f
            binding.headspinTitle.text = "Headspin - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.windmill >= 80) {
                binding.muchmillTitle.text = "Munchmill - " + pupil!!.munchmill.toString() + "%"
                setProgress(pupil!!.munchmill, binding.muchmillProgressBar)
            } else {
                binding.imageMunchmill.setImageResource(R.drawable.locked)
                binding.muchmillProgressBar.visibility = View.INVISIBLE
                binding.muchmillTitle.textSize = 12f
                binding.muchmillTitle.text = "Munchmill\nТребуется Windmill > 80"
            }
        } else {
            binding.imageMunchmill.setImageResource(R.drawable.locked)
            binding.muchmillProgressBar.visibility = View.INVISIBLE
            binding.muchmillTitle.textSize = 12f
            binding.muchmillTitle.text = "Munchmill - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.handstand >= 80) {
                binding.ninetynineTitle.text = "Ninety_nine - " + pupil!!.ninety_nine.toString() + "%"
                setProgress(pupil!!.ninety_nine, binding.ninetyNineProgressBar)
            } else {
                binding.imageNinety.setImageResource(R.drawable.locked)
                binding.ninetyNineProgressBar.visibility = View.INVISIBLE
                binding.ninetynineTitle.textSize = 12f
                binding.ninetynineTitle.text = "Ninety_nine\nТребуется Стойка на руках > 80"
            }
        } else {
            binding.imageNinety.setImageResource(R.drawable.locked)
            binding.ninetyNineProgressBar.visibility = View.INVISIBLE
            binding.ninetynineTitle.textSize = 12f
            binding.ninetynineTitle.text = "Ninety_nine - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.swipeTitle.text =
                "Swipes - " + pupil!!.swipes.toString() + "%"
            setProgress(pupil!!.swipes, binding.swipeProgressBar)
        } else {
            binding.imageSwipes.setImageResource(R.drawable.locked)
            binding.swipeProgressBar.visibility = View.INVISIBLE
            binding.swipeTitle.textSize = 14f
            binding.swipeTitle.setTypeface(null, Typeface.BOLD)
            binding.swipeTitle.text = "Swipes - Подписка отсутствует"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.turtlefrezze >= 40) {
                binding.turtelMovetitle.text = "Turtle - " + pupil!!.turtle.toString() + "%"
                setProgress(pupil!!.turtle, binding.turtleMoveProgressBar)
            } else {
                binding.imageTurtelMove.setImageResource(R.drawable.locked)
                binding.turtleMoveProgressBar.visibility = View.INVISIBLE
                binding.turtelMovetitle.textSize = 12f
                binding.turtelMovetitle.text = "Turtle\nТребуется TurtleFrezze > 40"
            }
        } else {
            binding.imageTurtelMove.setImageResource(R.drawable.locked)
            binding.turtleMoveProgressBar.visibility = View.INVISIBLE
            binding.turtelMovetitle.textSize = 12f
            binding.turtelMovetitle.text = "Turtle - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.wolf >= 60) and (pupil!!.horizont >= 70)) {
                binding.ufoTitle.text = "Ufo - " + pupil!!.ufo.toString() + "%"
                setProgress(pupil!!.ufo, binding.ufoProgressBar)
            } else {
                binding.imageUfo.setImageResource(R.drawable.locked)
                binding.ufoProgressBar.visibility = View.INVISIBLE
                binding.ufoTitle.textSize = 12f
                binding.ufoTitle.text = "Ufo\nТребуется Wolf > 60, Горизонт > 70"
            }
        } else {
            binding.imageUfo.setImageResource(R.drawable.locked)
            binding.ufoProgressBar.visibility = View.INVISIBLE
            binding.ufoTitle.textSize = 12f
            binding.ufoTitle.text = "Ufo - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.windmill >= 80) {
                binding.webTitle.text = "Web - " + pupil!!.web.toString() + "%"
                setProgress(pupil!!.web, binding.webProgressBar)
            } else {
                binding.imageWeb.setImageResource(R.drawable.locked)
                binding.webProgressBar.visibility = View.INVISIBLE
                binding.webTitle.textSize = 12f
                binding.webTitle.text = "Web\nТребуется Windmill > 80"
            }
        } else {
            binding.imageWeb.setImageResource(R.drawable.locked)
            binding.webProgressBar.visibility = View.INVISIBLE
            binding.webTitle.textSize = 12f
            binding.webTitle.text = "Web - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.babyfrezze >= 50) and (pupil!!.turtlefrezze >= 40)) {
                binding.windmillTitle.text = "Windmill - " + pupil!!.windmill.toString() + "%"
                setProgress(pupil!!.windmill, binding.windmillProgressBar)
            } else {
                binding.imageWindmill.setImageResource(R.drawable.locked)
                binding.windmillProgressBar.visibility = View.INVISIBLE
                binding.windmillTitle.textSize = 12f
                binding.windmillTitle.text = "Windmill\nТребуется TurtleFrezze > 40 и BabyFrezze > 50"
            }
        } else {
            binding.imageWindmill.setImageResource(R.drawable.locked)
            binding.windmillProgressBar.visibility = View.INVISIBLE
            binding.windmillTitle.textSize = 12f
            binding.windmillTitle.text = "Windmill - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.horizont >= 55) {
                binding.wolfTitle.text = "Wolf - " + pupil!!.wolf.toString() + "%"
                setProgress(pupil!!.wolf, binding.wolfProgressBar)
            } else {
                binding.imageWolf.setImageResource(R.drawable.locked)
                binding.wolfProgressBar.visibility = View.INVISIBLE
                binding.wolfTitle.textSize = 12f
                binding.wolfTitle.text = "Wolf\nТребуется Горизонт > 55"
            }
        } else {
            binding.imageWolf.setImageResource(R.drawable.locked)
            binding.wolfProgressBar.visibility = View.INVISIBLE
            binding.wolfTitle.textSize = 12f
            binding.wolfTitle.text = "Wolf - Заблокировано"
        }
        return view
    }

    override fun onClick(v: View) {
        val intent:Intent
        when (v.id) {
            R.id.imageBackspin -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Backspin"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.backspin)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageSwipes -> {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Swipes"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.swipes)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageTurtelMove -> if (pupil!!.turtlefrezze >= 40) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "TurtelMove"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.turtle)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageWindmill -> if ((pupil!!.babyfrezze >= 50) and (pupil!!.turtlefrezze >= 40)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Windmill"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.windmill)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageHeadspin -> if (pupil!!.headfrezze >= 60) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Headspin"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.headspin)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageCricket -> if (pupil!!.turtle >= 65) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Cricket"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.cricket)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageWeb -> if (pupil!!.windmill >= 80) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Web"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.web)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageWolf -> if (pupil!!.horizont >= 55) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Wolf"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.wolf)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageMunchmill -> if (pupil!!.windmill >= 80) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Munchmill"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.munchmill)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageFlare -> if ((pupil!!.handstand >= 30) and (pupil!!.angle >= 40) and (pupil!!.horizont >= 45)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Flare"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.flare)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageNinety -> if (pupil!!.handstand >= 80) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Ninety"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.ninety_nine)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageHalo -> if ((pupil!!.windmill >= 80) and (pupil!!.chairfrezze >= 50)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Halo"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.halo)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageJackhammer -> if (pupil!!.cricket >= 90) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Jackhammer"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.jackhammer)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageUfo -> if ((pupil!!.wolf >= 60) and (pupil!!.horizont >= 70)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Ufo"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.ufo)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageElbowAirflare -> if ((pupil!!.elbowfrezze >= 80) and (pupil!!.handstand >= 60)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "ElbowAirflare"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.elbowairflare)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageAirflare -> if ((pupil!!.flare >= 80) and (pupil!!.handstand >= 80)) {
                intent = Intent(
                    v.context,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Airflare"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.airflare)
                startActivity(intent) // создаем интент для связи активностью
            }
        }
    }

    companion object {
        private var pupil: Pupils? = null
        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        fun newInstance(page: Int, curPupil: Pupils?): MyPowerMoveResults {
            val pageFragment = MyPowerMoveResults()
            pupil = curPupil
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}