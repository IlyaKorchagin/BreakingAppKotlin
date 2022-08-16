package com.example.goodfootbreaking.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import android.text.TextUtils
import android.widget.Toast
import android.content.Intent
import android.view.View
import com.example.goodfootbreaking.databinding.ActivityForgetPasswordBinding


class ForgetPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityForgetPasswordBinding? = null
    private val binding get() = _binding!!

    private val PUPILS_KEY = "Pupils" //  идентификатор таблицы в БД
    private var mDataBase //  ссылка на БД
            : DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        binding.handler = this
        setContentView(view)
        init()
    }

    private fun init() {
        // Инициализация Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Инициализация Firebase DataBase
        mDataBase = FirebaseDatabase.getInstance().getReference(PUPILS_KEY)
    }

    fun onClickResetPassword(view: View?) {
        if (TextUtils.isEmpty(binding.edPasswordEmail.text.toString())) {
            Toast.makeText(applicationContext, "Введите Email", Toast.LENGTH_SHORT).show()
        } else {
            mAuth!!.sendPasswordResetEmail(binding.edPasswordEmail.text.toString().trim { it <= ' ' }
                .toLowerCase()).addOnSuccessListener {
                Toast.makeText(
                    applicationContext,
                    "Вам на почту отправленно сообщение о смене пароля",
                    Toast.LENGTH_SHORT
                ).show()
                val i = Intent(this@ForgetPasswordActivity, LoginActivity::class.java)
                startActivity(i)
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Ошибка! Сообщение не отправленно.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}