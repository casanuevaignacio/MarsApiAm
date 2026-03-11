package Model

import Model.Local.MarsDao
import Model.Remote.MarsRealState
import Model.Remote.RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MarsRepository(private val marsDao: MarsDao) {

    private val retrofitClient = RetrofitClient.getRetrofit()

    val dataFromInternet = MutableLiveData<List<MarsRealState>>()

    suspend fun fetchDataFromInternetCoroutines() {

        try {

            val response = retrofitClient.fetchMarsDatacCoroutines()

            when (response.code()) {

                in 200..299 -> response.body()?.let { terrains ->

                    //guardar en Room
                    marsDao.insertAllTerrains(terrains)

                    //enviar datos al LiveData
                    dataFromInternet.postValue(terrains)
                }

                in 300..399 ->
                    Log.d("REPO", "${response.code()} --- ${response.errorBody()}")

                else ->
                    Log.d("REPO", "${response.code()} --- ${response.errorBody()}")

            }

        } catch (t: Throwable) {

            Log.e("REPO", "${t.message}")

        }
    }

    //obtener por id
    fun getMarsByid(id: String): LiveData<MarsRealState> {
        return marsDao.getTerrainById(id)
    }

    //lista desde Room
    val listAllTask: LiveData<List<MarsRealState>> =
        marsDao.getAllTerrains()

    suspend fun inserTerrain(terrain: MarsRealState) {
        marsDao.insertTerrain(terrain)
    }

    suspend fun updateTerrain(terrain: MarsRealState) {
        marsDao.updateTerrains(terrain)
    }

    suspend fun deleteTerrain(terrain: MarsRealState) {
        marsDao.deleteTerrain(terrain)
    }

    suspend fun updateAllTerrains(terrains: List<MarsRealState>) {
        marsDao.insertAllTerrains(terrains)
    }
}