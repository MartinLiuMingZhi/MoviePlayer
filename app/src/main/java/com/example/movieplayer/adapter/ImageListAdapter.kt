package com.example.movieplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieplayer.databinding.ViewholderDetailImagesBinding
import com.example.movieplayer.domain.Actor
import com.example.movieplayer.domain.Constant

class ImageListAdapter(private val listActor:List<Actor>):RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:ViewholderDetailImagesBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ViewholderDetailImagesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listActor.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = listActor[position]
        Glide.with(holder.itemView.context)
            .load(actor.profile_path)
            .into(holder.binding.itemImages)
        holder.binding.actorName.text = actor.name
        holder.binding.charater.text = actor.character
    }
}