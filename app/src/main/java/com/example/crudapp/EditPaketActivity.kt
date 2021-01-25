package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Paket
import kotlinx.android.synthetic.main.activity_edit_paket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPaketActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var paketId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_paket)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_savePaket.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.paktDao().addPaket(
                    Paket(0, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_no.text.toString()) )
                )
                finish()
            }
        }
        btn_updatePaket.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.paktDao().updatePaket(
                    Paket(paketId, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_no.text.toString()) )
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
                btn_updatePaket.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_savePaket.visibility = View.GONE
                btn_updatePaket.visibility = View.GONE
                getPaket()
            }
            Constant.TYPE_UPDATE -> {
                btn_savePaket.visibility = View.GONE
                getPaket()
            }
        }
    }

    fun getPaket() {
        paketId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val pakets =  db.paktDao().getPaket( paketId )[0]
            txt_nama.setText( pakets.nama )
            txt_alamat.setText( pakets.no.toString() )
            txt_no.setText( pakets.no.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}