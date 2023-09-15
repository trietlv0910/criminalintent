package vn.android600.criminalintent.ui.crimes

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import vn.android600.criminalintent.R
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.ui.MainActivity
import java.util.UUID

class CrimeListFragment : Fragment(), CrimeAdapter.Callback {

    interface Callback{
        fun onAddCrimeItemClick()
        fun onCrimeEdit(uuid: UUID)
    }


    private lateinit var root : View
    private lateinit var crimesRecyclerView: RecyclerView
    private lateinit var adapter: CrimeAdapter

    private val viewModel : CrimeListViewModel by viewModels()

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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.crimeListStateFlow.collect{
                    updateUI(it)
                }
            }
        }
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

    private  fun updateUI(crimes : List<Crime>){
        adapter = CrimeAdapter(crimes)
        adapter.setOnCrimeItemClickListener(this)
        crimesRecyclerView.adapter = adapter
    }

    override fun onCrimeItemClick(uuid: UUID) {
        callback?.onCrimeEdit(uuid)
    }

    override fun onCrimeItemLongClick(crime: Crime) {
        confirmDeleteItemDialog(crime).show()
    }

    private fun getDialogBuilder(crime: Crime) = AlertDialog.Builder(requireContext()).apply {
        this.setTitle("Notification")
        this.setMessage("Are you sure you want to delete this item?")
        this.setPositiveButton("Yes") { dialog, _ ->
            viewModel.deleteCrime(crime)
            dialog.dismiss()
        }
        this.setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }
    }
    private fun confirmDeleteItemDialog(deleteCrime: Crime) = getDialogBuilder(deleteCrime).create()

    companion object{
        fun instance() = CrimeListFragment()
    }
}
