package com.example.goodfootbreaking.util

import android.graphics.Color
import com.kofigyan.stateprogressbar.StateProgressBar

object CustomProgressBar {
    @JvmField
    var descriptionData = arrayOf("20%", "40%", "60%", "80%", "100%")
    @JvmStatic
    fun setProgress(i: Int, stProgress: StateProgressBar) {
        when(i){
            in 0..19  -> stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
            in 20..39 -> stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
            in 40..59 -> stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
            in 60..79 -> stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR)
            in 80..99 -> stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE)
            100       -> {
                stProgress.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE)
                stProgress.currentStateDescriptionColor = Color.parseColor("#64bf72")
            }
        }

    }
}