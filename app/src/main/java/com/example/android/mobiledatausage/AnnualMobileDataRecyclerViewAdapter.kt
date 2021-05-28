package com.example.android.mobiledatausage

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.android.mobiledatausage.databinding.FragmentItemBinding

import com.example.android.mobiledatausage.model.AnnualMobileData

class AnnualMobileDataRecyclerViewAdapter(
    private val values: List<AnnualMobileData>
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
        holder.volumeView.text = item.volume.toString()
        if(item.hasDecrease()) {
            holder.imageView.visibility = View.VISIBLE
            holder.imageView.setOnClickListener {
                //TODO set a Toast Message
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