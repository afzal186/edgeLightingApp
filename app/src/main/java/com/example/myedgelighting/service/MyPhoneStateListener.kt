package com.example.myedgelighting.service

import android.telephony.PhoneStateListener
import android.util.Log
import android.widget.Toast

class MyPhoneStateListener : PhoneStateListener() {

    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        Log.d("MyPhoneListener", "$state   incoming no:$incomingNumber")
        if (state == 1) {
            val msg = "New Phone Call Event. Incomming Number : $incomingNumber"
            val duration = Toast.LENGTH_LONG
        }
    }
}
