package com.weemusic.android.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.weemusic.android.R
import java.time.LocalDate

class AlbumsAdapter(val albums: List<Album>) : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.album_card_view, parent, false)

        return AlbumsViewHolder(itemView)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) =
        holder.onBind(albums[position])

    class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(album: Album) {
            val coverUrl = album.artworkUrl100
            val title = album.name
            val artist = album.artistName
            val releaseDate = album.releaseDate

            val albumCover: ImageView = itemView.findViewById(R.id.album_cover)
            val albumTitle: TextView = itemView.findViewById(R.id.album_title)
            val albumArtist: TextView = itemView.findViewById(R.id.album_artist)
            val newReleaseText : TextView = itemView.findViewById(R.id.new_text)

            val today : LocalDate = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            val oneMonthBefore : LocalDate = today.minusWeeks(4)

            if (releaseDate > oneMonthBefore.toString()) {
                newReleaseText.visibility = View.VISIBLE
            } else {
                newReleaseText.visibility = View.GONE
            }

            Picasso.with(itemView.context).load(coverUrl).into(albumCover)
            albumTitle.text = title
            albumArtist.text = artist
        }
    }
}

