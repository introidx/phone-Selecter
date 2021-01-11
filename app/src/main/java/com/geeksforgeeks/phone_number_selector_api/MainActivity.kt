package com.geeksforgeeks.phone_number_selector_api

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.credentials.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var open_btn : Button
    lateinit var tv1 : TextView

    companion object{
        var CREDENTIAL_PICKER_REQUEST=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        open_btn = findViewById(R.id.btn_open)
        tv1 = findViewById(R.id.tv1)

        // set on click listener to button to open the phone selector dialog

        open_btn.setOnClickListener {
            phoneSelection()
        }
    }
    private fun phoneSelection() {
        //To retrieve the Phone Number hints, first, configure the hint selector dialog by creating a HintRequest object.

        val hintRequest = HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build()

        val options = CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build()

        //Then, pass the HintRequest object to credentialsClient.getHintPickerIntent()
        // to get an intent to prompt the user to choose a phone number.
        val credentialsClient = Credentials.getClient(applicationContext , options)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(
                    intent.intentSender,
                    CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, Bundle()
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK){
            // get data from the dialog which is of type Credential
            val credential : Credential?  = data?.getParcelableExtra(Credential.EXTRA_KEY)
            // set the received data t the text view
            credential?.apply {
                tv1.text = credential.id
            }
        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }
}
