package com.bove.martin.pluspagos.presentation.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentInstallmentsListBinding
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.InstallmentsAdapters
import org.koin.android.ext.android.inject


class InstallmentsListFr : Fragment(), InstallmentsAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by inject()
    private lateinit var binding: FragmentInstallmentsListBinding

    private lateinit var installmentsAdapters: InstallmentsAdapters
    private var payerCostList: List<PayerCost> = ArrayList()

    private var cardIssuerId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstallmentsListBinding.inflate(inflater, container, false)
        return binding.root
    }
    //todo manejar la vuelta atras.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.installments_fragment_tittle)

        val userAmount: Float = viewModel.getUserAmount()!!.toFloat()
        val paymentMethodId: String = viewModel.getUserPaymentSelection()!!.id
        cardIssuerId = viewModel.gerUserCardIssuer()?.id

        viewModel.getInstallments(paymentMethodId, userAmount, cardIssuerId)

        installmentsAdapters = InstallmentsAdapters(payerCostList, this)
        binding.dataIsloaded = false

        binding.installmentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = installmentsAdapters
        }

        viewModel.installmentsOptions.observe(viewLifecycleOwner, Observer {
            // TODO Prever comportamiento si viene mas de 1 elemento InstallmentOption.
            payerCostList = it[0].payerCosts
            installmentsAdapters.setData(payerCostList)
            binding.dataIsloaded = true
        })

    }

    override fun onItemClick(payerCost: PayerCost, posicion: Int) {
        Log.i("User", "onItemClick: ${payerCost.recommendedMessage}")
    }

}