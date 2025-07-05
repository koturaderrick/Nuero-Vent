package com.nuerovent.model

data class HomeItem(
    val label: String,
    val reading: String
)

interface OnItemClick {
    fun onItemClicked(item: HomeItem)
}