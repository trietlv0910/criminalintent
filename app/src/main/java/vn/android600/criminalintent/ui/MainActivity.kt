package vn.android600.criminalintent.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import vn.android600.criminalintent.R
import vn.android600.criminalintent.ui.crimes.CrimeListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadDefaultFragment()
    }

    private fun loadDefaultFragment(){
        val fragment = CrimeListFragment.instance()
        loadFragment(fragment)
    }

    private fun loadFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}