package vn.android600.criminalintent.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import vn.android600.criminalintent.R
import vn.android600.criminalintent.models.Crime

class CrimeFragment : Fragment() {

    private lateinit var root : View
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox : CheckBox
    private lateinit var titleEditText: EditText
    private lateinit var crime:Crime

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
        updateUI()
        initUIEventHandlers()
        return root
    }

    private fun updateUI(){
        dateButton.text = crime.date.toString()
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
        fun instance() = CrimeFragment()
    }
}