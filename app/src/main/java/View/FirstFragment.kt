package View
import androidx.fragment.app.activityViewModels
import com.example.marsapiam.R
import ViewModel.MarsViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marsapiam.MarsAdapter
import com.example.marsapiam.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // Cambia esto:// private val viewModel: MarsViewModel by viewModels()

    // Por esto (necesitas el import de fragment-ktx):
    // Reemplaza la línea del viewModel por esta:
    private val viewModel: MarsViewModel by activityViewModels()

    private lateinit var adapter: MarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Añadido super

        adapter = MarsAdapter()

        binding.rvTerrains.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTerrains.adapter = adapter

        // --- CONFIGURAR EL CLICK PARA NAVEGAR ---
        adapter.setOnItemClickListener { terrain ->
            // 1. Guardar en el ViewModel compartido
            viewModel.selected(terrain)

            Log.d("CLICK", "Terreno seleccionado: ${terrain.id}")

            // 2. Navegar usando la vista actual de forma segura
            view?.let {
                androidx.navigation.Navigation.findNavController(it).navigate(
                    R.id.action_FirstFragment_to_SecondFragment
                )
            }
        }

        // OBSERVAR DATOS DE ROOM
        viewModel.allTerrains.observe(viewLifecycleOwner) { lista ->
            if (lista != null && lista.isNotEmpty()) {
                adapter.setData(lista)
                Log.d("UI_DEBUG", "La lista contiene ${lista.size} elementos")
            } else {
                Log.d("UI_DEBUG", "La lista está vacía")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}