package com.geeksforgeeks.phone_number_selector_api

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    companion object{
        var CREDENTIAL_PICKER_REQUEST=1
        val permission_reuest = 101
    }

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hojasuru()

    }


    private fun hojasuru() {
        val hintRequest = HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build()

        val credentialsClient = Credentials.getClient(applicationContext)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(
                    intent.intentSender,
                    CREDENTIAL_PICKER_REQUEST,
                    null,
                    0,
                    0,
                    0,
                    Bundle()
            )
            Log.d("prakash", "runs")
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK){

            var credential : Credential?  = data?.getParcelableExtra(Credential.EXTRA_KEY)
            credential?.apply {
                tv1.text = credential.id
            }

        }
    }
}

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                        Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE),permission_reuest)
//        }
//
//        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        tv1.text = telephonyManager.line1Number.toString()

//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == permission_reuest){
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        else {
//            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//
//    }