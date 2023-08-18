package vn.android600.criminalintent.ui.crimes

import androidx.lifecycle.ViewModel
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.data.repositories.CrimeRepository
import java.util.*

class CrimeListViewModel : ViewModel(){

    private val crimeRepository = CrimeRepository.get()

    val crimes = mutableListOf<Crime>()

    init {
        Thread(Runnable {
            crimes.addAll(crimeRepository.getCrimes())
        }).start()
    }

    fun findCrimeById(id : String) = crimes.find {
        it.id == UUID.fromString(id)
    }
}