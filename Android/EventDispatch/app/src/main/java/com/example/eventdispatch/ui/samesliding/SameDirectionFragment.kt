package com.example.eventdispatch.ui.samesliding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.example.eventdispatch.R
import com.example.eventdispatch.widget.InnerInterceptScrollView
import com.example.eventdispatch.widget.OuterInterceptScrollView

class SameDirectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_same_direction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val conflictRadioButton: RadioButton = view.findViewById(R.id.with_conflict_radiobutton)
        val outerRadioButton: RadioButton = view.findViewById(R.id.outer_intercept_radiobutton)
        val innerRadioButton: RadioButton = view.findViewById(R.id.inner_intercept_radiobutton)
        val conflictScrollView: ScrollView = view.findViewById(R.id.conflict_scrollview)
        val outerScrollView: OuterInterceptScrollView = view.findViewById(R.id.outer_scrollview)
        val innerScrollView: InnerInterceptScrollView = view.findViewById(R.id.inner_scrollview)

        conflictRadioButton.isChecked = true
        conflictRadioButton.setOnClickListener {
            conflictScrollView.visibility = View.VISIBLE
            outerScrollView.visibility = View.GONE
            innerScrollView.visibility = View.GONE
        }
        outerRadioButton.setOnClickListener {
            conflictScrollView.visibility = View.GONE
            outerScrollView.visibility = View.VISIBLE
            innerScrollView.visibility = View.GONE
        }
        innerRadioButton.setOnClickListener {
            conflictScrollView.visibility = View.GONE
            outerScrollView.visibility = View.GONE
            innerScrollView.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = resources.getString(R.string.same_sliding)
    }
}