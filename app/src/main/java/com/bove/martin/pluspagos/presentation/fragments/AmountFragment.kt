package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentAmounBinding
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.utils.Constants
import com.bove.martin.pluspagos.presentation.utils.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AmountFragment : Fragment() {

    private val viewModel: MainActivityViewModel by sharedViewModel()
    private lateinit var binding: FragmentAmounBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmounBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.amount_fragment_tittle)

        binding.buttonPayment.setOnClickListener {
            if(validateAmount()) {
                it.hideKeyboard()
                viewModel.setUserAmount(binding.edAmount.getNumericValue()!!)
                binding.root.findNavController().navigate(R.id.action_amounFragment_to_paymentMethodsFr)
            }
        }

    }

    // Here we can omit the max amount validation and show the empty list message in the next fragment.
    fun validateAmount():Boolean {
        var validationResult = true;

        if (binding.edAmount.text.isNullOrEmpty()) {
            binding.edAmount.error = getString(R.string.amount_empty_validation)
            validationResult = false
        } else if (binding.edAmount.getNumericValue()!! > Constants.MAX_ALLOW_ENTRY) {
            binding.edAmount.error = getString(R.string.amount_max_amount_validation, Constants.MAX_ALLOW_ENTRY.toInt().toString())
            validationResult = false
        }

        return validationResult
    }
}