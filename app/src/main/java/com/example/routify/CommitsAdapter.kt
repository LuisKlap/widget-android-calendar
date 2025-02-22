package com.example.routify

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CommitsAdapter : ListAdapter<String, CommitsAdapter.CommitViewHolder>(CommitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        Log.d("ROUTIFYLOG.CommitsAdapter.onCreateViewHolder", "Creating view holder")
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return CommitViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        Log.d("ROUTIFYLOG.CommitsAdapter.onBindViewHolder", "Binding view holder at position: $position")
        holder.bind(getItem(position))
    }

    class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(date: String) {
            Log.d("ROUTIFYLOG.CommitsAdapter.CommitViewHolder.bind", "Binding date: $date")
            textView.text = date
        }
    }

    class CommitDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            Log.d("ROUTIFYLOG.CommitsAdapter.CommitDiffCallback.areItemsTheSame", "Comparing items: $oldItem and $newItem")
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            Log.d("ROUTIFYLOG.CommitsAdapter.CommitDiffCallback.areContentsTheSame", "Comparing contents: $oldItem and $newItem")
            return oldItem == newItem
        }
    }
}