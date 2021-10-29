package com.randstad.animesearchapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.randstad.animesearchapplication.R
import com.randstad.animesearchapplication.adapter.AnimeSearchAdapter
import com.randstad.animesearchapplication.model.AnimeItem
import com.randstad.animesearchapplication.repository.AnimeSearchRepositoryImpl

class AnimeSearchFragment : Fragment() {

    private lateinit var viewModel: AnimeSearchViewModel
    private lateinit var animeSearchText: EditText
    private lateinit var searchButton : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeSearchAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_anime_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
        configureUI(view)
    }

    private fun configureUI(view: View) {
        view.apply {
            animeSearchText = findViewById(R.id.anime_search_editText)
            searchButton = findViewById(R.id.anime_search_button)
            recyclerView = findViewById(R.id.anime_search_recycler)
            progressBar = findViewById(R.id.anime_progressbar)
            recyclerView.layoutManager = LinearLayoutManager(context)
            viewModel.searchByAnimeTerm("naruto")
            adapter = AnimeSearchAdapter(viewModel.animeList.value ?: emptyList())
            searchButton.setOnClickListener {
                viewModel.searchByAnimeTerm(animeSearchText.text.toString())
            }
        }
    }

    /**
     * view model configuration and initialization with repository
     */
    private fun configureViewModel() {
        viewModel = ViewModelProvider(this).get(AnimeSearchViewModel::class.java)
        viewModel.init(AnimeSearchRepositoryImpl())

        viewModel.animeList.observe(viewLifecycleOwner, Observer {
            it?.apply {
                adapter = AnimeSearchAdapter(this)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                false -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        })
    }
}