package vn.android600.criminalintent.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import vn.android600.criminalintent.data.models.Crime
import java.util.UUID


@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes() : LiveData<List<Crime>>
    @Query("SELECT * FROM crime WHERE id = (:id)")
    fun getCrimeById(id: UUID) : LiveData<Crime?>
    @Insert
    fun insertCrime(crime: Crime)
    @Update
    fun updateCrime(crime: Crime)
    @Delete
    fun deleteCrime(crime: Crime)
}