package com.android.chandchand

import com.android.chandchand.data.common.Result
import com.android.chandchand.domain.entities.FixtureEntity
import com.android.chandchand.presentation.model.FixturesPerLeagueModel
import com.android.chandchand.presentation.model.LeagueModel
import com.android.chandchand.presentation.utils.WeekDay
import kotlinx.coroutines.flow.flowOf

const val date = "2020-11-11"

val todayWeekDay = WeekDay.Today

val fixtureEntityList = listOf(
    FixtureEntity(
        1,
        3030,
        "Persian Gulf Cup",
        "Iran",
        "",
        "",
        1,
        "",
        "", "",
        1, "",
        "",
        11,
        "",
        "",
        22,
        "",
        "",
        "123",
        "321",
        "",
        "",
        "",
        ""
    )
)

val response = flowOf(
    Result.Success(fixtureEntityList)
)

val fixturesPerLeagueModelList = listOf(
    FixturesPerLeagueModel(
        LeagueModel(999, 999, 999),
        fixtureEntityList
    )
)

