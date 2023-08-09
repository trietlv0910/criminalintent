package vn.android600.criminalintent.ui.crimes

import androidx.lifecycle.ViewModel
import vn.android600.criminalintent.models.Crime

class CrimeListViewModel : ViewModel(){

    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100){
            val crimeTmp = Crime(title = "Crime #$i")
            crimeTmp.isSolved = i % 2 ==0
            crimes += crimeTmp

        }
    }
}