package com.example.exoplayertask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class VideoAdapter(
    private val context: Context,
    private val dataList: List<VideosModel>,
    private val videoItemClicked: OnVideoItemClicked
) :
    RecyclerView.Adapter<VideoAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewTitle.text = dataList[position].title
        holder.itemView.setOnClickListener {
            videoItemClicked.onVideoClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
        }
    }

    interface OnVideoItemClicked {
        fun onVideoClicked(position: Int)
    }
}
