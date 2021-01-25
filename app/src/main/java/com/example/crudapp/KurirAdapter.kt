package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Kurir
import kotlinx.android.synthetic.main.adapter_kurir.view.*

class KurirAdapter (private val allKurir: ArrayList<Kurir>, private val listener: OnAdapterListener) : RecyclerView.Adapter<KurirAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_kurir, parent, false)
        )
    }

    override fun getItemCount() = allKurir.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val kurir = allKurir[position]
        holder.view.text_nohp.text = kurir.nama
        holder.view.text_nohp.setOnClickListener {
            listener.onClick(kurir)
        }
        holder.view.icon_deleteKurir.setOnClickListener {
            listener.onDelete(kurir)
        }
        holder.view.icon_editKurir.setOnClickListener {
            listener.onUpdate(kurir)
        }
    }

    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Kurir>) {
        allKurir.clear()
        allKurir.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(kurir: Kurir)
        fun onDelete(kurir: Kurir)
        fun onUpdate(kurir: Kurir)
    }

}