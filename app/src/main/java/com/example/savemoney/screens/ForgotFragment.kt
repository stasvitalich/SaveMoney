package com.example.savemoney.screens

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentForgotBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotFragment : Fragment() {

    private lateinit var binding: FragmentForgotBinding
    private lateinit var mAuthorization: FirebaseAuth
    private val invalidAddress = R.string.InvalidEmailAddress
    private val empty = R.string.Required

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuthorization = Firebase.auth

        binding.buttonRecoverPassword.setOnClickListener {
            if (checkEmail()) {
                val email = binding.emailEditTextForRecovery.text.toString()
                mAuthorization.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "E-mail was sent!", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).navigate(R.id.action_forgotFragment_to_loginFragment)
                    } else {
                        Toast.makeText(context, "Failed to send E-mail.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        emailFocusListener()
    }



    private fun checkEmail(): Boolean {
        val checkedEmail = binding.emailEditTextForRecovery.text.toString()
        if (checkedEmail.isEmpty()) {
            binding.emailContainerForRecovery.error = "This is a required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(checkedEmail).matches()) {
            binding.emailContainerForRecovery.error = "Incorrect Email"
            return false
        }

        return true
    }


    private fun emailFocusListener() {
        binding.emailEditTextForRecovery.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailContainerForRecovery.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.emailEditTextForRecovery.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            if (emailText.isEmpty()) {
                binding.emailContainerForRecovery.setHelperTextColor(
                    ColorStateList.valueOf(
                        Color.parseColor(
                            "#686767"
                        )
                    )
                )
                return binding.root.context.getString(empty)
            } else {
                binding.emailContainerForRecovery.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                return binding.root.context.getString(invalidAddress)
            }
        }
        return null
    }

    companion object {

        @JvmStatic
        fun newInstance() = ForgotFragment()
    }
}