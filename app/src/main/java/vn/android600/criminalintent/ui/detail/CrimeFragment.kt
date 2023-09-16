package vn.android600.criminalintent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import vn.android600.criminalintent.R
import vn.android600.criminalintent.data.models.Crime
import vn.android600.criminalintent.ui.crimes.CrimeListViewModel
import java.util.Date
import java.util.UUID

class CrimeFragment : Fragment() {

    private val selectSuspect = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ){
        it?.let {
            parseContactSelection(it)
        }
    }

    private lateinit var root : View
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox : CheckBox
    private lateinit var titleEditText: EditText
    private lateinit var suspectButton: Button
    private lateinit var reportButton: Button


    private lateinit var crime: Crime


    private var isUpdateMode = true


    private val viewModel : CrimeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.crime = Crime()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_crime, container, false)
        initViews()
        initUIEventHandlers()
        updateUI()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val crimeId = arguments?.getString(KEY)
        isUpdateMode =
            if (crimeId != null){
                viewModel.loadCrime(crimeId)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        viewModel.crimeStateFlow.collect{
                            it?.let {
                                this@CrimeFragment.crime = it
                                updateUI()
                            }
                        }
                    }
                }
                true
            }else
                false

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE){requestKey, bundle ->
            val time = bundle.getLong(DatePickerFragment.RESULT_DATA_KEY)
            val date = Date(time)
            this.crime.date = date
            viewModel.updateCrime(this.crime)
        }

    }

    private fun updateUI(){
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

        dateButton.setOnClickListener {
            this.crime.date?.let {
                DatePickerFragment.newInstance(it).show(
                    parentFragmentManager,
                    null
                )
            }
        }

        reportButton.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, this@CrimeFragment.crime.title)
                putExtra(Intent.EXTRA_SUBJECT, "PH LOC")
            }.also {
                val chooserIntent = Intent.createChooser(it, getString(R.string.send_crime_report))
                startActivity(chooserIntent)
            }
        }
        suspectButton.setOnClickListener {
            selectSuspect.launch(null)
        }
    }

    override fun onStop() {
        super.onStop()
        refreshCrime()
        if (!isUpdateMode){
            if(this.crime.title.isEmpty()) return
            viewModel.saveCrime(this.crime)
        }
        else{
            viewModel.updateCrime(this.crime)
        }
    }

    private fun refreshCrime() = this.crime.apply {
        this.title = titleEditText.text.toString()
        this.isSolved = solvedCheckBox.isChecked
    }

    private fun initViews() {
        dateButton = root.findViewById(R.id.date_btn)
        solvedCheckBox = root.findViewById(R.id.solved_cb)
        titleEditText = root.findViewById(R.id.crime_title_edt)
        suspectButton = root.findViewById(R.id.suspect_btn)
        reportButton = root.findViewById(R.id.report_btn)
    }

    @SuppressLint("Recycle")
    private fun parseContactSelection(contactUri : Uri){
        val queryFields = arrayOf(Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DISPLAY_NAME)

        // create cursorloader
        val queryCursor = requireActivity()
            .contentResolver
            .query(contactUri, queryFields, null, null, null)

        queryCursor?.use {
            if (it.moveToFirst()){
                val suspect = it.getString(1)
                suspectButton.text = suspect
            }
        }
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