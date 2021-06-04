package com.example.grocerylist.CallBacks

interface ListItemFragmentCallBacks {
    fun receiveItemData(nameValue: String, infoValue: String)
    fun refreshItemRv()
}