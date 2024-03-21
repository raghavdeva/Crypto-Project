package com.example.crypto

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.databinding.RvLayoutBinding

class RvAdapter (val context: Context, var data:ArrayList<ViewModal>):
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : RvLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.Name.text = data[position].name
        holder.binding.Symbol.text = data[position].Symbol
        holder.binding.Price.text = data[position].price
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun changedata(filterdata : ArrayList<ViewModal>){
        data = filterdata
        notifyDataSetChanged()
    }

}