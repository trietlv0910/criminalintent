package vn.android600.criminalintent.ui.crimes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.android600.criminalintent.R

class CrimeListFragment : Fragment() {

    private lateinit var root : View
    private lateinit var crimesRecyclerView: RecyclerView

    private lateinit var adapter: CrimeAdapter

    private val viewModel : CrimeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_list_crime, container, false)
        initViews()
        updateUI()
        return root
    }
    private fun initViews(){
        crimesRecyclerView = root.findViewById(R.id.crime_recycle_view)
        crimesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
    }

    private  fun updateUI(){
        adapter = CrimeAdapter(viewModel.crimes)
        crimesRecyclerView.adapter = adapter
    }

    companion object{
        fun instance() = CrimeListFragment()
    }
}
