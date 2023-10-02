package com.example.myedgelighting.ui.activity.dashboard

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.databinding.ActivityDashboardBinding
import com.example.myedgelighting.service.NotificationService
import com.example.myedgelighting.ui.activity.more.SettingsActivity
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs
import com.example.myedgelighting.viewpager.BorderSelectionPager


@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private lateinit var edgeOverlayView: EdgeOverlayView
    private lateinit var mySharedPrefs: MySharedPrefs
    var serviceIntent: Intent? = null
    var screenWidth = 0
    var screenHeight = 0
    private lateinit var viewSymbol: ViewPager
    var wm: WindowManager? = null
    val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
        PixelFormat.TRANSLUCENT
    )

    private fun handleColorChange(selectedColor: Int) {
        // Make sure edgeOverlayView is properly initialized and accessible here
        edgeOverlayView.updateColorsForSingleColorMode()
        edgeOverlayView.updateGradient(selectedColor, selectedColor, selectedColor, selectedColor)
        edgeOverlayView.startColorAnimation(selectedColor, selectedColor)
        updateView()
    }


    private fun handleColorChange1(selectedColor1: Int, selectedColor2: Int) {
        // Make sure edgeOverlayView is properly initialized and accessible here
        edgeOverlayView.updateColorsForSingleColorMode()
        edgeOverlayView.updateGradient(
            selectedColor1,
            selectedColor2,
            selectedColor1,
            selectedColor2
        )
        edgeOverlayView.startColorAnimation(selectedColor1, selectedColor2)
        updateView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewSymbol = binding.vpBorderSelction
        getScreenSize()
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        edgeOverlayView = EdgeOverlayView(this, null)
        mySharedPrefs = MySharedPrefs(this)

        when (MySharedPrefs(this).getIntPref(CommonKeys.DISPLAY_MODE_KEY)) {
            0 -> {
                binding.borderStyleSwitch.isChecked = false
                setDefaultStyle()

            }

            1 -> {
                binding.borderStyleSwitch.isChecked = true
                setCharacterStyle()
            }
        }

        /* window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
         window.decorView.systemUiVisibility =
             View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY*/



        binding.vpColors.adapter = ViewPagerAdapter(supportFragmentManager)
        viewSymbol.adapter = BorderSelectionPager(supportFragmentManager)
        binding.vpColors.currentItem = mySharedPrefs.getIntPref(CommonKeys.SelectId)
        viewSymbol.currentItem = mySharedPrefs.getIntPref(CommonKeys.SelectSymbol)

        setRadioSelections()
        val savedCornerRadius = mySharedPrefs.getFloatPref(CommonKeys.Corner_Radius, 20f)

        val savedStokeWidth = mySharedPrefs.getFloatPref(CommonKeys.STROKE_WIDTH, 13f)

        val savedSymbolSize = mySharedPrefs.getFloatPref(CommonKeys.SYMBOL_SIZE, 30f)


        val savedAnimationSpeed = mySharedPrefs.getFloatPref(CommonKeys.ANIM_SPEED, 1.0f)



        binding.sbBorderSize.progress = savedSymbolSize.toInt()

        binding.sbAnimationSpeed.progress = savedAnimationSpeed.toInt()


        binding.swEnableService.isChecked = MySharedPrefs(this).getBoolPref(CommonKeys.Service)
        if (binding.swEnableService.isChecked) {
            startService()
        }

        binding.borderStyleSwitch.setOnCheckedChangeListener { _, isChecked ->
            MySharedPrefs(this).setBoolPref("first", isChecked)
            if (isChecked) {
                setCharacterStyle()

            } else {
                setDefaultStyle()

            }
        }


        binding.sbBorderSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                edgeOverlayView.setSymbolSizeFromSeekBar(progress)
                mySharedPrefs.setFloatPref(CommonKeys.SYMBOL_SIZE, progress.toFloat())


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })

        binding.sbNotchWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                edgeOverlayView.setNotchWidth(progress)
                mySharedPrefs.setFloatPref(CommonKeys.NOTCH_WIDTH, progress.toFloat())
                edgeOverlayView.NotchDisplay(2)


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })

        window.navigationBarColor = this.resources.getColor(R.color.bottomnav)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)


        binding.sbSetRadius.progress = savedCornerRadius.toInt()
        edgeOverlayView.setCornerRadiusFromSeekBar(savedCornerRadius.toInt())

        binding.sbStrokeWidth.progress = savedStokeWidth.toInt()
        // edgeOverlayView.setStrokeWidthFromSeekBar(savedStokeWidth.toInt())


        binding.sbBorderSize.progress = savedSymbolSize.toInt()
        // edgeOverlayView.setStrokeWidthFromSeekBar(savedSymbolSize.toInt())


        // Register a LocalBroadcastReceiver to receive color change updates

        registerColorChangeReceiver()
        registerColorChangeReceiver1()


        binding.sbSetRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                edgeOverlayView.setCornerRadiusFromSeekBar(progress)
                mySharedPrefs.setFloatPref(CommonKeys.Corner_Radius, progress.toFloat())
                val min = 5
                if (progress < min) {
                    seekBar!!.progress = min
                }
//                binding.sbSetRadius.max = 100 // 200 maximum value for the Seek bar

                // binding.sbSetRadius.progress = 0 // 50 default progress value
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar starts tracking touch
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })

        binding.sbStrokeWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                edgeOverlayView.setStrokeWidthFromSeekBar(progress)
                mySharedPrefs.setFloatPref(CommonKeys.STROKE_WIDTH, progress.toFloat())
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })


        val savedPosition: Int = MySharedPrefs(this).getIntPref(CommonKeys.ANIM_POSITION)

        binding.ivMore.setOnClickListener {
            val intent = Intent(this@DashboardActivity, SettingsActivity::class.java)
            startActivity(intent)
        }


        binding.swEnableService.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                startService()
            } else {
                stopService()
            }
        }



        binding.radioGroupColorOptions.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                binding.rbSingleColor.id -> {
                    binding.vpColors.currentItem = 0
                }

                binding.rbDoubleColor.id -> {
                    binding.vpColors.currentItem = 1

                }

                binding.rbTripleColor.id -> {
                    binding.vpColors.currentItem = 2
                }
            }
        }

        binding.vpColors.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.rbSingleColor.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectId, 0)
                        MySharedPrefs(this@DashboardActivity).setIntPref(
                            CommonKeys.SelectedColorMode,
                            1
                        )// Save color mode 1
                    }

                    1 -> {
                        binding.rbDoubleColor.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectId, 1)
                        MySharedPrefs(this@DashboardActivity).setIntPref(
                            CommonKeys.SelectedColorMode,
                            2
                        )// Save color mode 2
                    }

                    2 -> {
                        binding.rbTripleColor.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectId, 2)
                        MySharedPrefs(this@DashboardActivity).setIntPref(
                            CommonKeys.SelectedColorMode,
                            3
                        )
                        // Save color mode 3
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        binding.vpBorderSelction.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.rbEmojis.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectSymbol, 0)
                    }

                    1 -> {
                        binding.rbCharacters.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectSymbol, 1)
                    }

                    2 -> {
                        binding.rbAlphabets.isChecked = true
                        mySharedPrefs.setIntPref(CommonKeys.SelectSymbol, 2)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        binding.radioLightingStyle.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbTTB.id -> {
                    MySharedPrefs(this).setIntPref(
                        CommonKeys.SelectedAnimationMode,
                        0
                    ) // Save animtion mode
                }

                binding.rbReverse.id -> {
                    MySharedPrefs(this).setIntPref(
                        CommonKeys.SelectedAnimationMode,
                        1
                    ) // Save animtion mode
                }

                binding.rbHalfBorder.id -> {
                    MySharedPrefs(this).setIntPref(
                        CommonKeys.SelectedAnimationMode,
                        2
                    ) // Save animtion mode
                }

                binding.rbFullBorder.id -> {
                    MySharedPrefs(this).setIntPref(
                        CommonKeys.SelectedAnimationMode,
                        3
                    ) // Save animation mode
                }
            }
        }




        binding.radioGroupBorderStyle.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.rbEmojis.id -> {
                    viewSymbol.currentItem = 0
                    //saveBorderStyle(checkedId, 0)

                }

                binding.rbCharacters.id -> {
                    viewSymbol.currentItem = 1
                    //(checkedId, 1)

                }

                binding.rbAlphabets.id -> {
                    viewSymbol.currentItem = 2
                    // saveBorderStyle(checkedId, 2)

                    Log.d("idid", checkedId.toString())
                }
            }
        }
        binding.ivFullScreen.setOnClickListener {
            //display full screen stroke or symbol depending on mode selection 0 or 1
            edgeOverlayView.NotchDisplay(1)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 1)
            binding.clNotchSettings.visibility = View.GONE
            binding.ivFullScreenSelection.visibility = View.VISIBLE
            binding.ivNotch1Selection.visibility = View.GONE

        }
        binding.ivNotch1.setOnClickListener {
            //display rectangular in  middle rounder notch on between left and right on stroke or symbol depending on mode selection 0 or 1
            edgeOverlayView.NotchDisplay(2)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 2)
            binding.clNotchSettings.visibility = View.VISIBLE
            binding.ivFullScreenSelection.visibility = View.GONE
            binding.ivNotch1Selection.visibility = View.VISIBLE


        }
        binding.ivNotch2.setOnClickListener {
            //display rectangular  rounder notch on left top on stroke or symbol depending on mode selection 0 or 1 in edgeOverlayView
            /*edgeOverlayView.NotchDisplay(3)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 3)*/
            edgeOverlayView.NotchDisplay(4)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 4)

        }
        binding.ivNotch3.setOnClickListener {
            //display rectangular  V shape in middle of top left and top Right on stroke or symbol depending on mode selection 0 or 1 in edgeOverlayView
            edgeOverlayView.NotchDisplay(4)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 4)

        }
        binding.ivNotch4.setOnClickListener {
            //display rectangular  circle shape in middle of top left and top Right on stroke or symbol depending on mode selection 0 or 1 in edgeOverlayView
            edgeOverlayView.NotchDisplay(5)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 5)
        }

        binding.ivNotch5.setOnClickListener {
            //display rectangular  circle shape on top left on stroke or symbol depending on mode selection 0 or 1 in edgeOverlayView
            edgeOverlayView.NotchDisplay(6)
            MySharedPrefs(this).setIntPref(CommonKeys.notchType, 6)

        }

        binding.sbNotchX.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mySharedPrefs.setFloatPref(CommonKeys.NotchX, progress.toFloat())
                edgeOverlayView.setUpdateNotch()

            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })
        binding.sbNotchY.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mySharedPrefs.setFloatPref(CommonKeys.NotchY, progress.toFloat())
                edgeOverlayView.setUpdateNotch()

            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })
        binding.sbNotchRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mySharedPrefs.setFloatPref(CommonKeys.NotchCRadius, progress.toFloat())
                edgeOverlayView.setUpdateNotch()

            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })
        binding.sbNotchWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mySharedPrefs.setFloatPref(CommonKeys.NotchWidth, progress.toFloat())
                edgeOverlayView.setUpdateNotch()
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })
        binding.sbNotchHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                mySharedPrefs.setFloatPref(CommonKeys.NotchHeight, progress.toFloat())
                edgeOverlayView.setUpdateNotch()
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })
        binding.sbAnimationSpeed.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                edgeOverlayView.setAnimationSpeed(progress.toFloat())
                mySharedPrefs.setFloatPref(CommonKeys.ANIM_SPEED, progress.toFloat())
            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Perform any action when the SeekBar stops tracking touch
            }
        })

    }

    private fun getScreenSize() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
        binding.sbNotchY.max = screenHeight
        binding.sbNotchX.max = screenWidth
        binding.sbNotchHeight.max = screenHeight / 3
        binding.sbNotchWidth.max = screenWidth / 2
        binding.sbNotchX.progress = MySharedPrefs(this).getFloatPref(CommonKeys.NotchX, 0f).toInt()
        binding.sbNotchY.progress = MySharedPrefs(this).getFloatPref(CommonKeys.NotchY, 0f).toInt()
        binding.sbNotchWidth.progress =
            MySharedPrefs(this).getFloatPref(CommonKeys.NotchWidth, 100f).toInt()
        binding.sbNotchHeight.progress =
            MySharedPrefs(this).getFloatPref(CommonKeys.NotchHeight, 100f).toInt()
        binding.sbNotchRadius.progress =
            MySharedPrefs(this).getFloatPref(CommonKeys.NotchCRadius, 20f).toInt()
        if (MySharedPrefs(this).getIntPref(CommonKeys.notchType, 1) == 1) {
            binding.clNotchSettings.visibility = View.GONE
            binding.ivFullScreenSelection.visibility = View.VISIBLE
            binding.ivNotch1Selection.visibility = View.GONE
        } else {
            binding.clNotchSettings.visibility = View.VISIBLE
            binding.ivFullScreenSelection.visibility = View.GONE
            binding.ivNotch1Selection.visibility = View.VISIBLE
        }
    }

    private fun setRadioSelections() {
        when (mySharedPrefs.getIntPref(CommonKeys.SelectId)) {
            0 -> {
                binding.rbSingleColor.isChecked = true

            }

            1 -> {
                binding.rbDoubleColor.isChecked = true

            }

            2 -> {
                binding.rbTripleColor.isChecked = true

            }
        }
        when (mySharedPrefs.getIntPref(CommonKeys.SelectSymbol)) {
            0 -> {
                binding.rbEmojis.isChecked = true

            }

            1 -> {
                binding.rbCharacters.isChecked = true

            }

            2 -> {
                binding.rbAlphabets.isChecked = true

            }
        }
    }

    private fun setDefaultStyle() {
        edgeOverlayView.switchDisplayMode(0)
        binding.radioGroupBorderStyle.visibility = View.GONE
        binding.vpBorderSelction.visibility = View.GONE
        MySharedPrefs(this).setIntPref(CommonKeys.DISPLAY_MODE_KEY, 0)
        binding.tvBorderSize.visibility = View.GONE
        binding.sbBorderSize.visibility = View.GONE
        binding.tvStroke.visibility = View.VISIBLE
        binding.sbStrokeWidth.visibility = View.VISIBLE
        binding.clNotchStyle.visibility = View.VISIBLE
    }

    private fun setCharacterStyle() {
        edgeOverlayView.switchDisplayMode(1)
        binding.radioGroupBorderStyle.visibility = View.VISIBLE
        binding.vpBorderSelction.visibility = View.VISIBLE
        MySharedPrefs(this).setIntPref(CommonKeys.DISPLAY_MODE_KEY, 1)
        binding.tvBorderSize.visibility = View.VISIBLE
        binding.sbBorderSize.visibility = View.VISIBLE
        binding.tvStroke.visibility = View.GONE
        binding.sbStrokeWidth.visibility = View.GONE
        binding.clNotchStyle.visibility = View.GONE
    }

    private fun updateView() {
        wm?.updateViewLayout(edgeOverlayView, layoutParams)
    }

    private fun showEdgeView() {
        if (wm == null) {
            wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        layoutParams.gravity = Gravity.TOP or Gravity.START or Gravity.END or Gravity.BOTTOM

        layoutParams.x = -2
        layoutParams.y = -2


        // Set the SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION flags
        edgeOverlayView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        wm?.addView(edgeOverlayView, layoutParams)
    }

    private fun registerColorChangeReceiver() {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "colorChanged") {
                    val selectedColor = intent.getIntExtra("color", Color.RED)
                    handleColorChange(selectedColor)
                }
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver, IntentFilter("colorChanged"))
    }

    private fun registerColorChangeReceiver1() {
        val receiver1 = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "colorChanged1") {
                    val selectedColor1 = intent.getIntExtra("color1", Color.RED)
                    val selectedColor2 = intent.getIntExtra("color2", Color.RED)
                    handleColorChange1(selectedColor1, selectedColor2)
                }
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(receiver1, IntentFilter("colorChanged"))
    }


    private fun saveSelection(selectedId: Int, fragmentPosition: Int) {

        mySharedPrefs.setIntPref(CommonKeys.SelectId, selectedId)
        mySharedPrefs.setIntPref(CommonKeys.SelectFragment, fragmentPosition)
    }

    private fun saveBorderStyle(selectedId: Int, fragmentPosition: Int) {

        mySharedPrefs.setIntPref(CommonKeys.SelectSymbol, selectedId)
        mySharedPrefs.setIntPref(CommonKeys.SelectSymbolStyle, fragmentPosition)
    }

    private fun startService() {

        serviceIntent = Intent(this@DashboardActivity, NotificationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun stopService() {
        stopService(serviceIntent)
    }

    private fun setupBorderViewPager() {
        val setupBorderViewPager = binding.vpBorderSelction
        setupBorderViewPager.adapter = BorderSelectionPager(supportFragmentManager)
    }
//give me complete class code


    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onPause() {
        super.onPause()
        if (edgeOverlayView.windowToken != null) {
            wm?.let {
                it.removeView(edgeOverlayView)
                wm = null
            }
        }
    }

    override fun onResume() {

        super.onResume()
        showEdgeView()
    }

    override fun onDestroy() {
        super.onDestroy()
        wm?.let { it.removeView(edgeOverlayView) }
    }
}
