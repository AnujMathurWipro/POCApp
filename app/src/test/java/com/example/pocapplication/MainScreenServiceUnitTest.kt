package com.example.pocapplication

import com.example.pocapplication.service.MainScreenService
import com.example.pocapplication.models.Response
import com.example.pocapplication.models.RowsItem
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainScreenServiceUnitTest {
    @Test
    fun testGetErrorMessage_NoError() {
        val service = Mockito.spy(MainScreenService::class.java)
        assertEquals("", service.getErrorMessage(true))
    }
    @Test
    fun testGetErrorMessage_Error() {
        val service = Mockito.spy(MainScreenService::class.java)
        assertEquals("There was some problem with the request. Please try again.", service.getErrorMessage(false))
    }

    @Test
    fun testIsSuccessfulResponse_NoError() {
        val service = Mockito.spy(MainScreenService::class.java)
        val response = Mockito.mock(Response::class.java)
        val responseList = ArrayList<RowsItem>()
        responseList.add(RowsItem("", "", ""))
        responseList.add(RowsItem("", "", ""))
        Mockito.`when`(response.rows).thenReturn(responseList)
        assertTrue(service.isSuccessful(response))
    }

    @Test
    fun testIsSuccessfulResponse_Error() {
        val service = Mockito.spy(MainScreenService::class.java)
        val response = Mockito.mock(Response::class.java)
        val responseList: ArrayList<RowsItem>? = null
        Mockito.`when`(response.rows).thenReturn(responseList)
        assertFalse(service.isSuccessful(response))
    }
}
