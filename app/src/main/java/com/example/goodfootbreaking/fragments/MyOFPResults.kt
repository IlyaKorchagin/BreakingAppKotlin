package com.example.goodfootbreaking.fragments

import com.example.goodfootbreaking.util.CustomProgressBar.setProgress
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.util.CustomProgressBar
import android.graphics.Typeface
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.goodfootbreaking.activities.DescriptionActivity
import com.example.goodfootbreaking.databinding.FragmentMyOfpresultsBinding
import com.example.goodfootbreaking.model.Pupils

class MyOFPResults : Fragment(), View.OnClickListener {
    private var _binding: FragmentMyOfpresultsBinding? = null
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
        _binding = FragmentMyOfpresultsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.handler = this

        binding.angleProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageAngle.setOnClickListener(this)

        binding.bridgeProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageBridge.setOnClickListener(this)

        binding.fingersProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageFingers.setOnClickListener(this)

        binding.handstandProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHandstand.setOnClickListener(this)

        binding.horizontProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHorizont.setOnClickListener(this)

        binding.pushupProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imagePushups.setOnClickListener(this)

        if (pupil!!.subscription > 0) {
            binding.angelTitle.text = "Уголок - " + pupil!!.angle.toString() + "%"
            setProgress(pupil!!.angle, binding.angleProgressBar)
        } else {
            binding.imageAngle.setImageResource(R.drawable.locked)
            binding.angleProgressBar.visibility = View.INVISIBLE
            binding.angelTitle.textSize = 12f
            binding.angelTitle.text = "Уголок - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.bridgeTitle.text = "Мостик - " + pupil!!.bridge.toString() + "%"
            setProgress(pupil!!.bridge, binding.bridgeProgressBar)
        } else {
            binding.imageBridge.setImageResource(R.drawable.locked)
            binding.bridgeProgressBar.visibility = View.INVISIBLE
            binding.bridgeTitle.textSize = 12f
            binding.bridgeTitle.text = "Мостик - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.fingersTitle.text = "Стойка на пальцьцах - " + pupil!!.finger.toString() + "%"
            setProgress(pupil!!.finger, binding.fingersProgressBar)
        } else {
            binding.imageFingers.setImageResource(R.drawable.locked)
            binding.fingersProgressBar.visibility = View.INVISIBLE
            binding.fingersTitle.textSize = 12f
            binding.fingersTitle.text = "Стойка на пальцьцах - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.handStandTitle.text = "Стойка на руках - " + pupil!!.handstand.toString() + "%"
            setProgress(pupil!!.handstand, binding.handstandProgressBar)
        } else {
            binding.imageHandstand.setImageResource(R.drawable.locked)
            binding.handstandProgressBar.visibility = View.INVISIBLE
            binding.handStandTitle.textSize = 14f
            binding.handStandTitle.setTypeface(null, Typeface.BOLD)
            binding.handStandTitle.text = "Стойка на руках - Подписка отсутствует"
        }
        if (pupil!!.subscription > 0) {
            binding.horizontTitle.text = "Горизонт - " + pupil!!.horizont.toString() + "%"
            setProgress(pupil!!.horizont, binding.horizontProgressBar)
        } else {
            binding.imageHorizont.setImageResource(R.drawable.locked)
            binding.horizontProgressBar.visibility = View.INVISIBLE
            binding.horizontTitle.textSize = 12f
            binding.horizontTitle.text = "Горизонт - Заблокировано"
        }
        binding.pushUpsTitle.text = "Отжимания - " + pupil!!.pushups.toString() + "%"
        setProgress(pupil!!.pushups, binding.pushupProgressBar)
        return view
    }

    override fun onClick(v: View) {
        val intent: Intent
        when (v.id) {
            R.id.imageAngle -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Уголок"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.angle)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageBridge -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Мостик"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.bridge)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageFingers -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Стойка на пальцах"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.finger)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageHandstand -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Стойка на руках"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.handstand)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageHorizont -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Горизонт"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.horizont)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imagePushups -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Отжимания"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.pushups)
                startActivity(intent) // создаем интент для связи активностью
            }
        }
    }

    companion object {
        private var pupil: Pupils? = null
        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        fun newInstance(page: Int, curPupil: Pupils?): MyOFPResults {
            val pageFragment = MyOFPResults()
            pupil = curPupil
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}