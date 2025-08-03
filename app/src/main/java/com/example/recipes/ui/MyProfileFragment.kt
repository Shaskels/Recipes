package com.example.recipes.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipes.R
import com.example.recipes.domain.entity.Profile
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class MyProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.my_profile_title)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database
        val name: TextInputLayout = view.findViewById(R.id.user_name)
        val email: TextInputLayout = view.findViewById(R.id.user_email)
        val age: TextInputLayout = view.findViewById(R.id.user_age)

        val user = auth.currentUser
        user?.uid?.let {
            database.reference.child("Users").child(user?.uid!!).get().addOnSuccessListener {
                val map = it.value as Map<String, String>
                val profile = map["name"]?.let { it1 -> map["age"]?.let { it2 -> Profile(it1, it2) } }
                Log.d("Profile", "${profile?.age} ${profile?.name}")
                name.editText?.setText(profile?.name)
                age.editText?.setText(profile?.age)
            }
        }

        email.editText?.setText(user?.email)
    }
}