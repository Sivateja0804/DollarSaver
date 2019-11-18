package com.sshd.dollarsaver

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sshd.dollarsaver.Activities.Login


class MainPage : AppCompatActivity() {

    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mFragmentManager: FragmentManager
    lateinit var mFragmentTransaction: FragmentTransaction
    lateinit var mNavigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.home_page)

        mDrawerLayout=findViewById<View>(R.id.drawerLayout) as DrawerLayout
        mNavigationView=findViewById<View>(R.id.navView) as NavigationView

        mFragmentManager= supportFragmentManager
        mFragmentTransaction= mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.containerView, TabFragment()).commit()

        mNavigationView.setNavigationItemSelectedListener {
                menuItem ->  mDrawerLayout.closeDrawers()
            if (menuItem.itemId==R.id.nav_item_home){

                val ft= mFragmentManager.beginTransaction()
                ft.replace(R.id.containerView, TabFragment()).commit()
            }

            if (menuItem.itemId==R.id.nav_item_profile){
                val ft= mFragmentManager.beginTransaction()
                ft.replace(R.id.containerView, ProfileFragment()).commit()
            }
            if (menuItem.itemId==R.id.nav_item_account_balance){
                val ft= mFragmentManager.beginTransaction()
                ft.replace(R.id.containerView, AccountBalanceFragment()).commit()
            }
            if (menuItem.itemId==R.id.nav_item_logout){
                // build alert dialog
                val dialogBuilder = AlertDialog.Builder(this)

                // set message of alert dialog
                dialogBuilder.setMessage("Do you want to log out from DollarSaver")
                    .setCancelable(false)
                    .setPositiveButton("yes", DialogInterface.OnClickListener {
                            dialog, id ->
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent);
                    })
                    .setNegativeButton("no", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("DollarSaver")
                alert.show()
            }
            false
        }
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val mDrawerToggle = ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,R.string.app_name)
        mDrawerLayout.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        }

    override fun onBackPressed() {
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Do you want to log out from DollarSaver")
            .setCancelable(false)
            .setPositiveButton("yes", DialogInterface.OnClickListener {
                    dialog, id ->
                val intent = Intent(this, Login::class.java)
                startActivity(intent);
                super.onBackPressed()
            })
            .setNegativeButton("no", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("DollarSaver")
        alert.show()

    }

}
