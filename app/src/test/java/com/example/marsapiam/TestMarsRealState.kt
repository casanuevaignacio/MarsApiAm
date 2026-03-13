package com.example.marsapiam

import Model.Remote.MarsRealState
import androidx.room.ForeignKey

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class TestMarsReakState {

    private lateinit var marsTerrain: MarsRealState


    @Before
    fun setup(){

        // Creamos la instancia para test

        marsTerrain = MarsRealState(

            id= "1",
            price= 50000,
            type = "rent",
            img_src = "url"

        )
    }

    @After
    fun tearDown(){
        // No hay recursos que liberar en este caso

    }


    @Test
    fun testMarsRealStateConstructor(){

        // Verificamos que los valores sean correctos

        assertEquals("1",marsTerrain.id)
        assertEquals(50000,marsTerrain.price)
        assertEquals("rent",marsTerrain.type)
        assertEquals("url",marsTerrain.img_src)



    }


    @Test
    fun testMarsRealStateNotNull(){
        assertNotNull(marsTerrain)
    }

    @Test
    fun testMarsRealStatePricePositive(){

        assertTrue(marsTerrain.price >0)
    }

    @Test
    fun  testMarsRealStateTypeIsRent(){

        assertTrue  (marsTerrain.type == "rent")
    }




    @Test
    fun testDifferenteMarsRealStateObjects(){

        val otherTerrain = MarsRealState(

            id= "2",
            price= 70000,
            type = "buy",
            img_src = "other_url"

        )
        assertNotEquals(marsTerrain.id, otherTerrain.id)
        assertNotEquals(marsTerrain.price, otherTerrain.price)


    }


}