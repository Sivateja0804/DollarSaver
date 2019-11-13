package com.sshd.dollarsaver.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sshd.dollarsaver.MainPage
import com.sshd.dollarsaver.R
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import kotlinx.android.synthetic.main.login_page.*
class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        auth = FirebaseAuth.getInstance()
        //login button listner
        val login_button = findViewById<View>(R.id.login)
        login_button.setOnClickListener {
            //start data base code
            auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "Login succesful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainPage::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()

                    }
                }
        }

        //register button listner
        val register_button = findViewById<View>(R.id.register)
        register_button.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}