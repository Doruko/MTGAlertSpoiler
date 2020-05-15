package me.alejandro.mtgspoileralert.ui.setList

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.alejandro.mtgspoileralert.CoroutinesTestRule
import me.alejandro.mtgspoileralert.data.usecases.GetSetsUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class SetListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    var getSetsUseCase: GetSetsUseCase = mockk()

    private lateinit var viewModel: SetListAndroidViewModel

    @Before
    fun setup() {
        viewModel = SetListAndroidViewModel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onRetrieveSetListStart() = coroutinesTestRule.testDispatcher.runBlockingTest {
        //GIVEN
        every { getSetsUseCase.invoke(any(), any()) } returns mockk()

        //WHEN
        viewModel.loadSets()

        //THEN
        assert(viewModel.loadingVisibility.value == View.VISIBLE)
        assert(viewModel.errorMessage.value == null)
    }
}