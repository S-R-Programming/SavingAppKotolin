package com.websarva.wings.android.savingappkotolin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.util.ArrayList

class ResumeActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var email:String
    private lateinit var what_edit: EditText
    private lateinit var price_edit: EditText
    private lateinit var sum_text: TextView
    private lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_main)
        database = Firebase.database.reference
        email = "shiomotoandprogramming@gmail"
        what_edit = findViewById<EditText>(R.id.what)
        listview=findViewById<ListView>(R.id.listview)
        price_edit = findViewById<EditText>(R.id.price)
        setup()
    }

    fun setup() {
        var reference: DatabaseReference = database.child(email).child(getCurrentYearAndMonth())
        //.setValue("aiueo")
        //reference.setValue("ass")
        //reference.setValue("aiuas")//setValueだと最後だけしか追加されない
        reference.child("aiueo").setValue("aiueo")
        reference.child("ass").setValue("ass")
        reference.child("aiuas").setValue("aiuas")
        reference.child("bbb").setValue("bbb")
        reference.child("bbba").setValue("baaa")
        reference.child("bllll").setValue("blll")
        reference.child("bww").setValue("bwww")
        reference.child("brr").setValue("brr")
        reference.child("bvvv").setValue("bvvv")
        reference.child("btt").setValue("btt")
        reference.child("bvs").setValue("abee")
        reference.child("bkk").setValue("vkk")
        reference.child("dgf").setValue("grefv")
        reference.child("g3w").setValue("bfg")
        reference.child("myt").setValue("gse")
        var query = reference.orderByKey()
        query.addValueEventListener(object : ValueEventListener {
            //setupとしてだけでなく、データの更新もしてくれる
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var resumeList: MutableList<String?> = ArrayList()
                //StringBuilder sb = new StringBuilder();
                for (snapshot in dataSnapshot.children) { //detailの中身を全てget
                    var key = snapshot.key
                    var price_int=snapshot.getValue()
                    resumeList.add(key +" "+price_int+"円")
                    //  sb.append(key+"円");
                    //sb.append("\n");
                }
                var adapter = ArrayAdapter(this@ResumeActivity,R.layout.list_text,resumeList)
                listview.adapter=adapter
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

}