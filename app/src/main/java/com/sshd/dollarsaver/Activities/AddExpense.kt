package com.sshd.dollarsaver.Activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.utilities.Utilities
import com.sshd.dollarsaver.*
import com.sshd.dollarsaver.R
import kotlinx.android.synthetic.main.add_expense.*
import java.nio.channels.NonReadableChannelException
import kotlin.math.E

// why submit button, change visibility?
// rename food to grocery, actual food

//kotlinprojectdemo@gmail.com
//123456
//food, stationery - 3 each

class AddExpense : AppCompatActivity() {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var addExpense: Button? = null
    private var submit: Button? = null
    private var amount: EditText? = null
    private var quantity: EditText? = null
    private var item_name: EditText? = null
    var category_text: String=""
    var market_text: String=""
    var amount_text: String=""

//    var Food: List<Map<String,Any?>>? = null
    var Food: MutableList<Map<String,String>>? = null
    var Rent: MutableList<Map<String,String>>? = null
    var Transport: MutableList<Map<String,String>>? = null
    var Entertainment: MutableList<Map<String,String>>? = null
    var Stationery: MutableList<Map<String,String>>? = null
    var Utilities: MutableList<Map<String,String>>? = null
    var Miscellaneous: MutableList<Map<String,String>>? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expense)
        addExpense=findViewById(R.id.add_expense) as Button
        submit=findViewById(R.id.submit) as Button
        amount=findViewById(R.id.amount) as EditText
        quantity=findViewById(R.id.quantity) as EditText
        item_name=findViewById(R.id.item_name) as EditText

        var list_of_items = arrayOf("food", "rent", "transport", "entertainment", "stationery", "utilities", "miscellaneous")
        var list_of_markets = arrayOf("Costco", "Walmart", "Trader Joes", "WholeFoods", "7Eleven", "CVS")
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
            var flag=true
            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    Toast.makeText(applicationContext,category_text +" -> Select category = " + snapshot.child(category_text).value.toString(), Toast.LENGTH_LONG).show()
                    if (snapshot.child(category_text).value.toString() !=""){
                        if(category_text=="rent"){
                            Rent = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Rent!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="transport"){
                            Transport = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Transport!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="entertainment"){
                            Entertainment = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Entertainment!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="stationery"){
                            Stationery = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Stationery!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="utilities"){
                            Utilities = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Utilities!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="miscellaneous"){
                            Miscellaneous = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Miscellaneous!!.add(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="food"){
                            Food = snapshot.child(category_text).value as MutableList<Map<String,String>>
                            Food!!.add(mapOf("ItemName" to (item_name!!.text.toString()),"Quantity" to (quantity!!.text.toString()),"Amount" to (amount!!.text.toString()),"Market" to market_text))
                        }

                    }
                    else{
                        if(category_text=="rent"){
                            Rent= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="transport"){
                            Transport= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="entertainment"){
                            Entertainment= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="stationery"){
                            Stationery= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="utilities"){
                            Utilities= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="miscellaneous"){
                            Miscellaneous= mutableListOf(mapOf("ItemName" to "","Quantity" to "","Amount" to (amount!!.text.toString()),"Market" to ""))
                        }
                        if(category_text=="food"){
                            Food= mutableListOf(mapOf("ItemName" to (item_name!!.text.toString()),"Quantity" to (quantity!!.text.toString()),"Amount" to (amount!!.text.toString()),"Market" to market_text))
                        }
                    }
                    if(flag){
                        flag=false
                        if (Rent!=null){
                            mUserReference.child("rent").setValue(Rent)
                        }
                        if (Transport != null){
                            mUserReference.child("transport").setValue(Transport)
                        }
                        if (Entertainment != null){
                            mUserReference.child("entertainment").setValue(Entertainment)
                        }
                        if (Stationery != null){
                            mUserReference.child("stationery").setValue(Stationery)
                        }
                        if (Utilities != null){
                            mUserReference.child("utilities").setValue(Utilities)
                        }
                        if (Miscellaneous != null){
                            mUserReference.child("miscellaneous").setValue(Miscellaneous)
                        }
                        if (Food != null){
                            mUserReference.child("food").setValue(Food)
                        }
                    }

                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })

        })


        submit!!.setOnClickListener(View.OnClickListener {

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
                Toast.makeText(applicationContext,"category:"+category_text,Toast.LENGTH_LONG).show()
                Rent = null
                Transport = null
                Entertainment = null
                Utilities = null
                Stationery = null
                Miscellaneous = null
                Food = null

//                Toast.makeText(applicationContext,"category = ", category_text, Toast.LENGTH_LONG).show()
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
                market_text=list_of_markets[position]
                Toast.makeText(applicationContext,"market:"+market_text,Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }
}