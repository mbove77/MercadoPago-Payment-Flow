package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import com.bove.martin.pluspagos.presentation.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_amoun.*


class AmountFragment : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amoun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if ((activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).hideBottomSheet()

        buttonPayment.setOnClickListener {
                viewModel.validateAmount(edAmount.getNumericValue())
                it.hideKeyboard()
        }

        viewModel.amountIsValid.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.operationResult) {
                    viewModel.setUserAmount(edAmount.getNumericValue()!!)
                    findNavController().navigate(R.id.action_amounFragment_to_paymentMethodsFr)
                } else {
                    edAmount.error = it.resultMensaje?.asString((activity as MainActivity).applicationContext)
                }
            }
        }

    }

}