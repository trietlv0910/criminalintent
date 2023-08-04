package vn.android600.criminalintent.models

import java.util.Date
import java.util.UUID

data class Crime(
        val id : UUID = UUID.randomUUID(),
        var title : String,
        val date : Date = Date(),
        var isSolved : Boolean = false
    )
