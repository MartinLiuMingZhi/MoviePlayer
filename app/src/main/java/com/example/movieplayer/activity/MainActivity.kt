package com.example.movieplayer.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieplayer.databinding.ActivityMainBinding
import com.example.movieplayer.network.NetWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

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
        sendRequest()
    }

    private fun sendRequest() {
        loading1.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NetWork.nowPlaying("en-US",1)
                loading1.visibility = View.INVISIBLE
            }catch (e:Exception){
                Log.e("TAG","Exception: ${e.message}", e)
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