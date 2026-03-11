package View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marsapiam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // No agregues lógica de NavController aquí por ahora para asegurar que abra
    }
}

