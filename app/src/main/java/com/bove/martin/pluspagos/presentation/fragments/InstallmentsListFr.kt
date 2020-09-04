package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentInstallmentsListBinding
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.InstallmentsAdapters
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstallmentsListFr : Fragment(), InstallmentsAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by viewModel()
    private lateinit var binding: FragmentInstallmentsListBinding

    private lateinit var installmentsAdapters: InstallmentsAdapters
    private var payerCostList: List<PayerCost> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstallmentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.installments_fragment_tittle)

        val userAmount = viewModel.getUserAmount()!!.toFloat()
        val paymentMethodId = viewModel.getUserPaymentSelection()!!.id
        val cardIssuerId = viewModel.getUserCardIssuer()?.id

        // We call one implementation or another depending on whether the cardIssuerId is null or not.
        viewModel.getInstallments(paymentMethodId, userAmount, cardIssuerId)

        installmentsAdapters = InstallmentsAdapters(payerCostList, this)
        binding.dataIsloaded = false

        binding.installmentsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = installmentsAdapters
        }

        viewModel.installmentsOptions.observe(viewLifecycleOwner, {
            // TODO Consider behavior if there is more than 1 Installment Option item.
            payerCostList = it[0].payerCosts
            installmentsAdapters.setData(payerCostList)
            binding.dataIsloaded = true
        })

    }

    override fun onItemClick(payerCost: PayerCost, posicion: Int) {
        viewModel.setUserInstallmentSelection(payerCost)
        binding.root.findNavController().navigate(R.id.action_installmentsListFr_to_successFr)
    }

}