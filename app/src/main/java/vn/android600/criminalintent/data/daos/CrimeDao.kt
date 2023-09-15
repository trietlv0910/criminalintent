package vn.android600.criminalintent.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import vn.android600.criminalintent.data.models.Crime
import java.util.UUID


@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes() : Flow<List<Crime>>
    @Query("SELECT * FROM crime WHERE id = (:id)")
    suspend fun getCrimeById(id: UUID) : Crime?
    @Insert
    suspend fun insertCrime(crime: Crime)
    @Update
    suspend fun updateCrime(crime: Crime)
    @Delete
    suspend fun deleteCrime(crime: Crime)
}