package Model

import Model.Local.MarsDao
import Model.Remote.MarsRealState
import Model.Remote.RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MarsRepository(private val marsDao: MarsDao) {

    //llama al metodo conexio


    private val retrofitClient = RetrofitClient.getRetrofit()

    //hace referencia al pojo y la respues q vamos a recibir

    val dataFromInternet = MutableLiveData<List<MarsRealState>>()


    suspend fun fetchDataFromInternetCoroutines(){

        try {
            val response = retrofitClient.fetchMarsDatacCoroutines()

            when(response.code()){

                in 200..299 -> response.body()?.let {

                    if (it!= null){
                        marsDao.insertAllTerrains(it)
                    }
                }

                in 300..301 -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")

                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody().toString()}")


                }

            }catch (t: Throwable){
                Log.e("REPO", "${t.message}")

        }
    }


    fun getMarsByid(id: Int): LiveData<MarsRealState> {
        return getMarsByid(id)
    }


    val listAllTask: LiveData<List<MarsRealState>> = marsDao.getAllTerrains()

    suspend fun inserTerrain(terrain: MarsRealState) {
        marsDao.insertTerrain(terrain)

    }

    suspend fun updateTerrain(terrain: MarsRealState) {
        marsDao.updateTerrains(terrain)

    }

    suspend fun deleteTerrain(terrain: MarsRealState) {
        marsDao.deleteTerrain(terrain)

    }

    suspend fun deleteAll() {
        marsDao.deleteAll()

    }




}

