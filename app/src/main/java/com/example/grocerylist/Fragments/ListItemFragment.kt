package com.example.grocerylist.Fragments

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.Activity.MainActivity
import com.example.grocerylist.Adapters.ItemListAdapter
import com.example.grocerylist.CallBacks.ListItemFragmentCallBacks
import com.example.grocerylist.DataBaseHelper.DataBase
import com.example.grocerylist.ModelClasses.HomeListsModel
import com.example.grocerylist.ModelClasses.ListItemModel
import com.example.grocerylist.R
import java.util.*


class ListItemFragment(var obj: MainActivity, var list: HomeListsModel) : Fragment(), ListItemFragmentCallBacks,
    View.OnClickListener {

    val list_id = list.id
    private lateinit var rvItemList: RecyclerView
    private lateinit var btnCompleted: RelativeLayout
    private lateinit var myAdapter: ItemListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_item, container, false)

        rvItemList = view.findViewById(R.id.list_item)
        btnCompleted = view.findViewById(R.id.btn_completed)
        btnCompleted.setOnClickListener(this)
        init()
        return view
    }

    private fun init() {
        if (list.status == 0) {
            btnCompleted.visibility = View.VISIBLE
        } else {
            btnCompleted.visibility = View.GONE
        }
        this.refreshItemRv()
    }

    override fun receiveItemData(nameValue: String, infoValue: String) {
        if (nameValue.isNotEmpty() && infoValue.isNotEmpty()) {
            val db = DataBase(activity, DataBase.DB_NAME, null, DataBase.DB_VERSION)
            if (db.InsertItem(list_id, nameValue, infoValue)) {
                Toast.makeText(activity, "Item Added", Toast.LENGTH_SHORT)
                    .show()
                this.refreshItemRv()
            } else {
                Toast.makeText(activity, "Insertion Failed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun refreshItemRv() {
        myAdapter = ItemListAdapter(getItemList(list_id))
        val mLayoutManager = LinearLayoutManager(context)
        rvItemList.layoutManager = mLayoutManager
        rvItemList.adapter = myAdapter
    }

    private fun getItemList(list_id: Int): ArrayList<ListItemModel> {
        val list = ArrayList<ListItemModel>()
        val db = DataBase(context, DataBase.DB_NAME, null, DataBase.DB_VERSION)
        val cursor: Cursor = db.DisplayItems(list_id)
        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val listID = cursor.getInt(1)
                    val name = cursor.getString(2)
                    val info = cursor.getString(3)
                    list.add(ListItemModel(id, listID, name, info))
                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(activity, "No Data List Available", Toast.LENGTH_SHORT).show()
        }
        return list
    }

    override fun onClick(v: View?) {
        when (v) {
            btnCompleted -> {
                val db = DataBase(context, DataBase.DB_NAME, null, DataBase.DB_VERSION)
                db.updateList(list.id, 1)
                Toast.makeText(obj, "Mark As Completed", Toast.LENGTH_SHORT).show()
                btnCompleted.visibility = View.GONE
            }
        }
    }
}