package vn.android600.criminalintent.data.repositories

import android.content.Context
import androidx.room.Room
import vn.android600.criminalintent.data.CrimeDatabase
import vn.android600.criminalintent.data.models.Crime
import java.util.UUID
import java.util.concurrent.Executors

class CrimeRepository private  constructor(context: Context){

    private val executor = Executors.newSingleThreadExecutor()

    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        "crime_db"
    ).build()

    private val crimeDao = database.crimeDao()

    fun getCrimes() = crimeDao.getCrimes()

    fun getCrimeById(uuid: UUID) = crimeDao.getCrimeById(uuid)

    fun insertCrime(crime: Crime){
        executor.execute {
            crimeDao.insertCrime(crime)
        }
    }

    fun updateCrime(crime: Crime){
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun deleteCrime(crime : Crime){
        executor.execute {
            crimeDao.deleteCrime(crime)
        }
    }

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