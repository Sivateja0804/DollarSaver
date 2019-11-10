package com.sshd.dollarsaver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sshd.dollarsaver.Activities.AddExpense


class DashboardFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.dashboard_layout,null)
        val add_expense = view.findViewById<View>(R.id.add_expense) as FloatingActionButton
        add_expense.setOnClickListener {
            val intent = Intent(getActivity(), AddExpense::class.java)
            startActivity(intent)
        }



        return view
    }
}