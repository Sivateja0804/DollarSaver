package com.sshd.dollarsaver

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_layout.*



class ProfileFragment : Fragment(){

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    //UI elements
    private var mobilenumber: EditText? = null
    private var enter_password: EditText? = null
    private var confirm_password: EditText? = null
    private var submit_button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.profile_layout,null)

        val edit_password_click_me = view.findViewById(R.id.edit_password) as TextView
        submit_button = view.findViewById(R.id.submit) as Button
        mobilenumber=view.findViewById(R.id.mobilenumber) as EditText
        enter_password=view.findViewById(R.id.enter_password) as EditText
        confirm_password=view.findViewById(R.id.confirm_password) as EditText
        mobilenumber=view.findViewById(R.id.mobilenumber) as EditText
        val username=activity!!.intent.getStringExtra("username")
        val edit_username=view.findViewById(R.id.email) as EditText
        edit_username.setText(username)
        edit_username.isEnabled =false

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mobilenumber!!.setText( snapshot.child("mobilenumber").value as String)
                mobilenumber!!.isEnabled =false
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })


        edit_password_click_me.setOnClickListener {
        val password_layout=view.findViewById(R.id.password_layout) as LinearLayout
            password_layout.visibility=View.VISIBLE
        }

        submit_button!!.setOnClickListener(View.OnClickListener{
            if (TextUtils.isEmpty(enter_password!!.text.toString())){
                Toast.makeText(activity,"Enter your password!!", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(confirm_password!!.text.toString())){
                Toast.makeText(activity,"Enter Current Password", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if(confirm_password!!.text.toString()!=enter_password!!.text.toString()){
                Toast.makeText(activity,"Enter Password and Current Password are not matching", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (confirm_password!!.text.toString().length < 6){
                Toast.makeText(activity,"Password too short, enter mimimum 6 charcters" , Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mUser!!.updatePassword(confirm_password!!.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity,"Password updated Successfully",Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(activity,"Password not updated Successfully",Toast.LENGTH_LONG)
                }
            }
        })
        return view
    }

}