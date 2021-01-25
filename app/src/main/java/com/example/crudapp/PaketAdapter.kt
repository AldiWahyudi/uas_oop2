package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Paket
import kotlinx.android.synthetic.main.adapter_paket.view.*

class PaketAdapter (private val allPaket: ArrayList<Paket>, private val listener: OnAdapterListener) : RecyclerView.Adapter<PaketAdapter.HelmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelmViewHolder {
        return HelmViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_paket, parent, false)
        )
    }

    override fun getItemCount() = allPaket.size

    override fun onBindViewHolder(holder: HelmViewHolder, position: Int) {
        val siswa = allPaket[position]
        holder.view.text_nama.text = siswa.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(siswa)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(siswa)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(siswa)
        }
    }

    class HelmViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Paket>) {
        allPaket.clear()
        allPaket.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(paket: Paket)
        fun onDelete(paket: Paket)
        fun onUpdate(paket: Paket)
    }
}