package com.sshd.dollarsaver


import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class AccountBalanceFragment : Fragment(){

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.account_balance_layout,null)
        var budget= view.findViewById<EditText>(R.id.allocated_budget)
        var expense= view.findViewById<EditText>(R.id.expenses)
        var balance= view.findViewById<EditText>(R.id.balance_amount)
        var calculate= view.findViewById<Button>(R.id.calculate)
        balance.isEnabled=false
        expense.isEnabled=false
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        val currentUserDb = mDatabaseReference!!.child(mUser!!.uid)
        var Category: MutableList<Map<String,String>>? = null

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("hamza",snapshot.child("budget").value as String)
                var total_amount =0
                var category_list= listOf<String>("food","rent","transport","entertainment","stationery","utilities","miscellaneous")
                var i=0
                while(i<7){
                    try {
                        Category = snapshot.child(category_list.get(i)).value as MutableList<Map<String, String>>
                        Category!!.forEach {
                            total_amount=total_amount+it["Amount"]!!.toInt()
                        }
                    }catch (e: Exception){
                    }
                    i=i+1
                }

                expense.setText(total_amount.toString())
                budget.setText(snapshot.child("budget").value as String)
                balance.setText((budget.text.toString().toInt()-total_amount).toString())

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("hamza","afbkbfajbdf")
            }
        })
        try{
            calculate.setOnClickListener{
                if(budget.text.toString()!="") {
                    balance.setText((budget.text.toString().toInt() - expense.text.toString().toInt()).toString())
                    currentUserDb.child("budget").setValue(budget.text.toString())
                }
            }

        }
        catch(e :java.lang.Exception){

        }
        return view
    }
}