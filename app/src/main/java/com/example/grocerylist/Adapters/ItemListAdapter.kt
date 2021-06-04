package com.example.grocerylist.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.ModelClasses.ListItemModel
import com.example.grocerylist.R
import com.example.imageeditor.Adapter.inflate
import java.util.*

class ItemListAdapter(var itemList: ArrayList<ListItemModel>) :
    RecyclerView.Adapter<ItemListAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = parent.inflate(R.layout.list_item_rv_container, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = itemList[position].name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}