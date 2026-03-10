package Model.Remote

import retrofit2.Response
import retrofit2.http.GET


interface MarsApi {


    @GET("realstate")
    suspend fun fetchMarsDatacCoroutines(): Response<List<MarsRealState>>








}