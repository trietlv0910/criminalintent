package vn.android600.criminalintent.ui.crimes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.data.repositories.CrimeRepository
import java.util.*

class CrimeListViewModel : ViewModel(){

    private val crimeRepository = CrimeRepository.get()

    private val crimeListMStateFlow : MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimeListStateFlow : StateFlow<List<Crime>> = crimeListMStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
           crimeRepository.getCrimes()
               .collect{
                    crimeListMStateFlow.value = it
           }
        }
    }


    fun deleteCrime(crime: Crime){
        viewModelScope.launch {
            crimeRepository.deleteCrime(crime)
        }
    }
}