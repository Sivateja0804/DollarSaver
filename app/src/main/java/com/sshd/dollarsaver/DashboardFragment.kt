package com.sshd.dollarsaver

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sshd.dollarsaver.Activities.AddExpense
import lecho.lib.hellocharts.view.PieChartView
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData

class DashboardFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.dashboard_layout,null)
        val pieChartView = view.findViewById<View>(R.id.chart) as PieChartView
        val  pieData = arrayListOf<SliceValue>()
        pieData.add(SliceValue(15f, Color.BLUE).setLabel("Q1: $10"))
        pieData.add(SliceValue(25f, Color.GRAY).setLabel("Q2: $4"))
        pieData.add(SliceValue(10f, Color.RED).setLabel("Q3: $18"))
        pieData.add(SliceValue(60f, Color.MAGENTA).setLabel("Q4: $28"))
        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(true).setValueLabelTextSize(14)
        pieChartView.setPieChartData(pieChartData)
        val add_expense = view.findViewById<View>(R.id.add_expense) as FloatingActionButton
        add_expense.setOnClickListener {
            val intent = Intent(getActivity(), AddExpense::class.java)
            startActivity(intent)
        }



        return view
    }
}