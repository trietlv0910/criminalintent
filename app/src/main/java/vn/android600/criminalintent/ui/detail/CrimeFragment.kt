package vn.android600.criminalintent.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import vn.android600.criminalintent.R
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.ui.crimes.CrimeListViewModel
import java.util.UUID

class CrimeFragment : Fragment() {

    private lateinit var root : View
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox : CheckBox
    private lateinit var titleEditText: EditText
    private lateinit var crime: Crime

    private val viewModel : CrimeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_crime, container, false)
        initViews()
        initUIEventHandlers()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crimeId = arguments?.getString(KEY)
        if (crimeId != null){
            viewModel.loadCrime(crimeId)
        }

        viewModel.crimeLiveData.observe(viewLifecycleOwner){
            //callback
            updateUI(it)
        }

    }

    private fun updateUI(crime : Crime?){
        if (crime == null) return
        dateButton.text = crime.date.toString()
        titleEditText.setText(crime.title)
        solvedCheckBox.isChecked = crime.isSolved
    }

    private fun initUIEventHandlers(){
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        titleEditText.addTextChangedListener(textWatcher)
    }

    private fun initViews() {
        dateButton = root.findViewById(R.id.date_btn)
        solvedCheckBox = root.findViewById(R.id.solved_cb)
        titleEditText = root.findViewById(R.id.crime_title_edt)
    }

    companion object{
        private const val KEY = "ARG"
        fun instance() = CrimeFragment()
        fun instance(uuid: UUID) : CrimeFragment {
            val fragment = CrimeFragment()
            val bundle = Bundle()
            bundle.putString(KEY, uuid.toString())
            fragment.arguments = bundle
            return fragment
        }
    }
}