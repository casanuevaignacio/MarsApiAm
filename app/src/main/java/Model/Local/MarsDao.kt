package Model.Local

import Model.Remote.MarsRealState
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerrain(terrain: MarsRealState)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTerrains(terrains: List<MarsRealState>)

    @Update
    suspend fun updateTerrains(terrain: MarsRealState)

    @Delete
    suspend fun deleteTerrain(terrain: MarsRealState)

    @Query("DELETE FROM mars_table")
    suspend fun deleteAll()

    // traer todos los terrenos
    @Query("SELECT * FROM mars_table ORDER BY id DESC")
    fun getAllTerrains(): LiveData<List<MarsRealState>>

    // filtrar por tipo
    @Query("SELECT * FROM mars_table WHERE type = :type LIMIT 1")
    fun getTerrainByType(type: String): LiveData<MarsRealState>

    // filtrar por id (corregido)
    @Query("SELECT * FROM mars_table WHERE id = :id")
    fun getTerrainById(id: String): LiveData<MarsRealState>
}