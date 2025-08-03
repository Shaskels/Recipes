package com.example.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipes.R
import com.example.recipes.domain.entity.Profile
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class RegisterFragment : Fragment() {

    private lateinit var emailText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var nameText: TextInputEditText
    private lateinit var ageText: TextInputEditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database

        val user = auth.currentUser
        if (user!=null){
            val action = RegisterFragmentDirections.myProfile()
            findNavController().navigate(action)
        }

        emailText = view.findViewById(R.id.email_text)
        passwordText = view.findViewById(R.id.password_text)
        nameText = view.findViewById(R.id.name_text)
        ageText = view.findViewById(R.id.age_text)
        registerButton = view.findViewById(R.id.registerButton)
        loginButton = view.findViewById(R.id.login)

        loginButton.setOnClickListener {
            val action = RegisterFragmentDirections.login()
            findNavController().navigate(action)
        }

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser(){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        val name = nameText.text.toString()
        val age = ageText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "please enter credentials", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileId = task.result.user?.uid
                    val profile = Profile(name, age)
                    profileId?.let {database.reference.child("Users").child(profileId).setValue(profile)}
                    Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_LONG).show()
                    val action = RegisterFragmentDirections.myProfile()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}