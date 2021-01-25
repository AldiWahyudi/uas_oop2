package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_paket.setOnClickListener{
            val intent = Intent(this, PaketActivity::class.java) //pindah ke paket activity//
            startActivity(intent)
        }

        btn_kurir.setOnClickListener{
            val intent = Intent(this, KurirActivity::class.java)  //pindah ke kurir activity//
            startActivity(intent)
        }
    }
}