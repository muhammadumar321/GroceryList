package com.example.grocerylist.CallBacks

import android.widget.CheckedTextView
import androidx.fragment.app.Fragment

interface MainActivityCallBacks {
    fun fragmentChangeListener(fragment:Fragment)
    fun navItemSelectedListener(item:CheckedTextView)
}