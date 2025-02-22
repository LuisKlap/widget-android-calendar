package com.example.routify

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.routify.viewmodel.GitHubViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: GitHubViewModel by viewModels()
    private lateinit var adapter: CommitsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ROUTIFYLOG.MainActivity.onCreate", "Activity created")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameInput = findViewById<EditText>(R.id.username_input)
        val searchButton = findViewById<Button>(R.id.search_button)
        val recyclerView = findViewById<RecyclerView>(R.id.commits_recycler_view)

        adapter = CommitsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val username = usernameInput.text.toString()
            Log.d("ROUTIFYLOG.MainActivity.onCreate", "Search button clicked with username: $username")
            if (username.isNotEmpty()) {
                viewModel.fetchReposAndCommits(username, "", "2025-01-01T00:00:00Z", "2025-12-31T23:59:59Z")
            }
        }

        viewModel.commitDates.observe(this, Observer { dates ->
            Log.d("ROUTIFYLOG.MainActivity.onCreate", "Commit dates updated: $dates")
            adapter.submitList(dates)
        })
    }
}