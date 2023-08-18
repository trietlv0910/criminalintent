package vn.android600.criminalintent.data.repositories

import android.content.Context
import androidx.room.Room
import vn.android600.criminalintent.data.CrimeDatabase
import vn.android600.criminalintent.data.models.Crime
import java.util.UUID

class CrimeRepository private  constructor(context: Context){

    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        "crime_db"
    ).build()

    private val crimeDao = database.crimeDao()

    fun getCrimes() : List<Crime> = crimeDao.getCrimes()

    fun getCrimeById(uuid: UUID) = crimeDao.getCrimeById(uuid)

    companion object {
        private var INSTANCE : CrimeRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get() : CrimeRepository =
            INSTANCE?:throw IllegalStateException("CrimeRepository does not exist")
    }
}