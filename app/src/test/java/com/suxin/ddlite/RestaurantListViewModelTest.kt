package com.suxin.ddlite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.suxin.ddlite.model.RestaurantModel
import com.suxin.ddlite.viewmodel.RestaurantListViewModel
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RestaurantListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantListViewModel
    private lateinit var retrofitManager: DoorDashRetrofitManager

    @Mock
    private lateinit var getListObserver: Observer<List<RestaurantModel>>

    private lateinit var mockWebServer:  MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = RestaurantListViewModel()
        viewModel.getData().observeForever(getListObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        retrofitManager = DoorDashRetrofitManager()
    }

    @Test
    fun `fetch list and check response`() {
        // Act
        val  actualResponse = retrofitManager.getDoorDashService()?.getRestaurantListData("37.422740", "-122.139956", "0", "50")
            ?.blockingFirst()
        // Assert
        assertEquals(50, actualResponse?.stores?.size)
    }

    @Test
    fun `test viewModel`() {
        val  actualResponse = retrofitManager.getDoorDashService()?.getRestaurantListData("37.422740", "-122.139956", "0", "50")
            ?.blockingFirst()
        viewModel.restaurantsLiveData.value = actualResponse?.stores

        assertEquals(viewModel.restaurantsLiveData.value?.size, actualResponse?.stores?.size)
    }

    @After
    fun tearDown() {
        viewModel.getData().removeObserver(getListObserver)
        mockWebServer.shutdown()
    }
}