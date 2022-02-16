package com.example.monocle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.monocle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.navFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_balance,
                R.id.navigation_transfer,
                R.id.navigation_accounts,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            if(dest.id == R.id.navigation_sign_in)
                bottomNavigationView.visibility = View.GONE
            else
                bottomNavigationView.visibility = View.VISIBLE
        }
    }
}