package com.example.goodfootbreaking.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.goodfootbreaking.fragments.FRatingTable
import com.example.goodfootbreaking.fragments.FEvents
import com.example.goodfootbreaking.fragments.FMainRezults
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.goodfootbreaking.R
import com.google.firebase.database.FirebaseDatabase
import android.text.Html
import com.example.goodfootbreaking.model.Pupils
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.goodfootbreaking.databinding.ActivityStatisticBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class StatisticActivity : AppCompatActivity() {
    private var _binding: ActivityStatisticBinding? = null
    private val binding get() = _binding!!

    private val scope = MainScope()

    var fragRatingTable: FRatingTable? = null
    var fragEvents: FEvents? = null
    var fragMainRez: FMainRezults? = null
    var fTrans: FragmentTransaction? = null
    private var curEmail: String? = null
    private val PUPILS_KEY = "Pupils"
    private var mDataBase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatisticBinding.inflate(layoutInflater)
        val view = binding.root
        binding.handler = this
        setContentView(view)

        init()
        val intent = intent
        curEmail = intent.getStringExtra(CURRENT_EMAIL)
        fragRatingTable = FRatingTable()
        fragEvents = FEvents()
        scope.launch { loadDataFromDB() }
    }

    private fun init() {

        setSupportActionBar(binding.toolBarUser)
        mDataBase = FirebaseDatabase.getInstance().getReference(PUPILS_KEY)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Заполнение меню; элементы действий добавляются на панель приложения
        menuInflater.inflate(R.menu.menu_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Обработка нажатия кнопок на панели приложения
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fTrans = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.MyRoomMenuBtn -> {
                //if(curPupil.getSubscription()>0){
                val ratingtext = findViewById<View>(R.id.ratingtext) as TextView
                ratingtext.text =
                    " FREZZE RATING - " + curPupil!!.frezze_rating + "%"
                fTrans!!.replace(R.id.userFragment, fragMainRez!!)
                // }
                val head = findViewById<View>(R.id.headlayout) as LinearLayout
                this.title = Html.fromHtml("<small>Личный кабинет</small>")
                head.visibility = View.VISIBLE
            }
            R.id.RatingMenuBtn -> {
                val fmyrezbundle = Bundle()
                fmyrezbundle.putSerializable("curPupil", curPupil)
                fragRatingTable!!.arguments = fmyrezbundle
                fTrans!!.replace(R.id.userFragment, fragRatingTable!!)
                this.title = Html.fromHtml("<small>Рейтинговая таблица</small>")
                binding.headlayout.setVisibility(View.GONE)
            }
            R.id.EventsBtn -> {
                fTrans!!.replace(R.id.userFragment, fragEvents!!)
                this.title = Html.fromHtml("<small>Календарь событий</small>")
                binding.headlayout.setVisibility(View.GONE)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        fTrans!!.commit()
        return true
    }

    private val dataFromDB: Unit
        private get() {
            if (curEmail == "root@mail.ru") {
                val newPupil = Pupils(curEmail)
                curPupil = newPupil
                FillScreen(curPupil)
            } else {
                mDataBase!!.orderByChild("email").equalTo(curEmail)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (ds in dataSnapshot.children) {
                                val pupil = ds.getValue(Pupils::class.java)
                                if (pupil != null) {
                                    curPupil = pupil
                                    FillScreen(curPupil)
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }
        }

    @SuppressLint("ResourceAsColor")
    private fun FillScreen(curPupil: Pupils?) {
        val fmyrezbundle = Bundle()
        fmyrezbundle.putSerializable("curPupil", curPupil)
        fragMainRez = FMainRezults()
        fragMainRez!!.arguments = fmyrezbundle
        fTrans = supportFragmentManager.beginTransaction()
        fTrans!!.replace(R.id.userFragment, fragMainRez!!)
        fTrans!!.commit()
        binding.ratingBar.progress = curPupil!!.rating.toInt()
        binding.message.textSize = 14f
        binding.ratingtext.textSize = 12f
        binding.pupilName.textSize = 16f
        binding.pupilName.text = curPupil.name
        binding.status.textSize = 12f
        binding.status.text = "Номинация: "
        binding.pupilStatus.textSize = 12f
        binding.pupilStatus.typeface = Typeface.defaultFromStyle(Typeface.BOLD_ITALIC)
        when (curPupil.status) {
            0 -> binding.pupilStatus.text = "не указана"
            1 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.baby))
                binding.pupilStatus.text = "Дети до 7 лет"
            }
            2 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.logo_green))
                binding.pupilStatus.text = "Начинающие"
            }
            3 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.logo_sea))
                binding.pupilStatus.text = "Второгодки"
            }
            4 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.logo_orange))
                binding.pupilStatus.text = "Продолжающие"
            }
            5 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.logo_red))
                binding.pupilStatus.text = "Kids Pro"
            }
            6 -> {
                binding.pupilStatus.setTextColor(resources.getColor(R.color.logo_red))
                binding.pupilStatus.text = "Администратор"
            }
            else -> binding.pupilStatus.text = "Номинация: не указана"
        }
        if (binding.ratingBar.progress <= 10) {
            binding.avatar.setImageResource(R.drawable.level1)
            binding.message.text = "LEVEL 1. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar.progress <= 20) {
            binding.avatar.setImageResource(R.drawable.level2)
            binding.message.text = "LEVEL 2. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 30) {
            binding.avatar.setImageResource(R.drawable.level3)
            binding.message.text = "LEVEL 3. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 40) {
            binding.avatar.setImageResource(R.drawable.level4)
            binding.message.text = "LEVEL 4. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 50) {
            binding.avatar.setImageResource(R.drawable.level5)
            binding.message.text = "LEVEL 5. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 60) {
            binding.avatar.setImageResource(R.drawable.level6)
            binding.message.text = "LEVEL 6. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 70) {
            binding.avatar.setImageResource(R.drawable.level7)
            binding.message.text = "LEVEL 7. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 80) {
            binding.avatar.setImageResource(R.drawable.level8)
            binding.message.text = "LEVEL 8. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 90) {
            binding.avatar.setImageResource(R.drawable.level9)
            binding.message.text = "LEVEL 9. Rating - " + curPupil.rating + "%"
        } else if (binding.ratingBar!!.progress <= 100) {
            binding.avatar.setImageResource(R.drawable.level10)
            binding.message.text = "LEVEL 10. Rating - " + curPupil.rating + "%"
        }
        binding.ratingtext!!.text = " FREZZE RATING - " + curPupil.frezze_rating + "%"
    }


    suspend fun loadDataFromDB(){
        dataFromDB
    }

    companion object {
        const val CURRENT_EMAIL = "current_email"
        private var curPupil: Pupils? = null
    }
}