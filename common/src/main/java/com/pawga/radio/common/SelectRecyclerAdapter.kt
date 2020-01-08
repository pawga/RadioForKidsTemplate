package com.pawga.radio.common

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by sivannikov
 */


interface ISelect {
    var select: Boolean
}

interface IBinding<T> {
    val root: View
    var item: T
    fun getSignVisibilityView(): View // if select -> visible
    fun executePendingBindings()
}

open class SelectViewModel<T> : ViewModel() {
    open val list = ArrayList<T>()
}

abstract class SelectRecyclerAdapter<T: ISelect, V: IBinding<T>>(viewModel: SelectViewModel<T>) :
        RecyclerView.Adapter<SelectRecyclerAdapter.SelectViewHolder<T, V>>() {

    abstract fun from(parent: ViewGroup): SelectViewHolder<T, V>

    open val list = viewModel.list

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder<T, V> {
        return from(parent)
    }

    override fun onBindViewHolder(holder: SelectViewHolder<T, V>, position: Int) {
        val item = list[position]
        holder.bind(item)

        // Optimization trick
        holder.binding.root.setOnClickListener {
            item.select = !item.select
            holder.binding.getSignVisibilityView().visibility =
                    if (item.select) View.VISIBLE else View.INVISIBLE
        }
    }

    abstract class SelectViewHolder<T, V: IBinding<T>>(open val binding: V) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: T) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}
