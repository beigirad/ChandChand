package com.android.chandchand.presentation.fixtures

import androidx.lifecycle.viewModelScope
import com.android.chandchand.*
import com.android.chandchand.domain.usecase.GetFixturesUseCase
import com.android.chandchand.presentation.mapper.FixtureEntityUiMapper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

@ExperimentalCoroutinesApi
class FixturesViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var useCase: GetFixturesUseCase

    @RelaxedMockK
    lateinit var mapper: FixtureEntityUiMapper

    lateinit var viewModel: FixturesViewModel

    val intents = listOf(
        FixturesIntent.GetFixtures(date, todayWeekDay)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = FixturesViewModel(useCase, mapper)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun sendGetFixturesIntentShouldCallGetFixturesUseCase() {
        viewModel.send(FixturesIntent.GetFixtures(date, todayWeekDay))
        coVerify(exactly = 1) { useCase.execute(date) }
    }

    @Test
    fun testStates() = runBlockingTest {
        coEvery { useCase.execute(date) } returns response
        every { mapper.map(fixtureEntityList) } returns fixturesPerLeagueModelList

        val assertions = listOf(
            FixturesState(),
            FixturesState(isLoading = true),
            FixturesState(
                isLoading = false,
                todayFixtures = fixturesPerLeagueModelList
            )
        )
        val states = mutableListOf<FixturesState>()

        val stateCollectionJob = viewModel.viewModelScope.launch {
            viewModel.state.toList(states)
        }

        intents.forEach {
            viewModel.send(it)
        }

        Assert.assertEquals(assertions.size, states.size)
        assertions.zip(states) { assertion, state ->
            Assert.assertEquals(assertion, state)
        }

        stateCollectionJob.cancel()
    }
}