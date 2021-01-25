package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Kurir
import kotlinx.android.synthetic.main.activity_edit_kurir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditKurirActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var kurirId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_kurir)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveKurir.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.kurirDao().addKurir(
                    Kurir(0, txt_namaL.text.toString(), Integer.parseInt(txt_nohp.text.toString()))
                )
                finish()
            }
        }
        btn_updateKurir.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.kurirDao().updateKurir(
                    Kurir(kurirId, txt_namaL.text.toString(), Integer.parseInt(txt_nohp.text.toString()))
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateKurir.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveKurir.visibility = View.GONE
                btn_updateKurir.visibility = View.GONE
                getKurir()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveKurir.visibility = View.GONE
                getKurir()
            }
        }
    }

    fun getKurir() {
        kurirId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val kurirs =  db.kurirDao().getKurir( kurirId )[0]
            txt_namaL.setText( kurirs.nama )
            txt_nohp.setText( kurirs.nohp )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}