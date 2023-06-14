package com.akmalmf.nutrimatch.abstraction.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {

    protected var items: MutableList<T> = mutableListOf()

    var onItemClick: ((T) -> Unit)? = null

    abstract fun onBindViewHolder(holder: VH, item: T, position: Int)


    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, this.items[position], position)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    fun getItem(position: Int): T? = items[position]

    private fun addItem(item: MutableList<T>) {
        clear()
        this.items.addAll(item)
        this.notifyDataSetChanged()
    }

    private fun clear() {
        this.items.clear()
    }

    fun findItem(itemToFind: T): Int {
        return items.indexOf(itemToFind)
    }

    fun editItem(editedItem: T, position: Int){
        if(position >= 0){
            this.items[position] = editedItem
            this.notifyItemChanged(position)
        }
    }

    fun removeItem(deleteItem: T) {
        this.items.removeIf { it == deleteItem }
        this.notifyDataSetChanged()
    }

    fun setItem(item: MutableList<T>) {
        clear()
        addItem(item)
    }

    fun getItem(): MutableList<T> {
        return items
    }

    fun addSingleItem(item: T){
        this.items.add(item)
        this.notifyItemChanged(items.size-1)
    }
}