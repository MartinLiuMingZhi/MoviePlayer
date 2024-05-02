package com.example.movieplayer.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieplayer.R
import com.example.movieplayer.adapter.ImageListAdapter
import com.example.movieplayer.databinding.ActivityDetailBinding
import com.example.movieplayer.domain.Actor
import com.example.movieplayer.domain.Constant
import com.example.movieplayer.domain.Film
import com.example.movieplayer.network.NetWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val idFilm = intent.getIntExtra("id",0)
        Log.d("id",idFilm.toString())
        binding.imagesRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        binding.backImg.setOnClickListener {
            finish()
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.imagesRecyclerView.visibility = View.GONE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val responseDeferred = async { NetWork.movieDetail(idFilm, "en-US") }
                val responseActorDeferred = async { NetWork.movieActors(idFilm, "en-US") }

                val response = responseDeferred.await()
                val responseActor = responseActorDeferred.await()

                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.imagesRecyclerView.visibility = View.GONE
                }

                Glide.with(this@DetailActivity)
                    .load(Constant.IMG_BASE_URL+response.poster_path)
                    .into(binding.posterNormalImg)

                Glide.with(this@DetailActivity)
                    .load(Constant.IMG_BASE_URL+response.backdrop_path)
                    .into(binding.posterBigImg)

                binding.movieNameTxt.text = response.title
                binding.movieTimeTxt.text = response.runtime.toString()
                binding.movieDateTxt.text = response.release_date
                binding.movieSummeryInfo.text = response.overview


                val actors = responseActor.cast
                val list = ArrayList<Actor>()
                Log.d("actors",actors.toString())
                for (actor in actors){
                    val adult = actor.adult
                    val gender = actor.gender
                    val id = actor.id
                    val knownForDepartment = actor.known_for_department
                    val name = actor.name
                    val originalName = actor.original_name
                    val popularity = actor.popularity
                    val profilePath = Constant.IMG_BASE_URL+actor.profile_path
                    val castId = actor.cast_id
                    val character = actor.character
                    val creditId = actor.credit_id
                    val order = actor.order

                    list.add(Actor(adult,castId,character,creditId,gender,
                        id,knownForDepartment,name,order,originalName,popularity,profilePath))
                }

                runOnUiThread {
                    binding.imagesRecyclerView.visibility = View.VISIBLE
                    binding.imagesRecyclerView.adapter = ImageListAdapter(list)
                }

            }catch (e:Exception){
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    Log.e("TAG","Exception: ${e.message}", e)
                }
            }
        }
    }
}