package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Paket
import kotlinx.android.synthetic.main.activity_paket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaketActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var paketAdapter: PaketAdapter
    //menampilkan semua data //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadPaket()
    }

    fun loadPaket(){
        CoroutineScope(Dispatchers.IO).launch {
            val allPaket = db.paktDao().getAllPaket()
            Log.d("PaketActivity", "dbResponse: $allPaket")
            withContext(Dispatchers.Main) {
                paketAdapter.setData(allPaket)
            }
        }
    }

    fun setupListener() {
        btn_createPaket.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        paketAdapter = PaketAdapter(arrayListOf(), object: PaketAdapter.OnAdapterListener {
            override fun onClick(paket: Paket) {
                // read detail
                intentEdit(paket.id, Constant.TYPE_READ)
            }

            override fun onDelete(paket: Paket) {
                // delete data
                deleteDialog(paket)
            }

            override fun onUpdate(paket: Paket) {
                // edit data
                intentEdit(paket.id, Constant.TYPE_UPDATE)
            }

        })
        list_paket.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = paketAdapter
        }
    }

    fun intentEdit(siswaId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditPaketActivity::class.java)
                .putExtra("intent_id", siswaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(paket: Paket) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.paktDao().deletePaket(paket)
                    loadPaket()
                }
            }
        }
        alertDialog.show()
    }
}