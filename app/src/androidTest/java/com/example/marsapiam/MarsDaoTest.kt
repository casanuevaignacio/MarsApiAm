package com.example.marsapiam

import Model.Local.MarsDao
import Model.Local.MarsDatabase
import Model.Remote.MarsRealState
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


// ----------------------------------------------------
// Función para poder leer LiveData en tests
// LiveData es asíncrono, entonces esperamos el valor
// ----------------------------------------------------


fun <T> LiveData<T>.getOrAwaitValue():T{

    var data : T? = null
    val latch = CountDownLatch(1)

    val observer = object  : Observer<T> {
        override fun onChanged(value:T){

            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)

        }
    }
    observeForever (observer)
    // Espera máximo 2 segundos
    latch.await(2, TimeUnit.SECONDS)
    return data!!
}

// ----------------------------------------------------
// Test del DAO usando Room en memoria
// ----------------------------------------------------

@RunWith(AndroidJUnit4::class)
class MarsDaoTest {


    // Hace que LiveData funcione de forma inmediata en tests
    @get:Rule

    val instanceExucteRule = InstantTaskExecutorRule()


    private lateinit var  db : MarsDatabase

    private lateinit var dao : MarsDao


    // ----------------------------------------------------
    // Se ejecuta antes de cada test
    // Crea una base de datos temporal en memoria
    // ----------------------------------------------------


    @Before

    fun setUp(){

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MarsDatabase :: class.java
        )
            .allowMainThreadQueries() // solo para test
            .build()

        dao = db.marsDao()
    }

    // ----------------------------------------------------
    // Se ejecuta después de cada test
    // Cerramos la base de datos
    // ----------------------------------------------------

    @After
    fun tearDown(){

        db.close()
    }

    // ----------------------------------------------------
    // Test: insertar un terreno
    // ----------------------------------------------------

    @Test
    fun inserTerrain()= runBlocking {


        val terrain = MarsRealState("1", 44000, "rent", "url")
        dao.insertTerrain(terrain)

        val result = dao.getTerrainById("1").getOrAwaitValue()

        assertThat(result.id, equalTo("1"))
        assertThat(result.type, equalTo("rent"))
    }


    @Test
    fun insertAllTerrains( ) =runBlocking {

        val list = listOf(

            MarsRealState("1", 44000,"rent", "url1") ,
            MarsRealState("2", 54000,"rent", "url2"),
            MarsRealState("3", 74000,"buy", "url3")
        )

        dao.insertAllTerrains(list)

        val result = dao.getAllTerrains().getOrAwaitValue()

        // Debe haber 3 terrenos guardados
        assertThat(result.size, equalTo(3))


    }






}