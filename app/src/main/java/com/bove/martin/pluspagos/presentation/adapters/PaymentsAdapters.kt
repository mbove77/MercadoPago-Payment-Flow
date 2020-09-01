package com.bove.martin.pluspagos.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.PaymentItemBinding
import com.bove.martin.pluspagos.domain.model.Payment

class PaymentsAdapters(private var paymentList: List<Payment>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PaymentsAdapters.ViewHolder>()  {

    override fun getItemCount(): Int = paymentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.payment_item,
                parent,
                false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.paymentItemBinding.payment = paymentList[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(paymentList[position], position)
        }
    }

    fun setData(paymentList: List<Payment>) {
        this.paymentList = paymentList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val paymentItemBinding: PaymentItemBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(paymentItemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(payment: Payment, posicion: Int)
    }
}

