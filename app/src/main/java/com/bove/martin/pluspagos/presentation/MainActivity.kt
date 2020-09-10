package com.bove.martin.pluspagos.presentation

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: View
    var bottomSheetIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO add support to dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.mainHostFragment))
        behavior = BottomSheetBehavior.from<View>(binding.bottomSheet)
        bottomSheet = binding.bottomSheet as View
        behavior.peekHeight = 0

        viewModel.userAmount.observe(this, {
            binding.amount = it
        })

        viewModel.userPayMethod.observe(this, {
            binding.paymentMethod = it
        })
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}