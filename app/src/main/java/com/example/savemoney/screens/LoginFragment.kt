package com.example.savemoney.screens

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private var invalidAddress = R.string.InvalidEmailAddress
    private var empty = R.string.Required
    private var minimum8 = R.string.Minimum8
    private var upperCase = R.string.Upper_case
    private var lowerCase = R.string.Lower_case

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backText.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_chooseFragment2)
            }
        }
        emailFocusListener()
        passwordFocusListener()
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
                binding.emailContainer.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#686767")))
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
                            "#686767"
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