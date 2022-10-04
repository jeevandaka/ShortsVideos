package com.example.newshorts

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.media.session.MediaController
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_row.view.*

class RecyclerVideosAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerVideosAdapter.ViewHolder>() {

    private val items: ArrayList<String> = ArrayList()
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

         val VideoView: VideoView = itemView.videoView
         val progressBar: ProgressBar = itemView.progressBar

        fun setdata(url : String){
            VideoView.setVideoPath(url)

            VideoView.setOnPreparedListener(OnPreparedListener() {
                fun onPrepared(mediaPlayer: MediaPlayer) {
                    progressBar.visibility = View.GONE
                    mediaPlayer.start()
                }
            })
            VideoView.setOnCompletionListener(OnCompletionListener() {
                fun onCompletion(mediaPlayer: MediaPlayer) {
                    mediaPlayer.start()

                }
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.video_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uri : Uri = Uri.parse(items[position])
        holder.VideoView.setVideoURI(uri)
        holder.VideoView.start()

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateVideos(arrayOfVideos: ArrayList<String>) {
        items.clear()
        items.addAll(arrayOfVideos)

        notifyDataSetChanged()
    }


}