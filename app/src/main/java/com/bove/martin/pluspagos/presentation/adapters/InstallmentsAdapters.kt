package com.bove.martin.pluspagos.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.InstallmentItemBinding
import com.bove.martin.pluspagos.domain.model.PayerCost

class InstallmentsAdapters(private var installmentsList: List<PayerCost>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<InstallmentsAdapters.ViewHolder>() {

    override fun getItemCount(): Int = installmentsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.installment_item,
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.installmentItemBinding.payerCost = installmentsList[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(installmentsList[position], position)
        }
    }

    fun setData(paymentList: List<PayerCost>) {
        this.installmentsList = paymentList
        notifyDataSetChanged()
    }
    @Suppress("UNUSED_PARAMETER")
    inner class ViewHolder(val installmentItemBinding: InstallmentItemBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(installmentItemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(payerCost: PayerCost, posicion: Int)
    }
}
