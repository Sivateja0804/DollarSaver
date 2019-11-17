package com.sshd.dollarsaver.Activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sshd.dollarsaver.*
import com.sshd.dollarsaver.R

class AddExpense : AppCompatActivity() {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var addExpense: Button? = null
    private var submit: Button? = null
    private var amount: EditText? = null
    var category_text: String=""
    var amount_text: String=""

    var Food: List<Map<String,Any?>>? = null
    var Rent: MutableList<Map<String,String>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)
        addExpense=findViewById(R.id.add_expense) as Button
        submit=findViewById(R.id.submit) as Button
        amount=findViewById(R.id.amount) as EditText
        var list_of_items = arrayOf("food", "rent", "transport", "entertainment", "stationery", "utilities", "miscellaneous")
        val spinner = findViewById<Spinner>(R.id.spinner)
        val market_spinner=findViewById<Spinner>(R.id.market_spinner)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        //addexpense listner
        addExpense!!.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(amount!!.text.toString())){
                Toast.makeText(this,"Enter Amount", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (category_text==""){
                Toast.makeText(this,"Select category", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            submit!!.visibility=View.VISIBLE
            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(category_text).value.toString() !="null"){
                        if(category_text=="rent"){
                            Rent = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Rent!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                    }else{
                        Rent= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        })


        submit!!.setOnClickListener(View.OnClickListener {
           if (Rent!=null){
               mUserReference.child("rent").setValue(Rent)
           }
        })

        //category spinner adapter
        ArrayAdapter.createFromResource(this, R.array.category_list, R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        //category spinner lsitner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            category_text=list_of_items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }



        //market_spinner
        ArrayAdapter.createFromResource(this, R.array.market_list, R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            market_spinner.adapter = adapter
        }
        market_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }
}