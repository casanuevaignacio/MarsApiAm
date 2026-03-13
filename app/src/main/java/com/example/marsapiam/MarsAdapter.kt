
package com.example.marsapiam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marsapiam.databinding.ItemMarsBinding
import Model.Remote.MarsRealState



class MarsAdapter : RecyclerView.Adapter<MarsAdapter.MarsViewHolder>() {


    private var marsList = mutableListOf<MarsRealState>()

    // 1. Agrega esta variable para el click
    private var onItemClickListener: ((MarsRealState) -> Unit)? = null

    // 2. Función para asignar el click desde el Fragmento
    fun setOnItemClickListener(listener: (MarsRealState) -> Unit) {
        onItemClickListener = listener
    }

    inner class MarsViewHolder(val binding: ItemMarsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        val binding = ItemMarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        val terrain = marsList[position]
        holder.binding.tvType.text = terrain.type
        holder.binding.tvPrice.text = terrain.price.toString()

        Glide.with(holder.itemView.context).load(terrain.img_src).into(holder.binding.imgMars)

        // 3. ASIGNAR EL CLICK AL ITEM COMPLETO
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(terrain)
        }
    }

    override fun getItemCount(): Int = marsList.size

    fun setData(list: List<MarsRealState>) {
        marsList = list.toMutableList()
        notifyDataSetChanged()
    }
}