package com.example.goodfootbreaking.fragments


import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.model.Pupils
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.FragmentPagerAdapter


class FMainRezults() : Fragment() {
    private var mViewPager: ViewPager? = null
    var pagerAdapter: PagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fmain_rezults, container, false)
        curPupil = this.arguments!!.getSerializable("curPupil") as Pupils?
        mViewPager = view.findViewById<View>(R.id.MyRezultPager) as ViewPager
        pagerAdapter = MyFragmentPagerAdapter(childFragmentManager)
        mViewPager!!.adapter = pagerAdapter
        mViewPager!!.setPageTransformer(false, ViewPager.PageTransformer { v, pos ->
            val opacity = Math.abs(Math.abs(pos) - 1)
            v.alpha = opacity
        })
        mViewPager!!.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val ratingtext = activity!!.findViewById<View>(R.id.ratingtext) as TextView
                when (position) {
                    0 -> ratingtext.text =
                        ("FREZZE RATING - " + curPupil!!.frezze_rating).toString() + "%"
                    1 -> ratingtext.text =
                        ("POWER RATING - " + curPupil!!.powermove_rating).toString() + "%"
                    2 -> ratingtext.text =
                        ("ОФП RATING - " + curPupil!!.ofp_rating).toString() + "%"
                    3 -> ratingtext.text =
                        ("STRETCH RATING - " + curPupil!!.streching_rating).toString() + "%"
                    else -> ratingtext.text = ""
                }
            }
        })
        val tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
        tabLayout.getTabAt(0)!!.text = "FREZZE"
        tabLayout.getTabAt(1)!!.text = "MOVE"
        tabLayout.getTabAt(2)!!.text = "ОФП"
        tabLayout.getTabAt(3)!!.text = "STRETCH"
        return view
    }

    private inner class MyFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
        (fm)!!
    ) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return FMyFrezzesResults.newInstance(position, curPupil)
                1 -> return MyPowerMoveResults.newInstance(position, curPupil)
                2 -> return MyOFPResults.newInstance(position, curPupil)
                3 -> return MyStretchResults.newInstance(position, curPupil)
                else -> return FMyFrezzesResults.newInstance(position, curPupil)
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }

    companion object {
        private var curPupil: Pupils? = null
    }
}