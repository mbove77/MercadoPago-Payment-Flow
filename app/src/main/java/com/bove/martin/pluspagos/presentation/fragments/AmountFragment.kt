package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentAmounBinding
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.utils.hideKeyboard

class AmountFragment : Fragment() {
    private lateinit var binding: FragmentAmounBinding
    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAmounBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ((activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).showBottomSheet(false)

        binding.buttonPayment.setOnClickListener {
            viewModel.validateAmount(binding.edAmount.getNumericValue())
            it.hideKeyboard()
        }

        viewModel.amountIsValid.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.operationResult) {
                    viewModel.setUserAmount(binding.edAmount.getNumericValue()!!)
                    findNavController().navigate(R.id.action_amounFragment_to_paymentMethodsFr)
                } else {
                    binding.edAmount.error = it.resultMensaje?.asString((activity as MainActivity).applicationContext)
                }
            }
        }
    }
}
