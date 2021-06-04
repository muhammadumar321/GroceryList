package com.example.grocerylist.Fragments

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.Activity.MainActivity
import com.example.grocerylist.Adapters.AllListAdapter
import com.example.grocerylist.CallBacks.AllListFragmentCallBack
import com.example.grocerylist.DataBaseHelper.DataBase
import com.example.grocerylist.ModelClasses.HomeListsModel
import com.example.grocerylist.R
import java.util.*

class AllListsFragment(var obj: MainActivity) : Fragment(), AllListFragmentCallBack {
    private lateinit var rvAllLists: RecyclerView
    private lateinit var myAdapter: AllListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_lists, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        rvAllLists = view.findViewById(R.id.rv_all_lists)
        this.refreshFragments()
    }

    override fun refreshFragments() {
        myAdapter = AllListAdapter(obj,getList())
        val mLayoutManager = LinearLayoutManager(context)
        rvAllLists.layoutManager = mLayoutManager
        rvAllLists.adapter = myAdapter
    }

    private fun getList(): ArrayList<HomeListsModel> {
        val list = ArrayList<HomeListsModel>()
        val db = DataBase(context, DataBase.DB_NAME, null, DataBase.DB_VERSION)
        val cursor: Cursor = db.DisplayList()
        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(0)
                    val name = cursor.getString(1)
                    val status = cursor.getInt(2)
                    list.add(HomeListsModel(id, name, status))
                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(activity, "No Data List Available", Toast.LENGTH_SHORT).show()
        }
        return list
    }
}