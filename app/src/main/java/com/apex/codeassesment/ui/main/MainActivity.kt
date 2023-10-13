package com.apex.codeassesment.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.ui.details.DetailsActivity
import com.apex.codeassesment.util.snackBar
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
// TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
// TODO (2 points): Convert to view binding
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUser()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.user.collect {
                    if (it.isLoading) {
                        Log.e("MainActivity", "onCreate: $it")
                    }
                    if (it.error.isNotBlank()) {
                        binding.root.snackBar(it.error)
                    }
                    it.data?.let { data ->
                        Glide.with(binding.mainImage.context)
                            .load(data.picture?.medium)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(binding.mainImage)
                        binding.mainName.text = data.name?.first
                        binding.mainEmail.text = data.email
                    }
                }
            }
        }

        binding.mainRefreshButton.setOnClickListener { viewModel.getUser(true) }

//        userListView!!.adapter = arrayAdapter
        /*binding.mainUserList.setOnItemClickListener { parent, _, position, _ ->
            navigateDetails(
                parent.getItemAtPosition(
                    position
                ) as User
            )
        }*/

//        randomUser = userRepository.getSavedUser()
//
//        binding.mainSeeDetailsButton.setOnClickListener { navigateDetails(randomUser) }
//
//        binding.mainRefreshButton.setOnClickListener { randomUser = userRepository.getUser(true) }
//
//        binding.mainUserListButton.setOnClickListener {
//            val users = userRepository.getUsers()
////            arrayAdapter.clear()
////            arrayAdapter.addAll(users)
//        }
    }

    // TODO (2 points): Convert to extenstion function.
    private fun navigateDetails(user: User) {
        val putExtra = Intent(this, DetailsActivity::class.java).putExtra("saved-user-key", user)
        startActivity(putExtra)
    }

}
