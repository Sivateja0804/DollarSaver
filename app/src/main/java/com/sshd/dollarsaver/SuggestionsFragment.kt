package com.sshd.dollarsaver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SuggestionsFragment : Fragment(){
    var market_text: String=""
    var category_text: String=""

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    var category_obj: MutableList<Map<String,String>>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.suggestions_page,null)
        var list_of_items = arrayOf("food", "rent", "transport", "entertainment", "stationery", "utilities", "miscellaneous")
        var market_list= hashMapOf<String,Int>("Costco" to 0, "Walmart" to 0, "Trader Joes" to 0, "WholeFoods" to 0, "7Eleven" to 0, "CVS" to 0)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        var submit = view.findViewById<Button>(R.id.suggestions)

        var market1 = view.findViewById<TextView>(R.id.market1)
        var market2 = view.findViewById<TextView>(R.id.market2)
        var market3 = view.findViewById<TextView>(R.id.market3)
        var market4 = view.findViewById<TextView>(R.id.market4)
        var market5 = view.findViewById<TextView>(R.id.market5)
        var market6 = view.findViewById<TextView>(R.id.market6)

        var market_box1=view.findViewById<EditText>(R.id.market1_box)
        var market_box2=view.findViewById<EditText>(R.id.market2_box)
        var market_box3=view.findViewById<EditText>(R.id.market3_box)
        var market_box4=view.findViewById<EditText>(R.id.market4_box)
        var market_box5=view.findViewById<EditText>(R.id.market5_box)
        var market_box6=view.findViewById<EditText>(R.id.market6_box)


        var suggestion_item= view.findViewById<EditText>(R.id.enter_item)

        //category spinner adapter
        ArrayAdapter.createFromResource(getActivity()!!.getApplicationContext(), R.array.category_list, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        //category spinner lsitner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                category_text=list_of_items[position]
                Toast.makeText(activity,"Market:"+market_text, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        submit!!.setOnClickListener(View.OnClickListener {
            market_box1.setText("")
            market_box2.setText("")
            market_box3.setText("")
            market_box4.setText("")
            market_box5.setText("")
            market_box6.setText("")
            mDatabase = FirebaseDatabase.getInstance()
            mDatabaseReference = mDatabase!!.reference!!.child("Users")
            mAuth = FirebaseAuth.getInstance()
            val mUser = mAuth!!.currentUser
            val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    category_obj=snapshot.child(category_text).value as MutableList<Map<String,String>>
                    category_obj!!.forEach {
                        if (it["ItemName"]!!.toLowerCase()==suggestion_item.text.toString().toLowerCase()){
                            market_list[it["Market"]!!]=it["Amount"]!!.toInt()
                        }
                    }
                    val result = market_list.toList().sortedByDescending { (_, value) -> value}.toMap()
                    var sorted_market_list= result.keys
                    var sorted_market_values= result.values

                    market1.setText(sorted_market_list.elementAt(0))
                    if(sorted_market_values.elementAt(0)==0){
                        market_box1.setText("N/A")
                    }else{
                        market_box1.setText(sorted_market_values.elementAt(0).toString())
                    }

                    market2.setText(sorted_market_list.elementAt(1))
                    if(sorted_market_values.elementAt(1)==0){
                        market_box2.setText("N/A")
                    }else{
                        market_box2.setText(sorted_market_values.elementAt(1).toString())
                    }

                    market3.setText(sorted_market_list.elementAt(2))
                    if(sorted_market_values.elementAt(2)==0){
                        market_box3.setText("N/A")
                    }else{
                        market_box3.setText(sorted_market_values.elementAt(2).toString())
                    }

                    market4.setText(sorted_market_list.elementAt(3))
                    if(sorted_market_values.elementAt(3)==0){
                        market_box4.setText("N/A")
                    }else{
                        market_box4.setText(sorted_market_values.elementAt(3).toString())
                    }

                    market5.setText(sorted_market_list.elementAt(4))
                    if(sorted_market_values.elementAt(4)==0){
                        market_box5.setText("N/A")
                    }else{
                        market_box5.setText(sorted_market_values.elementAt(4).toString())
                    }

                    market6.setText(sorted_market_list.elementAt(5))
                    if(sorted_market_values.elementAt(5)==0){
                        market_box6.setText("N/A")
                    }else{
                        market_box6.setText(sorted_market_values.elementAt(5).toString())
                    }

                    var layout=view.findViewById<LinearLayout>(R.id.table)
                    layout.visibility=View.VISIBLE

                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("hamza","afbkbfajbdf")
                }
            })

        })

        return view

    }


}