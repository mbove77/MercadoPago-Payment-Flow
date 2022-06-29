package com.bove.martin.pluspagos.presentation

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.ActivityMainBinding
import com.bove.martin.pluspagos.presentation.utils.UiText
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View
    var bottomSheetIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initObservables()
    }

    private fun initObservables() {
        viewModel.userAmount.observe(this) {
            binding.amount = it
        }

        viewModel.userPaymentSelection.observe(this) {
            binding.paymentMethod = it
        }

        viewModel.operationsError.observe(this) {
            displayErrors(it)
        }
    }

    private fun initUi() {
        //TODO add support to dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupActionBarWithNavController(findNavController(R.id.mainHostFragment))
        behavior = BottomSheetBehavior.from<View>(binding.bottomSheet)
        bottomSheet = binding.bottomSheet
        behavior.peekHeight = 0
    }

    fun showBottomSheet() {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90f, resources.displayMetrics).roundToInt()
        behavior.peekHeight = px
        val animate = TranslateAnimation(0f, 0f, bottomSheet.height.toFloat(), 0f)
        animate.duration = 500
        animate.fillAfter = true
        animate.startOffset = 200
        bottomSheet.startAnimation(animate)
        bottomSheetIsVisible = true
    }

    fun hideBottomSheet() {
        val animate = TranslateAnimation(0f, 0f, 0f, bottomSheet.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        animate.startOffset = 200
        bottomSheet.startAnimation(animate)
        bottomSheetIsVisible = false
    }

    fun displayErrors(text: UiText) {
        Toast.makeText(this, text.asString(this), Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}