package vn.android600.criminalintent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.data.repositories.CrimeRepository
import java.util.UUID

class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()

    private val crimeMutableLiveData = MutableLiveData<UUID>()

    val crimeLiveData : LiveData<Crime?> = crimeMutableLiveData.switchMap {
        crimeRepository.getCrimeById(it)
    }

    fun loadCrime(uuidStr : String){
        crimeMutableLiveData.value = UUID.fromString(uuidStr)
    }
}