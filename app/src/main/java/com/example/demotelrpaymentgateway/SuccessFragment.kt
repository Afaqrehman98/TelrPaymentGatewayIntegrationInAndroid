package com.example.demotelrpaymentgateway

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.demotelrpaymentgateway.databinding.FragmentSuccessBinding
import com.telr.mobile.sdk.activty.WebviewActivity
import com.telr.mobile.sdk.entity.response.status.StatusResponse

class SuccessFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), WebviewActivity::class.java)
        val status =
            intent.getParcelableExtra<Parcelable>(WebviewActivity.PAYMENT_RESPONSE) as StatusResponse?
        binding.tvSuccess.text = binding.tvSuccess.text.toString() + " : " + status!!.trace

        if (status.auth != null) {
            status.auth.status // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.

            status.auth.avs /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
            status.auth.code // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.

            status.auth.message // The authorisation or processing error message.

            status.auth.ca_valid
            status.auth.cardcode // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.

            status.auth.cardlast4 // The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.

            status.auth.cvv /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
            status.auth.tranref //The payment gateway transaction reference allocated to this request.

            Log.d("hany", status.auth.tranref)
            status.auth.cardfirst6 // The first 6 digits of the card number used in the transaction, only for version 2 is submitted in Tran -> Version


            setTransactionDetails(status.auth.tranref, status.auth.cardlast4)


        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSuccess.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setTransactionDetails(ref: String, last4: String) {
        val sharedPref: SharedPreferences =
            requireContext().getSharedPreferences("telr", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("ref", ref)
        editor.putString("last4", last4)
        editor.apply()
    }
}

