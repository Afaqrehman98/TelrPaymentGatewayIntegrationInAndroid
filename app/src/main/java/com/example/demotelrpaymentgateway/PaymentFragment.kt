package com.example.demotelrpaymentgateway

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.demotelrpaymentgateway.databinding.FragmentPaymentBinding
import com.telr.mobile.sdk.activty.WebviewActivity
import com.telr.mobile.sdk.entity.request.payment.*
import java.math.BigInteger
import java.util.*

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private var amount = "350"
    val KEY = "????"
    val STORE_ID = "????"
    val EMAIL = "???"
    private val isSecurityEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPay.setOnClickListener {
            sendMessage(it)
        }
    }

    fun sendMessage(view: View?) {
        val intent = Intent(requireContext(), WebviewActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        amount = binding.etAmountEntered.toString()
        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest())
        intent.putExtra(
            WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME,
            "com.example.SuccessTransationActivity"
        )
        intent.putExtra(
            WebviewActivity.FAILED_ACTIVTY_CLASS_NAME,
            "com.example.FailedTransationActivity"
        )
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled)
        startActivity(intent)
    }

    private fun getMobileRequest(): MobileRequest {
        val mobile = MobileRequest()
        mobile.store = STORE_ID
        mobile.key = KEY
        val app = App()
        app.id = "123456789"
        app.name = "Telr SDK DEMO"
        app.user = "123456"
        app.version = "0.0.1"
        app.sdk = "123"
        mobile.app = app
        val tran = Tran()
        tran.test = "1"
        tran.type = "auth"
        tran.clazz = "paypage"
        tran.cartid = BigInteger(128, Random()).toString()
        tran.description = "Test Mobile API"
        tran.currency = "AED"
        tran.amount = amount
        tran.langauge = "en"
        mobile.tran = tran
        val billing = Billing()
        val address = Address()
        address.city = "Dubai"
        address.country = "AE"
        address.region = "Dubai"
        address.line1 = "SIT G=Towe"
        billing.address = address
        val name = Name()
        name.first = "Hany"
        name.last = "Sakr"
        name.title = "Mr"
        billing.name = name
        billing.email = EMAIL
        billing.phone = "588519952"
        mobile.billing = billing
        return mobile
    }


}