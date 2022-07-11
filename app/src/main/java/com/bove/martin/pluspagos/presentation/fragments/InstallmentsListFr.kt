package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentInstallmentsListBinding
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.InstallmentsAdapters

class InstallmentsListFr : Fragment(), InstallmentsAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentInstallmentsListBinding

    private lateinit var installmentsAdapters: InstallmentsAdapters
    private var payerCostList: List<PayerCost> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstallmentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAmount = viewModel.userAmount.value!!
        val paymentMethodId = viewModel.userPaymentSelection.value!!.id
        val cardIssuerId = viewModel.userBankSelection.value?.id

        if (payerCostList.isEmpty()) {
            // We call one implementation or another depending on whether the cardIssuerId is null or not.
            viewModel.getInstallments(paymentMethodId, userAmount, cardIssuerId)
            binding.dataIsloaded = false
        } else {
            binding.dataIsloaded = true
        }

        installmentsAdapters = InstallmentsAdapters(payerCostList, this)

        binding.installmentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = installmentsAdapters
        }

        viewModel.installmentsOptions.observe(viewLifecycleOwner) {
            binding.dataIsloaded = true
            payerCostList = it[0].payerCosts
            installmentsAdapters.setData(payerCostList)
            if (!(activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).showBottomSheet(
                true
            )
        }
    }

    override fun onItemClick(payerCost: PayerCost, posicion: Int) {
        viewModel.setUserInstallmentSelection(payerCost)
        binding.root.findNavController().navigate(R.id.action_installmentsListFr_to_successFr)
    }
}
