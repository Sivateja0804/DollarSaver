package com.sshd.dollarsaver.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sshd.dollarsaver.MainPage
import com.sshd.dollarsaver.R

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        //login button listner
        val login_button = findViewById<View>(R.id.login)
        login_button.setOnClickListener {
            //start data base code

            //get 200OK response
            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }

        //register button listner
        val register_button = findViewById<View>(R.id.register)
        register_button.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

    }
}