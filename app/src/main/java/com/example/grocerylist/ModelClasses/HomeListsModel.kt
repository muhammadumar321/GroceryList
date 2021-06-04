package com.example.grocerylist.ModelClasses

import java.io.Serializable

class HomeListsModel (id: Int, name: String, status: Int) : Serializable {

    var id = id
    var name = name
    var status = status
}