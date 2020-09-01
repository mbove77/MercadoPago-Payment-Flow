package com.bove.martin.pluspagos.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.domain.model.Payment
import com.bove.martin.pluspagos.presentation.adapters.PaymentsAdapters
import com.bove.martin.pluspagos.presentation.utils.EditTextCurrency
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()

    private lateinit var edAmmoun: EditTextCurrency;
    private var paymentList: List<Payment> = ArrayList<Payment>()

    val ammout = 2500f
    private lateinit var id: String
    private lateinit var issuerId: String


    private lateinit var paymentsAdapters: PaymentsAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupActionBarWithNavController(findNavController(R.id.hostFragment))
       /* edAmmoun = findViewById(R.id.edAmmount)

        edAmmoun.setCurrency("$")
        edAmmoun.setSpacing(true)
        edAmmoun.setDecimals(false)
        edAmmoun.setSeparator(".")

        paymentsRecycler.layoutManager = LinearLayoutManager(this)
        paymentsAdapters = PaymentsAdapters(paymentList)
        paymentsRecycler.adapter = paymentsAdapters

        viewModel.paimentsMethods.observe(this, Observer {
            paymentList = it
            paymentsAdapters.setData(paymentList)

            for (payment in it) {
                Log.w("DATA Payments", "${payment.name} - ${payment.id}")
            }
            //id = it[0].id
            //viewModel.getCardIssuers(id)

        })

        viewModel.cardIssuers.observe(this, Observer {
            for (cardIssuer in it) {
                //Log.w("DATA CardIssuer", "${cardIssuer.name}" )
            }
            // issuerId = it[0].id
            // viewModel.getInstallments(id, ammout, issuerId)
        })

        viewModel.installmentsOptions.observe(this, Observer {
            for (installments in it) {
                //Log.w("DATA Installments", "${installments.issuer.name } " )
                for (cost in installments.payerCosts) {
                    // Log.w("DATA Installments", "${cost.recommendedMessage } " )
                }
            }
        })*/

    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.hostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
}