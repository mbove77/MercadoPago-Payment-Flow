package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentSuccessBinding
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel

class SuccessFr : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if ((activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).showBottomSheet(false)

        binding.viewModel = viewModel

        binding.buttonConfirm.setOnClickListener {
            viewModel.clearUserSelections()
            binding.root.findNavController().navigate(R.id.action_successFr_to_amounFragment)
        }
    }
}
