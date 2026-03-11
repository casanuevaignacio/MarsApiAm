package View

import ViewModel.MarsViewModel
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.marsapiam.R
import com.example.marsapiam.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding




    private lateinit var  viewModel: MarsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MarsViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val firstFragment = FirstFragment()

        if( savedInstanceState == null){
            // 🔹 Agregamos el Fragment al layout usando commitNow() para que sea sincrónico

            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_main,firstFragment)
                .commitNow()

        }

        viewModel.liveDataFromInternet.observe(this) {

                lista ->
            Log.d("Mars_API", "Cantidad de elementos recibidos: ${lista.size}")

            lista.forEach { item ->
                Log.d("MARS_ITEM", item.toString())
            }
        }

    }






}



