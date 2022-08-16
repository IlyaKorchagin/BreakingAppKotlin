package com.example.goodfootbreaking.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
import com.example.goodfootbreaking.R.string
import com.google.android.gms.tasks.OnSuccessListener
import android.net.ConnectivityManager
import org.apache.commons.net.ntp.NTPUDPClient
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.view.View
import com.example.goodfootbreaking.model.Pupils
import com.example.goodfootbreaking.databinding.LoginLayoutBinding
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.InetAddress
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var _binding: LoginLayoutBinding? = null
    private val binding get() = _binding!!

    private val scope = MainScope()

    private var mAuth: FirebaseAuth? = null

    private val PUPILS_KEY = "Pupils" //  идентификатор таблицы в БД
    private var mDataBase //  ссылка на БД
            : DatabaseReference? = null
    private var registration //  признак регистрации, в случае регистрации при первом входе в БД будет добавлен новый пользователь
            : Boolean? = null
    private var curPupil: Pupils? = null
    private var currentDate //  текущая дата
            : Date? = null
    private var payDate //  дата оплаты подписки
            : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoginLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        binding.handler = this
        setContentView(view)

        init()
        scope.launch { loadDataFromDB() }
        // Переход к окну восстановления пароля
        binding.textForgetPassword.setOnClickListener {
            val i = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
            startActivity(i)
        }
    }

    // Проверка наличия пользователя в системе
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val cUser = mAuth!!.currentUser
        if (cUser != null && (cUser.email == "root@mail.ru" || cUser.isEmailVerified)) {
            showSigned()
            val userName = "Вы вошли как: " + cUser.email
            binding.tvUserEmail.text = userName
        } else notSigned()
    }

    // Начальная инициализация
    private fun init() {
        registration = false // сбрасываем флаг регистрации

        // Инициализация Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Инициализация Firebase DataBase
        mDataBase = FirebaseDatabase.getInstance().getReference(PUPILS_KEY)
    }

    // Регистрация нового пользователя
    fun onClickSignUp(view: View?) {
        if (isConnected) {
            if (!TextUtils.isEmpty(binding.edName.text.toString()) && !TextUtils.isEmpty(binding.edLogin.text.toString()) && !TextUtils.isEmpty(
                    binding.edPassword.text.toString()
                ) && !registration!!
            ) {
                mAuth!!.createUserWithEmailAndPassword(binding.edLogin.text.toString().trim { it <= ' ' }
                    .toLowerCase(), binding.edPassword.text.toString().trim { it <= ' ' })
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //User registered successfully
                            showSigned()
                            sendEmailVer()
                            Toast.makeText(
                                applicationContext,
                                "Вы успешно зарегистрированы",
                                Toast.LENGTH_SHORT
                            ).show()
                            registration = true
                        } else {
                            notSigned()
                            if (binding.edPassword.text.toString().length < 6) Toast.makeText(
                                applicationContext,
                                "Пароль должен быть более 5 символов",
                                Toast.LENGTH_SHORT
                            ).show() else Toast.makeText(applicationContext, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                if (TextUtils.isEmpty(binding.edLogin.text.toString())) Toast.makeText(applicationContext, "Введите Email", Toast.LENGTH_SHORT).show()
                else if (TextUtils.isEmpty(binding.edPassword.text.toString()))
                    Toast.makeText(applicationContext, "Введите Пароль", Toast.LENGTH_SHORT).show()
                else if (TextUtils.isEmpty(binding.edName.text.toString()))
                    Toast.makeText(applicationContext, "Введите Фамилию и Имя", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, string.notInternet, Toast.LENGTH_SHORT).show()
    }

    // Вход пользователя в систему
    fun onClickSignIn(view: View?) {
        if (isConnected) {
            if (!TextUtils.isEmpty(binding.edLogin.text.toString()) && !TextUtils.isEmpty(binding.edPassword.text.toString())) {
                mAuth!!.signInWithEmailAndPassword(binding.edLogin.text.toString().trim { it <= ' ' }
                    .toLowerCase(), binding.edPassword.text.toString().trim { it <= ' ' })
                    .addOnCompleteListener(this) { task ->
                        val cUser = mAuth!!.currentUser
                        if (task.isSuccessful && ((binding.edLogin.text.toString().trim { it <= ' ' }
                                .toLowerCase() == "root@mail.ru") || cUser!!.isEmailVerified)) {
                            showSigned()
                            Toast.makeText(applicationContext, "Вход произведен успешно", Toast.LENGTH_SHORT).show()
                        } else {
                            notSigned()
                            Toast.makeText(applicationContext, "Ошибка входа", Toast.LENGTH_SHORT).show()
                            Toast.makeText(applicationContext, "Если Вы регистровались ранее, то проверьте свой Email для подтверждения регистрации", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                if (TextUtils.isEmpty(binding.edPassword.text.toString())) Toast.makeText(
                    applicationContext, "Введите Пароль", Toast.LENGTH_SHORT
                ).show() else if (TextUtils.isEmpty(
                        binding.edLogin.text.toString()
                    )
                ) Toast.makeText(applicationContext, "Введите Email", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, string.notInternet, Toast.LENGTH_SHORT).show()
    }

    // Выход пользователя из своего аккаунта
    fun onClickSignOut(view: View?) {
        FirebaseAuth.getInstance().signOut()
        notSigned()
    }

    // Отрисовка элементов
    private fun showSigned() {
        val user = mAuth!!.currentUser!!
        if (user.isEmailVerified || user.email.toString() == "root@mail.ru") {
            val userName = "Вы вошли как: " + user.email
            binding.tvUserEmail.text = userName
            binding.bStart.visibility = View.VISIBLE
            binding.bSignOut.visibility = View.VISIBLE
            binding.tvUserEmail.visibility = View.VISIBLE
            binding.edPassword.visibility = View.GONE
            binding.edLogin.visibility = View.GONE
            binding.textForgetPassword.visibility = View.GONE
            binding.edName.visibility = View.GONE
            binding.bSignIn.visibility = View.GONE
            binding.bSignUp.visibility = View.GONE
        } else Toast.makeText(applicationContext, string.checkYourEmail, Toast.LENGTH_SHORT).show()
    }

    private fun notSigned() {
        binding.bStart.visibility = View.GONE
        binding.bSignOut.visibility = View.GONE
        binding.tvUserEmail.visibility = View.GONE
        binding.edPassword.visibility = View.VISIBLE
        binding.edLogin.visibility = View.VISIBLE
        binding.textForgetPassword.visibility = View.VISIBLE
        binding.edName.visibility = View.VISIBLE
        binding.bSignIn.visibility = View.VISIBLE
        binding.bSignUp.visibility = View.VISIBLE
    }

    // Вход на страницу статистики
    fun onClickStart(view: View?) {
        if (isConnected) {
            val cUser = mAuth!!.currentUser
            assert(cUser != null)
            if ((cUser!!.email.toString() != "root@mail.ru") && (registration)!!) {
                // Добавляем нового пользователя в случае регистрации и если не root
                val newPupil = Pupils()
                newPupil.name = binding.edName.text.toString().trim { it <= ' ' }
                newPupil.email = binding.edLogin.text.toString().trim { it <= ' ' }.toLowerCase()
                val uid = mDataBase!!.push().key // получаем ключ для нового пользователя
                newPupil.id = uid
                mDataBase!!.child((uid)!!)
                    .setValue(newPupil) // загружаем данные в БД с конкретным ключом UID
                registration = false
                curPupil = newPupil
            }
            if (curPupil!!.subscription > 0) {
                // Считываем данные о подписки пользователя
                val dataSubscription = Calendar.getInstance()
                // устанавливаем месяц (0-Январь,..11 - Декабрь)
                dataSubscription[Calendar.MONTH] = curPupil!!.subscriptionMonth
                // устанавливаем дату
                dataSubscription[Calendar.DATE] = curPupil!!.subscriptionDay
                // устанавливаем год
                dataSubscription[Calendar.YEAR] = curPupil!!.subscriptionYear
                payDate = dataSubscription.time

                // Определяем текущую дату
                currentDate = getCurrentDate()

                // Проверяем действительна ли подписка на сегодняшний день
                if (!dateDifference(payDate, currentDate, curPupil!!.subscription)) {
                    // В случае окнончания действия подписки сбрасываем соответствующий флаг "subscription"
                    val hashMap: HashMap<String?, Any?> = HashMap<String?, Any?>()
                    hashMap.put("subscription",0)
                    mDataBase!!.child(curPupil!!.id).updateChildren(hashMap).addOnSuccessListener(
                        OnSuccessListener<Any?> { })
                    Toast.makeText(this, string.subscriptionFinish, Toast.LENGTH_LONG).show()
                }
            } else Toast.makeText(this, string.subscriptionNull, Toast.LENGTH_LONG).show()
            val i = Intent(this@LoginActivity, StatisticActivity::class.java)
            val userName = cUser.email
            i.putExtra(StatisticActivity.CURRENT_EMAIL, userName)
            startActivity(i)
        } else Toast.makeText(this, string.notInternet, Toast.LENGTH_SHORT).show()
    }

    // Решение задачи верификации
    private fun sendEmailVer() {
        val user = mAuth!!.currentUser
        assert(user != null)
        user!!.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void?> {
            override fun onComplete(task: Task<Void?>) {
                if (task.isSuccessful) Toast.makeText(
                    applicationContext,
                    string.checkYourEmail,
                    Toast.LENGTH_SHORT
                ).show() else Toast.makeText(
                    applicationContext, "Ошибка верификации", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Проверка подключения к интернету
    val isConnected: Boolean
        get() {
            var connected = false
            try {
                val cm = applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val nInfo = cm.activeNetworkInfo
                connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
                return connected
            } catch (e: Exception) {
                Log.e("Connectivity Exception", e.message!!)
            }
            return connected
        }

    fun dateDifference(startDate: Date?, endDate: Date?, statusSubscription: Int): Boolean {

        //milliseconds
        val different = endDate!!.time - startDate!!.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        // определяем количество дней с момента приобретения подписки
        val elapsedDays = different / daysInMilli

        if (statusSubscription > 0) {
            when (statusSubscription) {
                1 -> return if (elapsedDays < 90) {
                    checkSubscription(90 - elapsedDays.toInt())
                    true
                } else {
                    false
                }
                2 -> return if (elapsedDays < 150) {
                    checkSubscription(150 - elapsedDays.toInt())
                    true
                } else {
                    false
                }
                3 -> return if (elapsedDays < 300) {
                    checkSubscription(300 - elapsedDays.toInt())
                    true
                } else {
                    false
                }
                5 -> return if (elapsedDays < 31) {
                    checkSubscription(31 - elapsedDays.toInt())
                    true
                } else {
                    false
                }
                10 -> return true
            }
        }
        return false
    }

    fun getCurrentDate(): Date {
        var date = Date()
        try {
            val timeServer = "0.pool.ntp.org"
            val timeClient = NTPUDPClient()
            val inetAddress = InetAddress.getByName(timeServer)
            val timeInfo = timeClient.getTime(inetAddress)
            val time = timeInfo.message.receiveTimeStamp.time
            val cal: Calendar = GregorianCalendar()
            cal.timeInMillis = time
            date = cal.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun checkSubscription(i: Int) {
        when (i) {
            5 -> Toast.makeText(applicationContext, "До окончания Вашей подписки осталось 5 дней", Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(applicationContext, "До окончания Вашей подписки осталось 4 дня", Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(applicationContext, "До окончания Вашей подписки осталось 3 дня", Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(applicationContext, "До окончания Вашей подписки осталось 2 дня", Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(applicationContext, "До окончания Вашей подписки осталось 1 день", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromDB(curEmail: String?) {
        if (curEmail == "root@mail.ru") {
            val newPupil = Pupils(curEmail)
            curPupil = newPupil
        } else {
            mDataBase!!.orderByChild("email").equalTo(curEmail)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            val pupil = ds.getValue(Pupils::class.java)
                            if (pupil != null) {
                                curPupil = pupil
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
        }
    }

    suspend fun loadDataFromDB(){
        val cUser = mAuth!!.currentUser
        if (cUser != null) getDataFromDB(cUser.email) // если пользователь уже зарегистрирован
    }

}