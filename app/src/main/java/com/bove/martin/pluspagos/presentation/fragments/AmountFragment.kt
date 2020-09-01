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
import com.bove.martin.pluspagos.presentation.utils.hideKeyboard
import org.koin.android.ext.android.inject

class AmountFragment : Fragment() {

    private val viewModel: MainActivityViewModel by inject()
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
            } else {
                binding.edAmount.error = getString(R.string.amount_validation)
            }
        }
    }

    fun validateAmount():Boolean {
        return !binding.edAmount.text.isNullOrEmpty()
    }
}