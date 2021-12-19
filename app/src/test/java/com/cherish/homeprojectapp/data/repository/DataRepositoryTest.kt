package com.cherish.homeprojectapp.data.repository

import androidx.lifecycle.liveData
import app.cash.turbine.test
import com.cherish.homeprojectapp.data.model.response.OrganizationResponse
import com.cherish.homeprojectapp.data.remote.ApiService
import com.cherish.homeprojectapp.data.remote.ResponseManager

import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import java.io.IOException
import java.lang.Exception

@ExperimentalCoroutinesApi
class DataRepositoryTest{
    private lateinit var repository: DataRepository
    private val testDispatcher = TestCoroutineDispatcher()
    var e: Throwable? = null

    private val apiService = mock<ApiService>()
//    private lateinit var repository: DataRepository
//
    @Before
    fun setUp(){

        repository = DataRepository(apiService, testDispatcher)
    }

    @Test
    fun `test that get organization emits successfully`() = runBlocking {
        val list = mutableListOf<OrganizationResponse>()
        val response = OrganizationResponse("https://avatars.githubusercontent.com/u/3286?v=4", "kjkjjj","nnnmnmnmnm","jkjjhhhhjh", 9,"jhjhhjhjhhhh","kjjjjjhhjhhj", "", "jnjhjhjhjhbbh", "kjhhhjhjhh","kjhkhjhjhjh","bjhbhjgfxghvj")
        list.add(response)

        // Mock API Service
        whenever(apiService.getOrganizations()) doReturn list
//        apiService.stub {
//            onBlocking { getOrganizations() } doReturn list
//        }

        //Test and Verify
        repository.getOrganization().test {
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true)) // emitting on start
            assertThat(awaitItem()).isEqualTo(ResponseManager.Success(list))    // emitting on success
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) // emitting on completion
            cancelAndConsumeRemainingEvents()
        }
    }

//    @Test
//    fun `test that get organization emits error`() = runBlockingTest {
//        val list = mutableListOf<OrganizationResponse>()
//        val response = OrganizationResponse("", "","","", 9,"","kjjjjjhhjhhj", "", "jnjhjhjhjhbbh", "kjhhhjhjhh","kjhkhjhjhjh","bjhbhjgfxghvj")
//        list.add(response)
//      val throwable = Throwable()
//       // whenever(apiService.getOrganizations()) doReturn list
//
//
//        // Mock API Service
//        apiService.stub {
//            onBlocking { getOrganizations() } doAnswer  {
//
//
//            }
//
//        }
//
//
//        //Test and Verify
//          repository.getOrganization().test  {
//
//              assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true))
//
//          //    awaitItem()// emitting on start
//              assertThat(awaitItem()).isEqualTo(ResponseManager.Failure())
//
////            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true)) // emitting on start
////            assertThat(awaitItem()).isEqualTo(ResponseManager.Success(list))    // emitting on success
////            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) // emitting on completion
//           cancelAndConsumeRemainingEvents()
//        }
//
//
//
//
//    }
//
}