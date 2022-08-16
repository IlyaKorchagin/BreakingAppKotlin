package com.example.goodfootbreaking.fragments

import com.google.firebase.database.DatabaseReference
import android.webkit.WebView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.view.Gravity
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.model.Event
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class FEvents : Fragment() {
    private var events: MutableList<Event?>? = null
    private var adapter: ArrayAdapter<*>? = null
    private val EVENT_KEY = "Events"
    private var mDataBase: DatabaseReference? = null
    private var curView: View? = null

    private val scope = MainScope()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fevents, container, false)
        curView = view
        mDataBase = FirebaseDatabase.getInstance().getReference(EVENT_KEY)
        events = ArrayList()
        adapter = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1,
            events as ArrayList<Event?>
        )
       scope.launch { loadDataFromDB() }
        return view
    }

    private val dataFromDB: Unit
        private get() {
            mDataBase!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (events!!.size > 0) events!!.clear()
                    for (ds in dataSnapshot.children) {
                        val event = ds.getValue(
                            Event::class.java
                        )!!
                        events!!.add(event)
                    }
                    adapter!!.notifyDataSetChanged()
                    SortTable(events, events!!.size, curView)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    fun SortTable(eventList: List<Event?>?, row_count: Int, view: View?) {
        var tableLayout = TableLayout(view!!.context)
        tableLayout = view.findViewById<View>(R.id.EventTableLayout) as TableLayout
        tableLayout.removeAllViews() // удалить все View из LinearLayout
        val Count_Columns = 2
        for (i in 0 until row_count) {
            val tableRow = TableRow(view.context)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            if (i % 2 == 0) tableRow.setBackgroundResource(R.drawable.eventtabrow1) else tableRow.setBackgroundResource(
                R.drawable.eventtabrow2
            )
            tableRow.setPadding(0, 8, 0, 0)
            val curevent = eventList!![i]
            for (j in 0 until Count_Columns + 1) {
                when (j) {
                    0 -> {
                        val dataTextView = TextView(view.context)
                        dataTextView.layoutParams =
                            TableRow.LayoutParams(215, LinearLayout.LayoutParams.WRAP_CONTENT)
                        dataTextView.setPadding(0, 2, 0, 0)
                        dataTextView.gravity = Gravity.CENTER
                        dataTextView.setTextColor(resources.getColor(R.color.white))
                        dataTextView.textSize = 14f
                        dataTextView.text = curevent!!.date
                        tableRow.addView(dataTextView, j)
                    }
                    1 -> {
                        val titleTextView = TextView(view.context)
                        titleTextView.setLayoutParams(
                            TableRow.LayoutParams(
                                570,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        )
                        titleTextView.setGravity(Gravity.CENTER)
                        when (curevent!!.type) {
                            0 -> titleTextView.setTextColor(resources.getColor(R.color.local))
                            1 -> titleTextView.setTextColor(resources.getColor(R.color.city))
                            2 -> titleTextView.setTextColor(resources.getColor(R.color.country))
                        }
                        titleTextView.setPadding(0, 0, 0, 10)
                        titleTextView.setTextSize(12f)
                        titleTextView.setTypeface(null, Typeface.BOLD)
                        titleTextView.setText(curevent.title)
                        tableRow.addView(titleTextView, j)
                    }
                    2 -> {
                        val placeTextView = TextView(view.context)
                        placeTextView.setLayoutParams(
                            TableRow.LayoutParams(
                                280,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        )
                        placeTextView.setGravity(Gravity.CENTER)
                        placeTextView.setTextColor(resources.getColor(R.color.white))
                        placeTextView.setPadding(0, 2, 0, 0)
                        placeTextView.setTextSize(14f)
                        placeTextView.setText(curevent!!.location)
                        tableRow.addView(placeTextView, j)
                    }
                }
            }
            tableLayout.addView(tableRow, i)
        }
    }

    suspend fun loadDataFromDB(){dataFromDB}
}