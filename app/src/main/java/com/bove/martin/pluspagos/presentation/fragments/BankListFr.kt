package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentBankListBinding
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.BanksAdapters


class BankListFr : Fragment(), BanksAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentBankListBinding
    private lateinit var banksAdapters: BanksAdapters
    private var banksList: List<CardIssuer> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBankListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.bank_fragment_tittle)

        val cardIssuerId: String = viewModel.userPaymentSelection.value!!.id

        if (banksList.isEmpty()) {
            viewModel.getCardIssuers(cardIssuerId)
            binding.dataIsloaded = false
        } else {
            binding.dataIsloaded = true
        }

        banksAdapters = BanksAdapters(banksList, this)

        binding.banksRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = banksAdapters
        }

        viewModel.cardIssuers.observe(viewLifecycleOwner) {
            if (it.size > 1) {
                banksList = it
                banksAdapters.setData(banksList)
                binding.dataIsloaded = true
                if (!(activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).showBottomSheet()
            } else {
                viewModel.setUserCardIssuer(null)
                // If the list is empty we go to the next fragment, removing this from the stack to ignore it if user goes back.
                val navBuilder: NavOptions.Builder = NavOptions.Builder()
                val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.bankListFr, true).build()
                binding.root.findNavController()
                    .navigate(R.id.action_bankListFr_to_installmentsListFr, null, navOptions)
            }
        }
    }

    override fun onItemClick(cardIssuer: CardIssuer, posicion: Int) {
        viewModel.setUserCardIssuer(cardIssuer)
        binding.root.findNavController().navigate(R.id.action_bankListFr_to_installmentsListFr)
    }
}