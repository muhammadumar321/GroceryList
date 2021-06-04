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
import com.example.grocerylist.Adapters.CompeltedListAdaper
import com.example.grocerylist.CallBacks.CompletedFragmentCallBacks
import com.example.grocerylist.DataBaseHelper.DataBase
import com.example.grocerylist.ModelClasses.HomeListsModel
import com.example.grocerylist.R
import java.util.*

class CompletedListFragment(var obj: MainActivity) : Fragment(), CompletedFragmentCallBacks {
    private lateinit var rvCompleted: RecyclerView
    private lateinit var myAdapter: CompeltedListAdaper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_completed_list, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        rvCompleted = view.findViewById(R.id.rv_completed_lists)
        this.refreshAdapter()
    }

    private fun getList(): ArrayList<HomeListsModel> {
        val list = ArrayList<HomeListsModel>()
        val db = DataBase(context, DataBase.DB_NAME, null, DataBase.DB_VERSION)
        val cursor: Cursor = db.DisplayList()
        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(2) == 1) {
                        val id = cursor.getInt(0)
                        val name = cursor.getString(1)
                        val status = cursor.getInt(2)
                        list.add(HomeListsModel(id, name, status))
                    }
                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(activity, "No Data List Available", Toast.LENGTH_SHORT).show()
        }
        return list
    }

    override fun refreshAdapter() {
        myAdapter = CompeltedListAdaper(obj,getList())
        val mLayoutManager = LinearLayoutManager(context)
        rvCompleted.layoutManager = mLayoutManager
        rvCompleted.adapter = myAdapter
    }

}