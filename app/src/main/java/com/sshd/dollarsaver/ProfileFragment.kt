package com.sshd.dollarsaver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =inflater.inflate(R.layout.profile_layout,null)

        val edit_password_click_me = view.findViewById(R.id.edit_password) as TextView
        edit_password_click_me.setOnClickListener {
        val password_layout=view.findViewById(R.id.password_layout) as LinearLayout
            password_layout.visibility=View.VISIBLE
        }
        return view
    }


}