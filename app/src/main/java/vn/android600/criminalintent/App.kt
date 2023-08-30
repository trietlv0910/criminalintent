package vn.android600.criminalintent

import android.app.Application
import vn.android600.criminalintent.data.repositories.CrimeRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}