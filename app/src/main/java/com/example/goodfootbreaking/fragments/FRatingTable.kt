package com.example.goodfootbreaking.fragments

import com.example.goodfootbreaking.model.Pupils
import com.google.firebase.database.DatabaseReference
import android.text.Html
import com.example.goodfootbreaking.R
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.example.goodfootbreaking.R.array
import android.widget.AdapterView.OnItemSelectedListener
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.os.AsyncTask
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class FRatingTable : Fragment() {
    private var adapter: ArrayAdapter<*>? = null
    private var adapterNoSubscription: ArrayAdapter<*>? = null
    private var allpupils: ArrayList<Pupils?>? = null
    private val PUPILS_KEY = "Pupils"
    private var mDataBase: DatabaseReference? = null
    private var curView: View? = null
    private var words: String? = null
    private var sort = 0
    private var emojicode = 0 // поле по которому идет сортировка

    private val scope = MainScope()


    //Отрисовка строк таблицы рейтинга
    fun drawRow(
        textView: TextView,
        currentPupil: Pupils?,
        emocodbreaking: Int,
        emocodbattle: Int,
        index: Int,
        sort: Int
    ) {
        if (sort == 0) {
            if ((currentPupil!!.new_position == 0) or (currentPupil.current_position - currentPupil.new_position == 0)) textView.text =
                String(Character.toChars(emocodbreaking)) + " " + currentPupil.name + " " + String(
                    Character.toChars(emocodbreaking)
                ) else if (currentPupil.current_position - currentPupil.new_position > 0) {
                val words =
                    String(Character.toChars(emocodbreaking)) + " " + currentPupil.name + "<small><font color='#64bf72'>" + din(
                        index,
                        currentPupil,
                        sort
                    ) + "</font></small>"
                textView.text = Html.fromHtml(words)
            } else {
                val words =
                    String(Character.toChars(emocodbreaking)) + " " + currentPupil.name + "<small><font color='#f26450'>" + din(
                        index,
                        currentPupil,
                        sort
                    ) + "</font></small>"
                textView.text = Html.fromHtml(words)
            }
        } else if (sort == 1) {
            if ((currentPupil!!.battle_new_position == 0) or (currentPupil.battle_cur_position - currentPupil.battle_new_position == 0)) textView.text =
                String(Character.toChars(emocodbattle)) + " " + currentPupil.name + " " + String(
                    Character.toChars(emocodbattle)
                ) else if (currentPupil.battle_cur_position - currentPupil.battle_new_position > 0) {
                val words =
                    String(Character.toChars(emocodbattle)) + " " + currentPupil.name + "<small><font color='#64bf72'>" + din(
                        index,
                        currentPupil,
                        sort
                    ) + "</font></small>"
                textView.text = Html.fromHtml(words)
            } else {
                val words =
                    String(Character.toChars(emocodbattle)) + " " + currentPupil.name + "<small><font color='#f26450'>" + din(
                        index,
                        currentPupil,
                        sort
                    ) + "</font></small>"
                textView.text = Html.fromHtml(words)
            }
        } else textView.text =
            String(Character.toChars(emocodbreaking)) + " " + currentPupil!!.name + " " + String(
                Character.toChars(emocodbreaking)
            ) + " "
    }

    // Расчет динамики прогресса по турнирной таблице (Breaking Up)
    fun din(i: Int, pupils: Pupils?, sort: Int): String {
        var result: String
        val dinamic: Int
        dinamic = if (sort == 0) {
            pupils!!.current_position - pupils.new_position
        } else {
            pupils!!.battle_cur_position - pupils.battle_new_position
        }
        if (dinamic == 0) result = ""
        if (dinamic > 0) {
            emojicode = when (dinamic) {
                1 -> 128076 //  смайлик - ок
                2 -> 128077 //  смайлик - класс
                3 -> 128163 //  смайлик - бомба
                4 -> 128165 //  смайлик - взрыв
                5 -> 128293 //  смайлик - огонь
                6 -> 128640 //  смайлик - ракета
                else -> 128640 //  смайлик - ракета
            }
            result =
                " (↑ " + Integer.toString(dinamic) + ") " + String(Character.toChars(emojicode))
        } else {
            emojicode = when (dinamic) {
                -1 -> 128201 //  смайлик - график с отрицательной динамикой
                -2 -> 12349 //  смайлик - график с отрицательной динамикой
                -3 -> 128078 //  смайлик - very bad
                -4 -> 128545 //  смайлик - злость
                -5 -> 128561 //  смайлик - ужас
                else -> 128561 //  смайлик - ужас
            }
            result = " (↓ " + Integer.toString(Math.abs(dinamic)) + ") " + String(
                Character.toChars(emojicode)
            )
        }
        return result
    }

    // Выбор цвета текста, в завимости от текущей позиции
    fun getColour(i: Int): Int {
        val res: Int
        res = when (i) {
            0 -> R.color.white
            1 -> R.color.baby
            2 -> R.color.logo_green
            3 -> R.color.logo_sea
            4 -> R.color.logo_orange
            5 -> R.color.logo_red
            else -> R.color.white
        }
        return res
    }

    fun getBattleColour(i: Int): Int {
        val res: Int
        res = when (i) {
            1 -> R.color.one
            2 -> R.color.two
            3 -> R.color.three
            4 -> R.color.four
            5 -> R.color.five
            6 -> R.color.six
            7 -> R.color.seven
            8 -> R.color.eight
            else -> R.color.white
        }
        return res
    }

    // Отрисовка таблицы рейтинга
    fun SortTable(pupilList: List<Pupils?>?, row_count: Int, view: View?, position: Int) {
        var tableLayout = TableLayout(view!!.context)
        tableLayout = view.findViewById<View>(R.id.RatingTableLayout) as TableLayout
        tableLayout.removeAllViews() // удалить все View из LinearLayout
        val Count_Columns = 2
        for (i in 0 until row_count) {
            val tableRow = TableRow(view.context)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            if (i % 2 == 0) tableRow.setBackgroundResource(R.drawable.ratingtabrow11) else tableRow.setBackgroundResource(
                R.drawable.ratingtabrow21
            )
            val curpupil = pupilList!![i] // считываем данные с текущего ученика
            for (j in 0 until Count_Columns + 1) {
                when (j) {
                    0 -> {
                        val textView = TextView(view.context)
                        textView.layoutParams =
                            TableRow.LayoutParams(160, LinearLayout.LayoutParams.WRAP_CONTENT)
                        when (i) {
                            0 -> {
                                textView.setTextColor(resources.getColor(R.color.one))
                                textView.textSize = 18f
                            }
                            1 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.two))
                                textView.textSize = 16f
                            }
                            2 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.three))
                                textView.textSize = 16f
                            }
                            3 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.four))
                                textView.textSize = 16f
                            }
                            4 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.five))
                                textView.textSize = 16f
                            }
                            5 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.six))
                                textView.textSize = 16f
                            }
                            6 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.seven))
                                textView.textSize = 16f
                            }
                            7 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.eight))
                                textView.textSize = 16f
                            }
                            else -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.white))
                                textView.textSize = 16f
                            }
                        }
                        textView.setPadding(0, 8, 0, 0)
                        textView.gravity = Gravity.CENTER

                        // Если мы сортируем по полю "Battles", то выводим места из поля "Battle_new_position"
                        if (sort == 1 && curPupil!!.status != 6) textView.text = Integer.toString(
                            curpupil!!.battle_new_position
                        ) else textView.text = Integer.toString(i + 1)
                        tableRow.addView(textView, j)
                    }
                    1 -> {
                        val textView = TextView(view.context)
                        textView.setLayoutParams(
                            TableRow.LayoutParams(
                                650,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        )
                        textView.setTextColor(resources.getColor(getColour(curpupil!!.status)))
                        when (i) {
                            0 -> {
                                textView.setTextSize(17f)
                                drawRow(textView, curpupil, 128081, 127942, i, sort)
                            }
                            1 -> {
                                textView.setTextSize(16f)
                                drawRow(textView, curpupil, 127941, 127941, i, sort)
                            }
                            2 -> {
                                textView.setTextSize(16f)
                                drawRow(textView, curpupil, 127941, 127941, i, sort)
                            }
                            else -> {
                                textView.setTextSize(16f)
                                drawRow(textView, curpupil, 0, 0, i, sort)
                            }
                        }
                        textView.setGravity(Gravity.CENTER)
                        textView.setPadding(0, 8, 0, 0)
                        tableRow.addView(textView, j)
                    }
                    2 -> {
                        val textView = TextView(view.context)
                        textView.setLayoutParams(
                            TableRow.LayoutParams(
                                250,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                        )
                        when (i) {
                            0 -> {
                                textView.setTextColor(resources.getColor(R.color.one))
                                textView.setTextSize(18f)
                            }
                            1 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.two))
                                textView.setTextSize(16f)
                            }
                            2 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.three))
                                textView.setTextSize(16f)
                            }
                            3 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.four))
                                textView.setTextSize(16f)
                            }
                            4 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.five))
                                textView.setTextSize(16f)
                            }
                            5 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.six))
                                textView.setTextSize(16f)
                            }
                            6 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.seven))
                                textView.setTextSize(16f)
                            }
                            7 -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.eight))
                                textView.setTextSize(16f)
                            }
                            else -> {
                                if (sort == 1 && curPupil!!.status != 6) textView.setTextColor(
                                    resources.getColor(
                                        getBattleColour(
                                            curpupil!!.battle_new_position
                                        )
                                    )
                                ) else textView.setTextColor(resources.getColor(R.color.white))
                                textView.setTextSize(16f)
                            }
                        }
                        textView.setGravity(Gravity.CENTER)
                        textView.setPadding(0, 8, 0, 0)
                        when (position) {
                            0 -> textView.setText(java.lang.Double.toString(curpupil!!.rating))
                            1 -> textView.setText(java.lang.Double.toString(curpupil!!.battle_rating.toDouble()))
                            2 -> textView.setText(java.lang.Double.toString(curpupil!!.frezze_rating))
                            3 -> textView.setText(java.lang.Double.toString(curpupil!!.powermove_rating))
                            4 -> textView.setText(java.lang.Double.toString(curpupil!!.ofp_rating))
                            5 -> textView.setText(java.lang.Double.toString(curpupil!!.streching_rating))
                            6 -> textView.setText(Integer.toString(curpupil!!.babyfrezze))
                            7 -> textView.setText(Integer.toString(curpupil!!.chairfrezze))
                            8 -> textView.setText(Integer.toString(curpupil!!.elbowfrezze))
                            9 -> textView.setText(Integer.toString(curpupil!!.headfrezze))
                            10 -> textView.setText(Integer.toString(curpupil!!.headhollowbackfrezze))
                            11 -> textView.setText(Integer.toString(curpupil!!.hollowbackfrezze))
                            12 -> textView.setText(Integer.toString(curpupil!!.invertfrezze))
                            13 -> textView.setText(Integer.toString(curpupil!!.onehandfrezze))
                            14 -> textView.setText(Integer.toString(curpupil!!.shoulderfrezze))
                            15 -> textView.setText(Integer.toString(curpupil!!.turtlefrezze))
                            16 -> textView.setText(Integer.toString(curpupil!!.airflare))
                            17 -> textView.setText(Integer.toString(curpupil!!.backspin))
                            18 -> textView.setText(Integer.toString(curpupil!!.cricket))
                            19 -> textView.setText(Integer.toString(curpupil!!.elbowairflare))
                            20 -> textView.setText(Integer.toString(curpupil!!.flare))
                            21 -> textView.setText(Integer.toString(curpupil!!.jackhammer))
                            22 -> textView.setText(Integer.toString(curpupil!!.halo))
                            23 -> textView.setText(Integer.toString(curpupil!!.headspin))
                            24 -> textView.setText(Integer.toString(curpupil!!.munchmill))
                            25 -> textView.setText(Integer.toString(curpupil!!.ninety_nine))
                            26 -> textView.setText(Integer.toString(curpupil!!.swipes))
                            27 -> textView.setText(Integer.toString(curpupil!!.turtle))
                            28 -> textView.setText(Integer.toString(curpupil!!.ufo))
                            29 -> textView.setText(Integer.toString(curpupil!!.web))
                            30 -> textView.setText(Integer.toString(curpupil!!.windmill))
                            31 -> textView.setText(Integer.toString(curpupil!!.wolf))
                            32 -> textView.setText(Integer.toString(curpupil!!.angle))
                            33 -> textView.setText(Integer.toString(curpupil!!.bridge))
                            34 -> textView.setText(Integer.toString(curpupil!!.finger))
                            35 -> textView.setText(Integer.toString(curpupil!!.handstand))
                            36 -> textView.setText(Integer.toString(curpupil!!.horizont))
                            37 -> textView.setText(Integer.toString(curpupil!!.pushups))
                            38 -> textView.setText(Integer.toString(curpupil!!.butterfly))
                            39 -> textView.setText(Integer.toString(curpupil!!.fold))
                            40 -> textView.setText(Integer.toString(curpupil!!.shoulders))
                            41 -> textView.setText(Integer.toString(curpupil!!.twine))
                            else -> textView.setText(java.lang.Double.toString(curpupil!!.rating))
                        }
                        if (curPupil!!.status == 6) {
                            words = if (curpupil.subscription > 0) {
                                textView.getText()
                                    .toString() + "<small><font>" + String(Character.toChars(9989)) + "</font></small>"
                            } else {
                                textView.getText()
                                    .toString() + "<small><font>" + String(Character.toChars(10006)) + "</font></small>"
                            }
                            textView.setText(Html.fromHtml(words))
                        }
                        tableRow.addView(textView, j)
                    }
                }
            }
            tableLayout.addView(tableRow, i)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rating_table, container, false)
        curView = view
        curPupil = this.arguments!!.getSerializable("curPupil") as Pupils?
        val ratingtext = activity!!.findViewById<View>(R.id.ratingtext) as TextView
        ratingtext.text = ""
        mDataBase = FirebaseDatabase.getInstance().getReference(PUPILS_KEY)
        allpupils = ArrayList()
        adapter = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, allpupils!!)


        // Получаем экземпляр элемента Spinner
        val spinner = view.findViewById<View>(R.id.sortspinner) as Spinner
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            view.context,
            array.sortList,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val elementsArray = resources.getStringArray(array.elementsBDList)
        // Вызываем адаптер
        if (curPupil!!.subscription == 0) {
            adapterNoSubscription =
                ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, allpupils!!)
            val adapterNoSubscription: ArrayAdapter<*> = ArrayAdapter.createFromResource(
                view.context,
                array.sortListNoSubscription,
                android.R.layout.simple_spinner_item
            )
            adapterNoSubscription.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapterNoSubscription
        } else {
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                var selectedItemPosition = selectedItemPosition
                if (selectedItemPosition != 1) {
                    if (curPupil!!.subscription == 0) {
                        selectedItemPosition = 1
                        Toast.makeText(
                            context,
                            "Доступна сортирока только по Баттлам",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                sort = selectedItemPosition


                if (curPupil!!.subscription > 0) {
                    scope.launch { loadDataFromDB(elementsArray[selectedItemPosition]) }
                } else {
                    scope.launch { loadDataFromDB("battle_rating") }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return view
    }

    private fun getDataFromDB(sortField: String) {
        if (sortField == "battle_rating") {
            if (curPupil!!.status == 6) {
                mDataBase!!.orderByChild(sortField)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (allpupils!!.size > 0) allpupils!!.clear()
                            for (ds in dataSnapshot.children) {
                                val pupil = ds.getValue(Pupils::class.java)!!
                                allpupils!!.add(pupil)
                            }
                            adapter!!.notifyDataSetChanged()

                            //----> Сортировка по убыванию
                            Collections.sort(allpupils) { o1, o2 -> o2!!.battle_rating - o1!!.battle_rating }
                            //<---- Сортировка по убыванию
                            SortTable(allpupils, allpupils!!.size, curView, sort)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            } else {
                mDataBase!!.orderByChild("status").equalTo(curPupil!!.status.toDouble())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (allpupils!!.size > 0) allpupils!!.clear()
                            for (ds in dataSnapshot.children) {
                                val pupil = ds.getValue(Pupils::class.java)!!
                                allpupils!!.add(pupil)
                            }
                            adapter!!.notifyDataSetChanged()

                            //----> Сортировка по убыванию
                            Collections.sort(allpupils) { o1, o2 -> o2!!.battle_rating - o1!!.battle_rating }
                            //<---- Сортировка по убыванию
                            SortTable(allpupils, allpupils!!.size, curView, sort)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }
        } else {
            mDataBase!!.orderByChild(sortField)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (allpupils!!.size > 0) allpupils!!.clear()
                        for (ds in dataSnapshot.children) {
                            val pupil = ds.getValue(Pupils::class.java)!!
                            allpupils!!.add(pupil)
                        }
                        adapter!!.notifyDataSetChanged()
                        Collections.reverse(allpupils)
                        SortTable(allpupils, allpupils!!.size, curView, sort)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }
    }


    suspend fun loadDataFromDB(vararg params: String?): Void? {
        params[0]?.let { getDataFromDB(it) }
        return null
    }

    companion object {
        private var curPupil: Pupils? = null
    }
}