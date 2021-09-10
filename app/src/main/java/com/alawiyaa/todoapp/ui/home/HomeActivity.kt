package com.alawiyaa.todoapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alawiyaa.todoapp.R
import com.alawiyaa.todoapp.databinding.ActivityHomeBinding
import com.alawiyaa.todoapp.ui.task.add.AddTaskActivity
import com.alawiyaa.todoapp.ui.task.add.AddTaskViewModel
import com.alawiyaa.todoapp.viewmodel.ToDoViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding
    private lateinit var navController: NavController
    private lateinit var homeViewModel: HomeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.botNav?.menu?.getItem(1)?.isEnabled = false

        val factory = ToDoViewModelFactory.getInstance(this)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)

       val unit = homeViewModel.getCountTask()
        setSupportActionBar(binding?.toolBar);
        supportActionBar?.setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        binding?.toolBar?.title = "";

        setupBottomNavigationView()
        binding?.fabAdd?.setOnClickListener(this)
//        binding?.tvTask?.text = "$count Task Today"
        Toast.makeText(this,"Active : $unit",Toast.LENGTH_LONG).show()

    }

    private fun updateUI(account: GoogleSignInAccount?) {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {

            binding?.tvName?.text = "Hallo, ${acct.givenName}!"
            binding?.imgUser?.let { Glide.with(this).load(acct.photoUrl).into(it) }
        }
    }

    private fun setupBottomNavigationView() {
        val navView: BottomNavigationView? = findViewById(R.id.bot_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_task,
            R.id.navigation_settings

        ).build()


        setSupportActionBar(binding?.toolBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.fab_add){
            val addTask = Intent(this@HomeActivity, AddTaskActivity::class.java)
            startActivity(addTask)
        }
    }
}