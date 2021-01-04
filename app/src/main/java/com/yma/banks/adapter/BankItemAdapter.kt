package com.yma.banks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yma.banks.R
import com.yma.banks.databinding.ActivityBankItemBinding
import com.yma.banks.model.BanksApiResponse
import com.yma.banks.viewholder.BankItemViewHolder

class BankItemAdapter(
    private val items: List<BanksApiResponse>,
    val banksItemClickListener: (BanksApiResponse) -> Unit
) : RecyclerView.Adapter<BankItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ActivityBankItemBinding.inflate(inflater)

        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = lp

        return BankItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BankItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { banksItemClickListener(items[position]) }
    }
}