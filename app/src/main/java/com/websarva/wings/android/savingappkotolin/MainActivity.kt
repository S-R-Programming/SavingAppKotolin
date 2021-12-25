package com.websarva.wings.android.savingappkotolin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //「新規登録」ボタンを押したとき、アカウント作成画面へ
    fun createAccount(view: View){
        val intentAccount = Intent(this@MainActivity,CreateAccountActivity::class.java)
        startActivity(intentAccount)
    }

    //「ログイン」ボタンを押したとき
    fun login(view: View){
        val intentLogin = Intent(this@MainActivity,LoginActivity::class.java)
        startActivity(intentLogin)
    }
}