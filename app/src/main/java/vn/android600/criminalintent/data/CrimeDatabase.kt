package vn.android600.criminalintent.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.android600.criminalintent.data.converters.CrimeTypeConverters
import vn.android600.criminalintent.data.daos.CrimeDao
import vn.android600.criminalintent.data.models.Crime


@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase(){

    abstract fun crimeDao() : CrimeDao
}