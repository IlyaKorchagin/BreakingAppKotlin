package com.example.goodfootbreaking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.goodfootbreaking.util.CustomProgressBar
import android.graphics.Typeface
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.activities.DescriptionActivity
import com.example.goodfootbreaking.databinding.FragmentMyStretchResultsBinding
import com.example.goodfootbreaking.model.Pupils

class MyStretchResults : Fragment(), View.OnClickListener {
    private var _binding: FragmentMyStretchResultsBinding? = null
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
        _binding = FragmentMyStretchResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.handler = this

        binding.buterflyProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageButterfly.setOnClickListener(this)

        binding.foldProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageFold.setOnClickListener(this)

        binding.shouldersProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageShoulders.setOnClickListener(this)

        binding.twineProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageTwine.setOnClickListener(this)
        binding.twineTitle.text = "Шпагат - " + pupil!!.twine.toString() + "%"
        CustomProgressBar.setProgress(pupil!!.twine, binding.twineProgressBar)
        if (pupil!!.subscription > 0) {
            binding.foldTitle.text = "Складка - " + pupil!!.fold.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.fold, binding.foldProgressBar)
        } else {
            binding.imageFold.setImageResource(R.drawable.locked)
            binding.foldProgressBar.visibility = View.INVISIBLE
            binding.foldTitle.textSize = 14f
            binding.foldTitle.setTypeface(null, Typeface.BOLD)
            binding.foldTitle.text = "Складка - Подписка отсутствует"
        }
        if (pupil!!.subscription > 0) {
            binding.shouldersTitle.text =
                "Плечи - " + pupil!!.shoulders.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.shoulders, binding.shouldersProgressBar)
        } else {
            binding.imageShoulders.setImageResource(R.drawable.locked)
            binding.shouldersProgressBar.visibility = View.INVISIBLE
            binding.shouldersTitle.textSize = 12f
            binding.shouldersTitle.text = "Плечи - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.buterflyTitle.text = "Бабочка - " + pupil!!.butterfly.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.butterfly, binding.buterflyProgressBar)
        } else {
            binding.imageButterfly.setImageResource(R.drawable.locked)
            binding.buterflyProgressBar.visibility = View.INVISIBLE
            binding.buterflyTitle.textSize = 12f
            binding.buterflyTitle.text = "Плечи - Заблокировано"
        }
        return view
    }

    override fun onClick(v: View) {
        val intent: Intent
        when (v.id) {
            R.id.imageButterfly -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Бабочка"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.butterfly)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageFold -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Складка"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.fold)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageShoulders -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Плечи"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.shoulders)
                startActivity(intent) // создаем интент для связи активностью
            }
            R.id.imageTwine -> {
                intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Шпагат"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.twine)
                startActivity(intent) // создаем интент для связи активностью
            }
        }
    }

    companion object {
        private var pupil: Pupils? = null
        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        @JvmStatic
        fun newInstance(page: Int, curPupil: Pupils?): MyStretchResults {
            val pageFragment = MyStretchResults()
            pupil = curPupil
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}