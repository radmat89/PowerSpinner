package com.skydoves.powerspinner

fun <T> PowerSpinnerView.getItems(): List<T> {
    return getSpinnerAdapter<T>().getItems()
}

fun <T> PowerSpinnerView.getSelectedItem(): T? {
    return getSpinnerAdapter<T>().selectedItem
}

fun <T> PowerSpinnerView.selectItem(item: T): Boolean {
    return getSpinnerAdapter<T>().selectItem(item)
}
