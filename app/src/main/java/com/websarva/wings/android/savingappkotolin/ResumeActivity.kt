package com.websarva.wings.android.savingappkotolin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.reflect.typeOf

class ResumeActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var email:String
    private lateinit var what_edit: EditText
    private lateinit var price_edit: EditText
    private lateinit var year_edit: EditText
    private lateinit var month_edit: EditText
    private lateinit var sum_text: TextView
    private lateinit var sum_top: TextView
    private lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)
        database = Firebase.database.reference
        this.email = intent.getStringExtra("email").toString()
        what_edit = findViewById<EditText>(R.id.what)
        listview=findViewById<ListView>(R.id.listview)
        price_edit = findViewById<EditText>(R.id.price)
        sum_text = findViewById<TextView>(R.id.sum)
        sum_top = findViewById<TextView>(R.id.sum_top)
        year_edit = findViewById<EditText>(R.id.year_edit)
        month_edit = findViewById<EditText>(R.id.month_edit)
        setup()
    }

    fun setup() {
        var reference: DatabaseReference = database.child(email).child(getCurrentYearAndMonth())
        //.setValue("aiueo")
        //reference.setValue("ass")
        //reference.setValue("aiuas")//setValueだと最後だけしか追加されない
        reference.child("aiueo").setValue(100)
        reference.child("ass").setValue(432)
        reference.child("aiuas").setValue(133)
        reference.child("bbb").setValue(1444)
        reference.child("bbba").setValue(65)
        reference.child("bllll").setValue(847)
        reference.child("bww").setValue(746)
        reference.child("brr").setValue(25)
        reference.child("bvvv").setValue(543)
        reference.child("btt").setValue(765)
        reference.child("bvs").setValue(8764)
        reference.child("bkk").setValue(54)
        reference.child("dgf").setValue(87)
        reference.child("g3w").setValue(43)
        reference.child("myt").setValue(431)
        var query = reference.orderByKey()
        var sum:Int = 0//今月の合計金額をカウント
        query.addValueEventListener(object : ValueEventListener {
            //setupとしてだけでなく、データの更新もしてくれる
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resumeList: MutableList<String?> = ArrayList()
                //StringBuilder sb = new StringBuilder();
                for (snapshot in dataSnapshot.children) {
                    var key = snapshot.key
                    var price: Int? = snapshot.getValue(Int :: class.java)
                    sum=sum + price!!
                    resumeList.add(key)
                }
                var adapter = ArrayAdapter(this@ResumeActivity,R.layout.list_text,resumeList)
                listview.adapter=adapter
                sum_top.text  = getCurrentYearAndMonth()+"月の節約金額"
                sum_text.text = sum.toString()+"円"//合計金額を表示
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    fun getCurrentYearAndMonth():String{
        var current = LocalDateTime.now()
        var formatter = DateTimeFormatter.ISO_DATE
        var formatted = current.format(formatter)//2019-03-22 現在時刻
        var year_month:String=formatted.dropLast(3)//2019-03
        return year_month
    }

    fun add(view: View){
        var what_text = what_edit.text.toString()
        Log.i("what_text",what_text)//OK
        var price_text = price_edit.text.toString()
        Log.i("price_text",price_text)//OK
        //登録ボタンを押したとき
        if ((what_text== "" )== false && (price_text== "" )== false) {
            //12月31日 07:31 ビール 120円
            val detail = getDay() + " " + getTime() + " " + what_text+ " " + price_text+"円"
            //今月を指定
            var reference: DatabaseReference = database.child(email).child(getCurrentYearAndMonth())
            var price_int:Int? = price_text.toInt()//ここに問題あり
            Log.i("price_info",price_int.toString())
            reference.child(detail).setValue(price_int)
            var query = reference.orderByKey()
            var sum:Int = 0//今月の合計金額をカウント
            query.addValueEventListener(object : ValueEventListener {
                //setupとしてだけでなく、データの更新もしてくれる
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var resumeList: MutableList<String?> = ArrayList()
                    for (snapshot in dataSnapshot.children) { //detailの中身を全てget
                        var key = snapshot.key
                        var price: Int? = snapshot.getValue(Int :: class.java)
                        sum=sum + price!!
                        resumeList.add(key)
                    }
                    var adapter = ArrayAdapter(this@ResumeActivity,R.layout.list_text,resumeList)
                    listview.adapter=adapter
                    sum_text.text = sum.toString()+"円"//合計金額を表示
                    sum_top.text  = getCurrentYearAndMonth()+"月の節約金額"
                    sum_text.text = sum.toString()+"円"//合計金額を表示
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        }
    }

    fun search(view: View){
        var year_text = year_edit.text.toString()
        Log.i("what_text",year_text)//OK
        var month_text = month_edit.text.toString()
        Log.i("price_text",month_text)//OK
        //登録ボタンを押したとき
        if ((year_text== "" )== false && (month_text== "" )== false) {
            var reference: DatabaseReference = database.child(email).child(year_text+"-"+month_text)
            var query = reference.orderByKey()
            var sum:Int = 0//月の合計金額をカウント
            query.addValueEventListener(object : ValueEventListener {
                //setupとしてだけでなく、データの更新もしてくれる
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var resumeList: MutableList<String?> = ArrayList()
                    for (snapshot in dataSnapshot.children) { //detailの中身を全てget
                        var key = snapshot.key
                        var price: Int? = snapshot.getValue(Int :: class.java)
                        sum=sum + price!!
                        resumeList.add(key)
                    }
                    var adapter = ArrayAdapter(this@ResumeActivity,R.layout.list_text,resumeList)
                    listview.adapter=adapter
                    sum_text.text = sum.toString()+"円"//合計金額を表示
                    sum_top.text  = year_text+"-"+month_text+"月の節約金額"
                    sum_text.text = sum.toString()+"円"//合計金額を表示
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }


    }

    private fun getDay(): String? {
        val charSequence = DateFormat.format("MM月dd日", Calendar.getInstance())
        return charSequence.toString()
    }

    private fun getTime(): String? {
        val charSequence = DateFormat.format("HH:mm", Calendar.getInstance())
        return charSequence.toString()
    }

}