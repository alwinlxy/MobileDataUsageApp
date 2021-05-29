package com.example.android.mobiledatausage.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.databinding.FragmentItemBinding

class AnnualMobileDataRecyclerViewAdapter(
    private val values: List<DbAnuualMobileData>
) : RecyclerView.Adapter<AnnualMobileDataRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.yearView.text = item.year.toString()
        holder.volumeView.text = item.totalVolume.toString()
        if(item.decrease) {
            holder.imageView.visibility = View.VISIBLE
            holder.imageView.setOnClickListener {
                Toast.makeText(it.context, "Year ${item.year} has decrease in volume data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val yearView: TextView = binding.year
        val volumeView: TextView = binding.volume
        val imageView: ImageView = binding.imageView


    }

}