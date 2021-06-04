package com.example.grocerylist.Activity

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.grocerylist.CallBacks.MainActivityCallBacks
import com.example.grocerylist.DataBaseHelper.DataBase
import com.example.grocerylist.Fragments.AllListsFragment
import com.example.grocerylist.Fragments.CompletedListFragment
import com.example.grocerylist.Fragments.HomeFragment
import com.example.grocerylist.Fragments.ListItemFragment
import com.example.grocerylist.R
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, MainActivityCallBacks {

    private lateinit var home: CheckedTextView
    private lateinit var allList: CheckedTextView
    private lateinit var completedList: CheckedTextView
    private lateinit var fab: CardView
    private lateinit var label: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        home = findViewById(R.id.home)
        allList = findViewById(R.id.all_list)
        completedList = findViewById(R.id.completed)
        label = findViewById(R.id.label)
        fab = findViewById(R.id.fab)
        initClicks()
    }

    private fun initClicks() {
        home.setOnClickListener(this)
        allList.setOnClickListener(this)
        completedList.setOnClickListener(this)
        this.navItemSelectedListener(home)


    }

    override fun onClick(v: View?) {
        when (v) {
            home -> {
                this.navItemSelectedListener(home)
            }
            allList -> {

                this.navItemSelectedListener(allList)
            }
            completedList -> {

                this.navItemSelectedListener(completedList)
            }
        }
    }

    override fun fragmentChangeListener(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
        when (fragment) {
            is HomeFragment -> {
                label.text = "Home"
                fab.setOnClickListener {
                    addHomeListDialoge(fragment)
                }
            }
            is AllListsFragment -> {
                label.text = "All Lists"
                fab.setOnClickListener {
                    addAllListDialoge(fragment)
                }
            }
            is CompletedListFragment -> {
                label.text = "Completed Lists"
                fab.setOnClickListener {
                    addCompletedListDialoge(fragment)
                }
            }
            is ListItemFragment -> {
                label.text = "List Item"
                fab.setOnClickListener {
                    addNewListItem(fragment)
                }
            }
        }
    }

    private fun addCompletedListDialoge(fragment: CompletedListFragment): Boolean {
        var status = 1
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        val main_dialog = Dialog(this)
        main_dialog.setContentView(R.layout.attention_dialogue)
        Objects.requireNonNull(main_dialog.window)!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        main_dialog.window!!.setLayout((width / 1.1f).toInt(), (height / 1.3).toInt())
        main_dialog.setCancelable(false)
        val cancel: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_cancel)
        val name: EditText = main_dialog.findViewById<EditText>(R.id.list_name)
        val submit: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_submit)
        cancel.setOnClickListener {
            main_dialog.dismiss()
        }
        submit.setOnClickListener(View.OnClickListener {
            val nameValue = name.text.toString()
            if (nameValue.isNotEmpty()) {
                val db = DataBase(this, DataBase.DB_NAME, null, DataBase.DB_VERSION)
                if (db.InsertList(nameValue, status)) {
                    Log.d("TAG", "List Added by $nameValue and $status")
                } else {
                    Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT)
                        .show()
                }
                main_dialog.dismiss()
                fragment.refreshAdapter()
            } else {
                name.error = "Name must not be Empty"
            }
        })
        main_dialog.show()
        return true
    }


    private fun addNewListItem(fragment: ListItemFragment): Boolean {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        val main_dialog = Dialog(this)
        main_dialog.setContentView(R.layout.list_item_container)
        Objects.requireNonNull(main_dialog.window)!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        main_dialog.window!!.setLayout((width / 1.1f).toInt(), (height / 1.3).toInt())
        main_dialog.setCancelable(false)
        val cancel: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_cancel)
        val name: EditText = main_dialog.findViewById<EditText>(R.id.item_name)
        val info: EditText = main_dialog.findViewById<EditText>(R.id.item_info)
        val submit: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_submit)
        cancel.setOnClickListener {
            main_dialog.dismiss()
        }
        submit.setOnClickListener(View.OnClickListener {
            val nameValue = name.text.toString()
            val infoValue = info.text.toString()
            if (nameValue.isNotEmpty()) {
                fragment.receiveItemData(nameValue, infoValue)
                main_dialog.dismiss()
            } else {
                name.error = "Name must not be Empty"
            }
        })
        main_dialog.show()
        return true
    }

    override fun navItemSelectedListener(item: CheckedTextView) {
        when (item) {
            home -> {
                item.isChecked = true
                allList.isChecked = false
                completedList.isChecked = false

                this.fragmentChangeListener(HomeFragment(this))
            }
            allList -> {
                item.isChecked = false
                allList.isChecked = true
                completedList.isChecked = false

                this.fragmentChangeListener(AllListsFragment(this))
            }
            completedList -> {
                item.isChecked = false
                allList.isChecked = false
                completedList.isChecked = true

                this.fragmentChangeListener(CompletedListFragment(this))
            }
        }
    }

    private fun addAllListDialoge(fragment: AllListsFragment): Boolean {
        var status = 0
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        val main_dialog = Dialog(this)
        main_dialog.setContentView(R.layout.attention_dialogue)
        Objects.requireNonNull(main_dialog.window)!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        main_dialog.window!!.setLayout((width / 1.1f).toInt(), (height / 1.3).toInt())
        main_dialog.setCancelable(false)
        val cancel: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_cancel)
        val name: EditText = main_dialog.findViewById<EditText>(R.id.list_name)
        val submit: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_submit)
        cancel.setOnClickListener {
            main_dialog.dismiss()
        }
        submit.setOnClickListener(View.OnClickListener {
            val nameValue = name.text.toString()
            if (nameValue.isNotEmpty()) {
                val db = DataBase(this, DataBase.DB_NAME, null, DataBase.DB_VERSION)
                if (db.InsertList(nameValue, status)) {
                    Log.d("TAG", "List Added by $nameValue and $status")
                } else {
                    Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT)
                        .show()
                }
                main_dialog.dismiss()
                fragment.refreshFragments()
            } else {
                name.error = "Name must not be Empty"
            }
        })
        main_dialog.show()
        return true
    }

    fun addHomeListDialoge(fragment: HomeFragment): Boolean {
        var status = 0
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        val main_dialog = Dialog(this)
        main_dialog.setContentView(R.layout.attention_dialogue)
        Objects.requireNonNull(main_dialog.window)!!
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        main_dialog.window!!.setLayout((width / 1.1f).toInt(), (height / 1.3).toInt())
        main_dialog.setCancelable(false)
        val cancel: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_cancel)
        val name: EditText = main_dialog.findViewById<EditText>(R.id.list_name)
        val submit: RelativeLayout = main_dialog.findViewById<RelativeLayout>(R.id.btn_submit)
        cancel.setOnClickListener {
            main_dialog.dismiss()
        }
        submit.setOnClickListener(View.OnClickListener {
            val nameValue = name.text.toString()
            if (nameValue.isNotEmpty()) {
                val db = DataBase(this, DataBase.DB_NAME, null, DataBase.DB_VERSION)
                if (db.InsertList(nameValue, status)) {
                    Log.d("TAG", "List Added by $nameValue and $status")
                } else {
                    Toast.makeText(this, "Insertion Failed", Toast.LENGTH_SHORT)
                        .show()
                }
                main_dialog.dismiss()
                fragment.refreshAdapter()
            } else {
                name.error = "Name must not be Empty"
            }
        })
        main_dialog.show()
        return true
    }

}
