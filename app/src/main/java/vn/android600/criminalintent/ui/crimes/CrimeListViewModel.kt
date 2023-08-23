package vn.android600.criminalintent.ui.crimes

import androidx.lifecycle.ViewModel
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.data.repositories.CrimeRepository
import java.util.*

class CrimeListViewModel : ViewModel(){

    private val crimeRepository = CrimeRepository.get()

    val crimesLiveData = crimeRepository.getCrimes()

}