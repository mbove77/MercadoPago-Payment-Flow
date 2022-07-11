package com.bove.martin.pluspagos.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.BankItemBinding
import com.bove.martin.pluspagos.domain.model.CardIssuer

class BanksAdapters(private var bankList: List<CardIssuer>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<BanksAdapters.ViewHolder>() {

    override fun getItemCount(): Int = bankList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.bank_item,
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bankItemBinding.cardIssuer = bankList[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(bankList[position], position)
        }
    }

    fun setData(bankList: List<CardIssuer>) {
        this.bankList = bankList
        notifyDataSetChanged()
    }

    @Suppress("UNUSED_PARAMETER")
    inner class ViewHolder(val bankItemBinding: BankItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(bankItemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(cardIssuer: CardIssuer, posicion: Int)
    }
}
