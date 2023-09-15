package vn.android600.criminalintent.ui.detail

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.data.repositories.CrimeRepository
import java.util.UUID

class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

    private val crimeMStateFlow : MutableStateFlow<Crime?> = MutableStateFlow(null)
    val crimeStateFlow : StateFlow<Crime?> = crimeMStateFlow.asStateFlow()

    fun loadCrime(uuidStr : String){
        viewModelScope.launch{
            crimeMStateFlow.value = crimeRepository.getCrimeById(UUID.fromString(uuidStr))
        }
    }

    fun saveCrime(crime: Crime){
        viewModelScope.launch {
            crimeRepository.insertCrime(crime)
        }
    }
    fun updateCrime(crime: Crime){
        viewModelScope.launch {
            crimeRepository.updateCrime(crime)
        }
    }
}