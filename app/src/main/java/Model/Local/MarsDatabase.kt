package Model.Local

import Model.Remote.MarsRealState
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database( entities = [MarsRealState::class], version = 1)
abstract class MarsDatabase : RoomDatabase(){



    // DAO para acceder a las operaciones de la tabla

    abstract fun marsDao(): MarsDao


    companion object{


        @Volatile
        private var INSTANCE : MarsDatabase? = null


        fun getDataBase( context: Context): MarsDatabase{

            // Si ya existe una instancia, se retorna
            return INSTANCE ?: synchronized(this) {

                // Si no existe, se crea la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarsDatabase::class.java,
                    "mars_database"
                ).build()

                INSTANCE= instance
                instance
            }




        }






    }


}