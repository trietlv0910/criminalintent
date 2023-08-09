package vn.android600.criminalintent.ui.crimes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.android600.criminalintent.R
import vn.android600.criminalintent.ui.MainActivity
import java.util.UUID

class CrimeListFragment : Fragment() {

    interface Callback{
        fun onAddCrimeItemClick()
        fun onCrimeEdit(uuid: UUID)
    }


    private lateinit var root : View
    private lateinit var crimesRecyclerView: RecyclerView
    private lateinit var adapter: CrimeAdapter

    private val viewModel : CrimeListViewModel by activityViewModels()

    private var callback : Callback? =  null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }
    private fun setupMenu(){
        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.crime_list_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.add_crime_menu_item){
                    callback?.onAddCrimeItemClick()
                    true
                }else false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun initViews(){
        crimesRecyclerView = root.findViewById(R.id.crime_recycle_view)
        crimesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
    }

    private  fun updateUI(){
        adapter = CrimeAdapter(viewModel.crimes)
        adapter.setOnCrimeItemClickListener{
           callback?.onCrimeEdit(it)
        }
        crimesRecyclerView.adapter = adapter
    }

    companion object{
        fun instance() = CrimeListFragment()
    }
}
