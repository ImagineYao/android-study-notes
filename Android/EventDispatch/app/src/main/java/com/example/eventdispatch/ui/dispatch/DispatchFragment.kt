package com.example.eventdispatch.ui.dispatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.eventdispatch.R
import com.example.eventdispatch.util.EventUtil
import com.example.eventdispatch.util.LogUtil
import com.example.eventdispatch.widget.MyButton

class DispatchFragment : Fragment() {
    private lateinit var myButton: MyButton
    private lateinit var activityOnTouchEventSwitch: SwitchCompat
    private lateinit var myLayoutOnInterceptEventSwitch: SwitchCompat
    private lateinit var myLayoutOnTouchEventSwitch: SwitchCompat
    private lateinit var myButtonOnTouchSwitch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_dispatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myButton = view.findViewById(R.id.my_button)
        setButtonListener()

        activityOnTouchEventSwitch = view.findViewById(R.id.activity_on_touch_event_switch)
        myLayoutOnInterceptEventSwitch = view.findViewById(R.id.mylayout_on_intercepted_event_switch)
        myLayoutOnTouchEventSwitch = view.findViewById(R.id.mylayout_on_touch_event_switch)
        myButtonOnTouchSwitch = view.findViewById(R.id.mybutton_on_touch_event_switch)
        setSwitchListener()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = resources.getString(R.string.event_dispatch)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setButtonListener() {
        myButton.setOnClickListener {
            LogUtil.i(MyButton.TAG, "onClick", LogUtil.Depth.VIEW)
        }
        myButton.setOnTouchListener { _, ev ->
            val eventName = EventUtil.getActionName(ev)
            LogUtil.i(MyButton.TAG, "onTouch $eventName return ${EventData.myButtonOnTouch}", LogUtil.Depth.VIEW)
            EventData.myButtonOnTouch
        }
    }

    private fun setSwitchListener() {
        activityOnTouchEventSwitch.setOnCheckedChangeListener { _, isChecked ->
            EventData.activityOnTouchEvent = isChecked
            myButton.isClickable = !isChecked
        }
        myLayoutOnInterceptEventSwitch.setOnCheckedChangeListener { _, isChecked ->
            EventData.myLayoutOnInterceptEvent = isChecked
        }
        myLayoutOnTouchEventSwitch.setOnCheckedChangeListener{ _, isChecked ->
            EventData.myLayoutOnInterceptEvent = isChecked
            EventData.myLayoutOnTouchEvent = isChecked
        }
        myButtonOnTouchSwitch.setOnCheckedChangeListener { _, isChecked ->
            EventData.myButtonOnTouch = isChecked
        }
    }
}