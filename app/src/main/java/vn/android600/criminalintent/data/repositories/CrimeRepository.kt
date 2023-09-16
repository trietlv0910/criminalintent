package vn.android600.criminalintent.data.repositories

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import vn.android600.criminalintent.data.CrimeDatabase
import vn.android600.criminalintent.data.migration_1_2
import vn.android600.criminalintent.data.models.Crime
import java.util.UUID
import java.util.concurrent.Executors

class CrimeRepository private  constructor(context: Context){

    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        "crime_db"
    )
        .addMigrations(migration_1_2)
        .build()

    private val crimeDao = database.crimeDao()

    fun getCrimes() : Flow<List<Crime>> = crimeDao.getCrimes()

    suspend fun getCrimeById(uuid: UUID) = crimeDao.getCrimeById(uuid)

    suspend fun insertCrime(crime: Crime){
        crimeDao.insertCrime(crime)
    }

    suspend fun updateCrime(crime: Crime){
        crimeDao.updateCrime(crime)
    }

    suspend fun deleteCrime(crime : Crime){
        crimeDao.deleteCrime(crime)
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