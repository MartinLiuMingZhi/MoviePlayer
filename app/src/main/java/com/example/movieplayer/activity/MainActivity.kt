package com.example.movieplayer.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieplayer.adapter.FilmListAdapter
import com.example.movieplayer.databinding.ActivityMainBinding
import com.example.movieplayer.domain.Film
import com.example.movieplayer.network.NetWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNewMovie:RecyclerView
    private lateinit var recyclerViewUpcoming:RecyclerView
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        sendRequestNow()
        sendRequestUpCom()
    }

    private fun sendRequestNow() {
        runOnUiThread {
            loading1.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = NetWork.nowPlaying("en-US",1)
                runOnUiThread {
                    loading1.visibility = View.GONE
                }

                val filmList = response.results
                val list = ArrayList<Film>()
                for (film in filmList){
                    val adult = film.adult
                    val backdropPath = film.backdrop_path
                    val genreIds = film.genre_ids
                    val id = film.id
                    val originalLanguage = film.original_language
                    val originalTitle = film.original_title
                    val overview = film.overview
                    val popularity = film.popularity
                    val posterPath = film.poster_path
                    val releaseDate = film.release_date
                    val title = film.title
                    val video = film.video
                    val voteAverage = film.vote_average
                    val voteCount = film.vote_count

                    list.add(
                        Film(adult,backdropPath,genreIds,id,originalLanguage,
                        originalTitle,overview,popularity,posterPath,releaseDate,
                        title,video,voteAverage,voteCount)
                    )
                }
                runOnUiThread {
                    binding.recyclerView1.adapter = FilmListAdapter(list)
                }


            }catch (e:Exception){
                runOnUiThread {
                    loading1.visibility = View.GONE
                    Log.e("TAG","Exception: ${e.message}", e)
                }
            }

        }
    }

    private fun sendRequestUpCom() {
        runOnUiThread {
            loading2.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = NetWork.upcoming("en-US",1)
                runOnUiThread {
                    loading2.visibility = View.GONE
                }

                val filmList = response.results
                val list = ArrayList<Film>()
                for (film in filmList){
                    val adult = film.adult
                    val backdropPath = film.backdrop_path
                    val genreIds = film.genre_ids
                    val id = film.id
                    val originalLanguage = film.original_language
                    val originalTitle = film.original_title
                    val overview = film.overview
                    val popularity = film.popularity
                    val posterPath = film.poster_path
                    val releaseDate = film.release_date
                    val title = film.title
                    val video = film.video
                    val voteAverage = film.vote_average
                    val voteCount = film.vote_count

                    list.add(
                        Film(adult,backdropPath,genreIds,id,originalLanguage,
                            originalTitle,overview,popularity,posterPath,releaseDate,
                            title,video,voteAverage,voteCount)
                    )
                }
                runOnUiThread {
                    binding.recyclerView2.adapter = FilmListAdapter(list)
                }


            }catch (e:Exception){
                runOnUiThread {
                    loading2.visibility = View.GONE
                    Log.e("TAG","Exception: ${e.message}", e)
                }
            }

        }
    }

    private fun initView() {
        recyclerViewNewMovie = binding.recyclerView1
        recyclerViewUpcoming = binding.recyclerView2
        loading1 = binding.loading1
        loading2 = binding.loading2
        recyclerViewNewMovie.layoutManager =  LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerViewUpcoming.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        
    }
}