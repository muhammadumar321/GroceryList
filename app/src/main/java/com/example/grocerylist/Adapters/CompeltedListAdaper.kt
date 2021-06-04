package com.example.grocerylist.Adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.Activity.MainActivity
import com.example.grocerylist.Fragments.ListItemFragment
import com.example.grocerylist.ModelClasses.HomeListsModel
import com.example.grocerylist.R
import com.example.imageeditor.Adapter.inflate
import java.util.*

class CompeltedListAdaper(var obj: MainActivity, var list: ArrayList<HomeListsModel>) :
    RecyclerView.Adapter<CompeltedListAdaper.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = parent.inflate(R.layout.home_rv_container, false)
        return CompeltedListAdaper.MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = list[position].name
        holder.itemView.setOnClickListener {
            obj.fragmentChangeListener(ListItemFragment(obj, list[position]))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}