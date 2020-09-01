package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentBankListBinding
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.adapters.BanksAdapters
import org.koin.android.ext.android.inject

// todo contenplar caso de que no tenga banco
class BankListFr : Fragment(), BanksAdapters.OnItemClickListener {
    private val viewModel: MainActivityViewModel by inject()
    private lateinit var binding: FragmentBankListBinding

    private lateinit var banksAdapters: BanksAdapters
    private var banksList: List<CardIssuer> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBankListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.bank_fragment_tittle)

        val cardIssuerId: String? = viewModel.getUserPaymentSelection()?.id
        cardIssuerId?.let { viewModel.getCardIssuers(it) }

        viewModel.getPaimentsMethods()
        banksAdapters = BanksAdapters(banksList, this)
        binding.dataIsloaded = false

        binding.banksRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = banksAdapters
        }

        viewModel.cardIssuers.observe(viewLifecycleOwner, Observer {
            if (it.size > 1) {
                banksList = it
                banksAdapters.setData(banksList)
                binding.dataIsloaded = true
            } else {
                viewModel.setUserCardIssuer(null)
                val navBuilder: NavOptions.Builder = NavOptions.Builder()
                val navOptions: NavOptions = navBuilder.setPopUpTo(R.id.bankListFr, true).build();
                binding.root.findNavController().navigate(R.id.action_bankListFr_to_installmentsListFr, null, navOptions)
            }
        })
    }

    override fun onItemClick(cardIssuer: CardIssuer, posicion: Int) {
        viewModel.setUserCardIssuer(cardIssuer)
        binding.root.findNavController().navigate(R.id.action_bankListFr_to_installmentsListFr)
        Log.i("User", "onItemClick: ${cardIssuer.name}")
    }
}