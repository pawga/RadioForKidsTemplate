package com.pawga.radio.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pawga.radio.data.db.RadioStationItem
import com.pawga.radio.databinding.MediaListItemBinding

/**
 * Created by sivannikov
 */

class RadioStationsAdapter : PagedListAdapter<RadioStationItem,
        RadioStationsAdapter.RadioItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MediaListItemBinding.inflate(inflater)
        return RadioItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RadioItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RadioStationItem>() {

            override fun areItemsTheSame(oldItem: RadioStationItem, newItem: RadioStationItem): Boolean =
                    oldItem.station?.id == newItem.station?.id

            override fun areContentsTheSame(oldItem: RadioStationItem, newItem: RadioStationItem): Boolean =
                    oldItem.station == newItem.station
        }
    }

    class RadioItemViewHolder(val binding : MediaListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RadioStationItem) {
            binding.radioItem = item
            binding.executePendingBindings()
        }
    }
}


