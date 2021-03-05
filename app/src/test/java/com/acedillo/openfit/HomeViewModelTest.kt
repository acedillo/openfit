package com.acedillo.openfit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.acedillo.openfit.model.Main
import com.acedillo.openfit.repository.Repository
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var repository: Repository

    @Before
    fun setup() {
        repository = Mockito.mock(Repository::class.java)
        Mockito.`when`(repository.getWorkouts())
            .thenReturn(Gson().fromJson(loadFile("main.json"), Main::class.java))
    }

    @Test
    fun testGetMain() {
        setup()
        val viewModel = HomeViewModel(repository, GlobalScope)
        runBlocking {
            val job = viewModel.getMain()
            Assert.assertTrue(viewModel.loading.value!!)
            job.join()
            Assert.assertFalse(viewModel.loading.value!!)
            Assert.assertEquals(20, viewModel.workouts.value?.results?.size)
        }
    }


    //TODO Add more test cases

    private fun loadFile(fileName: String): String {
        val `is` = FileInputStream("src/test/resources/${fileName}")
        val buf = BufferedReader(InputStreamReader(`is`))

        var line = buf.readLine()
        val sb = StringBuilder()

        while (line != null) {
            sb.append(line).append("\n")
            line = buf.readLine()
        }
        return sb.toString()
    }
}