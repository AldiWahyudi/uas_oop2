package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Kurir
import kotlinx.android.synthetic.main.activity_kurir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KurirActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var kurirAdapter: KurirAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kurir)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadKurir()
    }
//menampilkan semua data //
    fun loadKurir() {
        CoroutineScope(Dispatchers.IO).launch {
            val allKurir = db.kurirDao().getAllKurir()
            Log.d("KurirActivity", "dbResponse: $allKurir")
            withContext(Dispatchers.Main) {
                kurirAdapter.setData(allKurir)
            }
        }
    }

    fun setupListener() {
        btn_createKurir.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        kurirAdapter = KurirAdapter(arrayListOf(), object: KurirAdapter.OnAdapterListener {
            override fun onClick(kurir: Kurir) {
                intentEdit(kurir.id, Constant.TYPE_READ)
            }
            override fun onDelete(kurir: Kurir) {
                deleteDialog(kurir)
            }
            override fun onUpdate(kurir: Kurir) {
                // edit data
                intentEdit(kurir.id, Constant.TYPE_UPDATE)
            }

        })
        list_kurir.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = kurirAdapter
        }
    }

    fun intentEdit(kurirId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditKurirActivity::class.java)
                .putExtra("intent_id", kurirId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(Kurir: Kurir) {
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
                    db.kurirDao().deleteKurir(Kurir)
                    loadKurir()
                }
            }
        }
        alertDialog.show()
    }
}