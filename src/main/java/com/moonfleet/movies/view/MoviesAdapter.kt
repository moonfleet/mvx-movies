package com.moonfleet.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class MoviesAdapter<T>(private var items: List<T>,
                            private val itemLayout: Int,
                            private val bindItem: (Int, View, T) -> View,
                            private val onItemClick: (T) -> Unit = { _ -> },
                            private val onItemLongClick: (T) -> Unit = { _ -> }) : RecyclerView.Adapter<MoviesAdapter<T>.BaseViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        holder.bind(position, item, onItemClick, onItemLongClick)
    }

    fun updateItems(list: List<T>) {
        items = list
        notifyDataSetChanged()
    }

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int, item: T, onClickListener: (T) -> Unit, onLongClickListener: (T) -> Unit) {
            val view = bindItem(position, itemView, item)
            view.setOnClickListener {
                onClickListener(item)
            }
            view.setOnLongClickListener{
                onLongClickListener(item)
                true
            }
        }

    }
}