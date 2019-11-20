package com.sshd.dollarsaver

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sshd.dollarsaver.Activities.AddExpense
import lecho.lib.hellocharts.view.PieChartView
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData

class DashboardFragment : Fragment(){
    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    var Food: MutableList<Map<String,String>>? = null
    var Rent: MutableList<Map<String,String>>? = null
    var Transport: MutableList<Map<String,String>>? = null
    var Entertainment: MutableList<Map<String,String>>? = null
    var Stationery: MutableList<Map<String,String>>? = null
    var Utilities: MutableList<Map<String,String>>? = null
    var Miscellaneous: MutableList<Map<String,String>>? = null


    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.dashboard_layout,null)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)


        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var food_amount =0
                var rent_amount =0
                var transport_amount =0
                var entertainment_amount =0
                var stationery_amount =0
                var utilities_amount =0
                var miscellaneous_amount =0
                try {
                    Food = snapshot.child("food").value as MutableList<Map<String, String>>
                    Food!!.forEach {
                        food_amount=food_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    food_amount=0
                }

                try {
                    Rent=snapshot.child("rent").value as MutableList<Map<String,String>>
                    Rent!!.forEach {
                        rent_amount=rent_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    rent_amount=0
                }

                try {
                    Transport=snapshot.child("transport").value as MutableList<Map<String,String>>
                    Transport!!.forEach {
                        transport_amount=transport_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    transport_amount=0
                }

                try {
                    Entertainment=snapshot.child("entertainment").value as MutableList<Map<String,String>>
                    Entertainment!!.forEach {
                        entertainment_amount=entertainment_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    entertainment_amount=0
                }

                try {
                    Stationery=snapshot.child("stationery").value as MutableList<Map<String,String>>
                    Stationery!!.forEach {
                        stationery_amount=stationery_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    stationery_amount=0
                }

                try {
                    Utilities=snapshot.child("utilities").value as MutableList<Map<String,String>>
                    Utilities!!.forEach {
                        utilities_amount=utilities_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    utilities_amount=0
                }

                try {
                    Miscellaneous=snapshot.child("miscellaneous").value as MutableList<Map<String,String>>
                    Miscellaneous!!.forEach {
                        miscellaneous_amount=miscellaneous_amount+it["Amount"]!!.toInt()
                    }
                }catch (e: Exception){
                    miscellaneous_amount=0
                }

                val pieChartView = view.findViewById<View>(R.id.chart) as PieChartView
                val  pieData = arrayListOf<SliceValue>()









                var total_amount=food_amount+rent_amount+transport_amount+entertainment_amount+stationery_amount+utilities_amount+miscellaneous_amount
                if(food_amount!=0){
                    pieData.add(SliceValue((food_amount.toFloat()/total_amount.toFloat())*100, Color.BLUE).setLabel("Food: $"+food_amount))
                    }
                if (rent_amount!=0){
                    pieData.add(SliceValue((rent_amount.toFloat()/total_amount.toFloat())*100, Color.RED).setLabel("Rent: $"+rent_amount))
                }
                if(transport_amount!=0){
                    pieData.add(SliceValue((transport_amount.toFloat()/total_amount.toFloat())*100, Color.GRAY).setLabel("Transport: $"+transport_amount))
                }
                if(entertainment_amount!=0){
                    pieData.add(SliceValue((entertainment_amount.toFloat()/total_amount.toFloat())*100, Color.MAGENTA).setLabel("Entertainment: $"+entertainment_amount))
                }
                if(stationery_amount!=0){
                    pieData.add(SliceValue((stationery_amount.toFloat()/total_amount.toFloat())*100, Color.CYAN).setLabel("Stationery: $"+stationery_amount))
                }
                if(utilities_amount!=0){
                    pieData.add(SliceValue((utilities_amount.toFloat()/total_amount.toFloat())*100, Color.GREEN).setLabel("Utilities: $"+utilities_amount))
                }
                if(miscellaneous_amount!=0){
                    pieData.add(SliceValue((miscellaneous_amount.toFloat()/total_amount.toFloat())*100, Color.DKGRAY).setLabel("Miscellaneous: $"+miscellaneous_amount))
                }

                val pieChartData = PieChartData(pieData)
                pieChartData.setHasLabels(true).setValueLabelTextSize(14)
                pieChartView.setPieChartData(pieChartData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("hamza","afbkbfajbdf")
            }
        })

        val add_expense = view.findViewById<View>(R.id.add_expense) as FloatingActionButton


        add_expense.setOnClickListener {
            val intent = Intent(getActivity(), AddExpense::class.java)
            startActivity(intent)
        }



        return view
    }
}