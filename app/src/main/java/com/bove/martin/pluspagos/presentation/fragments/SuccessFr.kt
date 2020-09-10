package com.bove.martin.pluspagos.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.FragmentSuccessBinding
import com.bove.martin.pluspagos.presentation.MainActivity
import com.bove.martin.pluspagos.presentation.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SuccessFr : Fragment() {
    private val viewModel: MainActivityViewModel by sharedViewModel()
    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.success_fragment_tittle)
        if ((activity as MainActivity).bottomSheetIsVisible) (activity as MainActivity).hideBottomSheet()

        binding.viewModel = viewModel

        binding.buttonConfirm.setOnClickListener {
            viewModel.clearUserSelections()
            binding.root.findNavController().navigate(R.id.action_successFr_to_amounFragment)
        }
    }

}