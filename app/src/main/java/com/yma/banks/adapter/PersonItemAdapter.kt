package com.yma.banks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yma.banks.databinding.ActivityPersonItemBinding
import com.yma.banks.model.PersonApiResponse
import com.yma.banks.viewholder.PersonItemViewHolder

class PersonItemAdapter(
    private val items: List<PersonApiResponse>,
    val personItemClickListener: (PersonApiResponse) -> Unit
) : RecyclerView.Adapter<PersonItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActivityPersonItemBinding.inflate(inflater)

        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = lp

        return PersonItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PersonItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { personItemClickListener(items[position]) }
    }
}