package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentPaymentMethodsBinding
import com.bove.martin.pluspagos.domain.model.Payment
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.PaymentsAdapters
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PaymentMethodsFr : Fragment(), PaymentsAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by sharedViewModel()
    private lateinit var binding: FragmentPaymentMethodsBinding
    private lateinit var paymentsAdapters: PaymentsAdapters
    private var paymentList: List<Payment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentMethodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.payment_fragment_tittle)

        // In this way we avoid recreating the list when the user goes back,
        // We could also implement a swipe to refresh to update the list.
        if(paymentList.isEmpty()) {
            viewModel.getPaymentsMethods()
            binding.dataIsloaded = false
        }

        paymentsAdapters = PaymentsAdapters(paymentList, this)

        binding.paymentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = paymentsAdapters
        }

        viewModel.paymentsMethods.observe(viewLifecycleOwner, {
            paymentList = it
            paymentsAdapters.setData(paymentList)
            binding.dataIsloaded = true
        })
    }

    override fun onItemClick(payment: Payment, posicion: Int) {
        viewModel.setUserPaymentSelection(payment)
        binding.root.findNavController().navigate(R.id.action_paymentMethodsFr_to_bankListFr)
    }

}