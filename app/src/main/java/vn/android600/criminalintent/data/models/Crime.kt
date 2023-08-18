package vn.android600.criminalintent.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID


@Entity
data class Crime(
        @PrimaryKey val id : UUID = UUID.randomUUID(),
        var title : String = "",
        val date : Date? = Date(),
        var isSolved : Boolean = false
    )
