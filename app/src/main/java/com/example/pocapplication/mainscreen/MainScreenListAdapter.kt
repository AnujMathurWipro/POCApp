package com.example.pocapplication.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocapplication.R
import com.example.pocapplication.databinding.ItemListBinding
import com.example.pocapplication.models.RowsItem

class MainScreenListAdapter(context: Context?, private var list: List<RowsItem?>?) : RecyclerView.Adapter<ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListBinding = DataBindingUtil.inflate(inflater, R.layout.item_list, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding: ItemListBinding? = DataBindingUtil.getBinding(holder.itemView)
        val item = list?.get(position)
        binding?.item = item
        binding?.executePendingBindings()
    }

    fun setListItems(items: List<RowsItem?>?) {
        list = items
        notifyDataSetChanged()
    }

}