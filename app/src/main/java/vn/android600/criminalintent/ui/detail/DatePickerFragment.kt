package vn.android600.criminalintent.ui.detail

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class DatePickerFragment : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inputDate = arguments?.getLong(DATE_INPUT_KEY)
        val calendar = Calendar.getInstance()
        calendar.time = if (inputDate == null) Date() else Date(inputDate)
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val date = GregorianCalendar(year, month, dayOfMonth).time
            setFragmentResult(REQUEST_KEY_DATE, bundleOf(RESULT_DATA_KEY to date.time))
        }

        return DatePickerDialog(
            requireContext(),
            listener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
    companion object{
        private const val DATE_INPUT_KEY = "date"
        const val REQUEST_KEY_DATE = "date request key"
        const val RESULT_DATA_KEY = "result"
        fun newInstance(date : Date) : DatePickerFragment{
            val args = Bundle().apply {
                putLong(DATE_INPUT_KEY, date.time)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}