package vn.android600.criminalintent.ui.crimes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.android600.criminalintent.R
import vn.android600.criminalintent.models.Crime

class CrimeAdapter(private val crimes : List<Crime>) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_crime, parent, false)
        return CrimeHolder(itemView)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime)
    }

    override fun getItemCount() = crimes.size

    class CrimeHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val titleTextView : TextView by lazy {
            itemView.findViewById(R.id.title_tv)
        }
        private val dateTextView : TextView by lazy {
            itemView.findViewById(R.id.date_tv)
        }
        private val solvedImageView : ImageView by lazy {
            itemView.findViewById(R.id.imageView)
        }

        fun bind(crime: Crime){
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            solvedImageView.visibility = if (crime.isSolved)  View.VISIBLE else View.INVISIBLE
        }
    }
}