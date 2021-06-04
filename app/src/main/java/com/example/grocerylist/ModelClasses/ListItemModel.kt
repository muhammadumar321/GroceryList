package com.example.grocerylist.ModelClasses

import java.io.Serializable

class ListItemModel (id: Int,listID:Int, name: String, info: String) : Serializable {

    var id = id
    var listID = listID
    var name = name
    var info = info
}