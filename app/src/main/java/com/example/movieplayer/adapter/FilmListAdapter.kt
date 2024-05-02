package com.example.movieplayer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieplayer.activity.DetailActivity
import com.example.movieplayer.databinding.ViewholderFilmBinding
import com.example.movieplayer.domain.Constant
import com.example.movieplayer.domain.Film
import com.example.movieplayer.domain.Result

class FilmListAdapter(private var filmList:List<Film>):RecyclerView.Adapter<FilmListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding:ViewholderFilmBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderFilmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = filmList[position]
        holder.binding.titleTxt.text = film.title
        holder.binding.scoreTxt.text = film.vote_average.toString()
        Glide.with(holder.itemView.context)
            .load(Constant.IMG_BASE_URL+film.poster_path)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra("id",film.id)
            holder.itemView.context.startActivity(intent)
        }
    }
}