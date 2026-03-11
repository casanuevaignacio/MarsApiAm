package ViewModel

import Model.Local.MarsDatabase
import Model.MarsRepository
import Model.Remote.MarsRealState
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MarsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarsRepository

    // 1. Estas variables deben observar directamente al Repositorio
    val allTerrains: LiveData<List<MarsRealState>>
    val liveDataFromInternet: LiveData<List<MarsRealState>>

    init {
        val marsDao = MarsDatabase.getDataBase(application).marsDao()
        repository = MarsRepository(marsDao)

        // 2. IMPORTANTE: Asignar primero las referencias a los LiveData del Repositorio
        // Usamos listAllTask porque así se llama en tu MarsRepository.kt
        allTerrains = repository.listAllTask
        liveDataFromInternet = repository.dataFromInternet

        // 3. DESPUÉS disparamos la descarga
        viewModelScope.launch {
            repository.fetchDataFromInternetCoroutines()
        }
    }

    // LiveData para guardar el terreno seleccionado
    private var selectedTerrain: MutableLiveData<MarsRealState> = MutableLiveData()

    // Guardar terreno seleccionado
    fun selected(mars: MarsRealState) {
        selectedTerrain.value = mars
    }

    // Obtener terreno seleccionado
    fun selectedItem(): LiveData<MarsRealState> = selectedTerrain

    // Insertar terreno
    fun insertarTerrain(terrain: MarsRealState) = viewModelScope.launch {
        repository.inserTerrain(terrain)
    }

    // Actualizar terreno
    fun updateTerrain(terrain: MarsRealState) = viewModelScope.launch {
        repository.updateTerrain(terrain)
    }

    // Obtener terreno por ID
    fun getTerrainById(id: String): LiveData<MarsRealState> {
        return repository.getMarsByid(id.toString())
    }

}