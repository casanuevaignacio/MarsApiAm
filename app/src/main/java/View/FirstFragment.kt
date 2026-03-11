package View

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

    private val viewModel: MarsViewModel by viewModels()

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

        adapter = MarsAdapter()
        adapter.setOnItemClickListener {
            //guarda el terreno seleccionado
            terrain -> viewModel.selected(terrain)
            //pasamos al segundo fragmento
            Log.d("CLICK","Has seleccionado: ${terrain.id}")


        }


        binding.rvTerrains.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvTerrains.adapter = adapter

        // OBSERVAR ROOM
        viewModel.allTerrains.observe(viewLifecycleOwner) { lista ->
            if (lista != null && lista.isNotEmpty()) {
                // AQUÍ pasas los datos a tu adaptador
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