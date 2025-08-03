package com.example.recipes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.recipes.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var emailText: TextInputEditText
    private lateinit var passwordText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        emailText = view.findViewById(R.id.email_text)
        passwordText = view.findViewById(R.id.password_text)
        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register)

        registerButton.setOnClickListener {
            val action = LoginFragmentDirections.register()
            findNavController().navigate(action)
        }

        loginButton.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "please enter credentials", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login successful!!", Toast.LENGTH_LONG).show()
                    val action = LoginFragmentDirections.myProfile()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Login failed!!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}