package com.example.myedgelighting.service


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.telephony.gsm.SmsMessage
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.ui.activity.dashboard.DashboardActivity
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs
import org.jetbrains.annotations.Nullable


@Suppress("DEPRECATION")
class NotificationService : Service() {
    private var isShowing = false
    private lateinit var edgeOverlayView: EdgeOverlayView

    private var displayMode: Int = 0
    var duration: Long = 4000
    private var customString: String = "*"
    var windowManager: WindowManager? = null
    val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        },
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE,
        PixelFormat.TRANSLUCENT
    )

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {

        @SuppressLint("SuspiciousIndentation")
        override fun onReceive(context: Context?, intent: Intent?) {


            if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                try {
                    // TELEPHONY MANAGER class object to register one listner
                    val tmgr = context!!.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

                    //Create Listner
                    val PhoneListener = MyPhoneStateListener()

                    // Register listener for LISTEN_CALL_STATE
                    tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE)
                } catch (e: Exception) {
                    Log.e("Phone Receive Error", " $e")
                }
                val telephonyManager =
                    context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val callState = telephonyManager.callState
                if (callState == TelephonyManager.CALL_STATE_RINGING) {
                    showCustomLayout()
                }
            }
            if (intent?.action == Intent.ACTION_POWER_CONNECTED || intent?.action == "ACTION_NOTIFICATION_RECEIVED") {
                showCustomLayout()
            } else if (intent?.action == Intent.ACTION_POWER_DISCONNECTED) {
                if (edgeOverlayView.windowToken != null)
                    windowManager?.removeView(edgeOverlayView)
            } else if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") {
                showCustomLayout()
            }
        }


        private fun showCustomLayout() {
            val savedCustomString =
                MySharedPrefs(applicationContext).getStringPref("custom_string")
            val setRadius =
                MySharedPrefs(applicationContext).getFloatPref(CommonKeys.Corner_Radius, 20f)
            val selectedColor = MySharedPrefs(applicationContext).getIntPref(
                CommonKeys.SelectedColor1,
                Color.RED
            )
            val selectedColor1 = MySharedPrefs(applicationContext).getIntPref(
                CommonKeys.SelectedColor2,
                Color.RED
            )
            val selectedColor3 = MySharedPrefs(applicationContext).getIntPref(
                CommonKeys.SelectedColor3,
                Color.RED
            )
            val cornerRadius =
                MySharedPrefs(applicationContext).getFloatPref(CommonKeys.Corner_Radius, 20f)
            val textSize = MySharedPrefs(applicationContext).getFloatPref("text_size", 20f)
            val strokeWidth =
                MySharedPrefs(applicationContext).getFloatPref(CommonKeys.STROKE_WIDTH, 13f)


            // Set the initial progress of the SeekBars based on the saved values
            edgeOverlayView.setCornerRadiusFromSeekBar(cornerRadius.toInt())
            edgeOverlayView.setSymbolSizeFromSeekBar(textSize.toInt())
            edgeOverlayView.setStrokeWidthFromSeekBar(strokeWidth.toInt())
            //edgeOverlayView = EdgeOverlayView(applicationContext, null)

            if (savedCustomString != null) {
                edgeOverlayView.setCustomString(savedCustomString)
            }
            edgeOverlayView.setCornerRadius(cornerRadius)

            /*val duration: Long =
                MySharedPrefs(applicationContext).getLongPref(CommonKeys.ANIM_DURATION)*/
            Handler().postDelayed({
                if (edgeOverlayView.windowToken != null) {
                    windowManager?.removeView(edgeOverlayView)
                }
            }, duration)


            // val edgeOverlayView = inflater.inflate(R.layout.notification_layout, null)

            layoutParams.gravity = Gravity.TOP or Gravity.START or Gravity.END or Gravity.BOTTOM

            layoutParams.x = -2
            layoutParams.y = -2

            // Set the SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION flags
            edgeOverlayView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            windowManager?.addView(edgeOverlayView, layoutParams)


        }
    }


    override fun onCreate() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val callFilter = IntentFilter()
        callFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        val iFilter = IntentFilter()
        iFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        iFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        iFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        iFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        iFilter.addAction("ACTION_NOTIFICATION_RECEIVED")
        registerReceiver(receiver, iFilter)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        createNotificationChannel()
        displayMode = intent.getIntExtra("displayMode", 0)
        customString = intent.getStringExtra("customString") ?: "*"
        edgeOverlayView = EdgeOverlayView(this, null)
        edgeOverlayView.setCustomString(customString)
        edgeOverlayView.switchDisplayMode(displayMode)
        MySharedPrefs(applicationContext).setBoolPref(CommonKeys.SERVICE_KEY, true)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (edgeOverlayView.windowToken != null) {
            windowManager?.removeView(edgeOverlayView)
            windowManager = null
        }
        MySharedPrefs(applicationContext).setBoolPref(CommonKeys.SERVICE_KEY, false)
        unregisterReceiver(receiver)


    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
            val contentIntent = Intent(this, DashboardActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
//
            // Create the notification
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Edge Lighting Service")
                .setContentText("Tap to change settings")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Remove the notification when clicked
            val notification = builder.build()

            // Start the service as a foreground service
            startForeground(1, notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}
