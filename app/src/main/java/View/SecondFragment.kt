package View

import ViewModel.MarsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.marsapiam.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    // Usamos activityViewModels() para compartir el MISMO ViewModel que el FirstFragment
    private val viewModel: MarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Observar el terreno seleccionado en el ViewModel
        viewModel.selectedItem().observe(viewLifecycleOwner) { terrain ->
            if (terrain != null) {
                // Pintar los datos en el XML
                binding.tvTypeDetail.text = "Tipo: ${terrain.type}"
                binding.tvPriceDetail.text = "Precio: $${terrain.price}"

                // Cargar la imagen con Glide
                Glide.with(this)
                    .load(terrain.img_src)
                    .into(binding.imgDetail)
            }
        }

        // 2. Configurar el botón del "Carrito"
        binding.btnAddToCart.setOnClickListener {
            val terrainType = binding.tvTypeDetail.text
            Toast.makeText(requireContext(), "Añadido al carrito: $terrainType", Toast.LENGTH_SHORT).show()

            // Aquí podrías agregar lógica para guardar en una tabla de "Favoritos" o "Carrito"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}