package com.example.savemoney.screens

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding

    val mAuth = Firebase.auth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText


    private var invalidAddress = R.string.InvalidEmailAddress
    private var empty = R.string.Required
    private var minimum8 = R.string.Minimum8
    private var upperCase = R.string.Upper_case
    private var lowerCase = R.string.Lower_case

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        binding.buttonRegister.setOnClickListener {
            registerUser()
        }

        binding.backText.setOnClickListener {
            view?.let { // Here we prevented application from unexpected closure
                Navigation.findNavController(it).navigate(R.id.action_registrationFragment_to_chooseFragment2)
            }
        }
        binding.haveAccountText.setOnClickListener {
            view?.let { // Here we prevented application from unexpected closure
                Navigation.findNavController(it).navigate(R.id.action_registrationFragment_to_loginFragment)
            }
        }
        emailFocusListener()
        passwordFocusListener()
    }

    private fun registerUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty()){
            emailEditText.error = "Email is required"
            emailEditText.requestFocus()
            return
        }

        if (password.isEmpty()){
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) {task ->
            if (task.isSuccessful){
                Toast.makeText(context, "You've just created a new account", Toast.LENGTH_LONG).show()
                view?.let { // Here we prevented application from unexpected closure
                    Navigation.findNavController(it).navigate(R.id.action_registrationFragment_to_loginFragment)
                }

            } else{
                Toast.makeText(context, task.exception?.message ?: "ERROR", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            if (emailText.isEmpty()) {
                binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
                return binding.root.context.getString(empty)
            } else {
                binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                return binding.root.context.getString(invalidAddress)
            }

        }
        return null
    }


    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.setHelperTextColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            "#FFFFFF"
                        )
                    )
                )
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.passwordEditText.text.toString()
        if (passwordText.length < 8) {
            binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.RED))
            return binding.root.context.getString(minimum8)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.RED))
            return binding.root.context.getString(upperCase)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            binding.passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.RED))
            return binding.root.context.getString(lowerCase)
        }

        return null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}