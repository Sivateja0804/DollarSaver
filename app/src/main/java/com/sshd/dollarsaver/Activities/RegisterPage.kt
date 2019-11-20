package com.sshd.dollarsaver.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sshd.dollarsaver.R
import kotlinx.android.synthetic.main.register_page.*
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.view.View
import androidx.navigation.findNavController
import android.widget.Toast
import android.content.Intent
import android.renderscript.Sampler
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Categories(
    var Food: String? = "",
    var Rent: String? = "",
    var Transport: String? = "",
    var Entertainment: String? = "",
    var Stationery: String? = "",
    var Utilities: String? = "",
    var Miscellaneous: String? = "",
    var Budget: String? = "1000"
)

class RegisterPage : AppCompatActivity() {

    private var auth : FirebaseAuth?= null
    //private lateinit var database: DatabaseReference


    private var inputFirstName: EditText? = null
    private var inputLastName: EditText? = null
    private var inputEmail: EditText? = null
    private var inputphoneNumber:EditText? = null
    private var inputPassword: EditText? = null
    private var inputconfirmPassword: EditText? = null
    private var btnSignUp: Button? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)
        inputFirstName = findViewById(R.id.firstname) as EditText
        inputLastName = findViewById(R.id.lastname) as EditText
        inputphoneNumber = findViewById(R.id.phonenumber) as EditText
        inputEmail = findViewById(R.id.emailid) as EditText
        inputPassword = findViewById(R.id.reg_password) as EditText
        inputconfirmPassword = findViewById(R.id.reg_password_cnf) as EditText
        btnSignUp = findViewById(R.id.sign_up) as Button

        auth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        //database = FirebaseDatabase.getInstance().reference


        btnSignUp!!.setOnClickListener(View.OnClickListener {
            val firstname = inputFirstName!!.text.toString().trim()
            val lastname = inputLastName!!.text.toString().trim()
            val phonenumber = inputphoneNumber!!.text.toString().trim()
            val email = inputEmail!!.text.toString().trim()
            val password = inputPassword!!.text.toString().trim()
            val confirmpassword = inputconfirmPassword!!.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext,"Enter your email Address!!", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext,"Enter your Password",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (password.length < 6){
                Toast.makeText(applicationContext,"Password too short, enter mimimum 6 charcters" , Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(firstname)){
                Toast.makeText(applicationContext,"Enter your Firstname",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(lastname)){
                Toast.makeText(applicationContext,"Enter your Lastname",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(phonenumber)){
                Toast.makeText(applicationContext,"Enter your Phone Number",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (TextUtils.isEmpty(confirmpassword)){
                Toast.makeText(applicationContext,"Enter your Password Again",Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            auth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, OnCompleteListener { task ->

                    if (!task.isSuccessful){
                        Toast.makeText(this@RegisterPage,"Registration Failed",Toast.LENGTH_SHORT).show()
                        return@OnCompleteListener
                    }else{
                        val userId = auth!!.currentUser!!.uid
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.setValue(Categories("","","","","","","","1000"))
                        currentUserDb.child("firstName").setValue(firstname)
                        currentUserDb.child("lastName").setValue(lastname)
                        currentUserDb.child("mobilenumber").setValue(phonenumber)
                        Toast.makeText(this,"Registration Successful"+task.isSuccessful,Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterPage, Login::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                })

        })
    }
    }
