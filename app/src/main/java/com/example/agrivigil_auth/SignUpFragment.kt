package com.example.agrivigil_auth

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agrivigil_auth.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepo = (requireActivity().application as MyApplication).authRepo

        setupPasswordValidation()

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameInput.text.toString().trim()
            val lastName = binding.lastNameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val phone = binding.phoneInput.text.toString().trim()
            val pass = binding.passwordInput.text.toString()
            val confirmPass = binding.confirmPasswordInput.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    // Call updated signUp with phone number and profile details
                    authRepo.signUp(email, pass, phone, firstName, lastName) { success, error ->
                        if (success) {
                            Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                            authRepo.logout()
                            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                        } else {
                            Toast.makeText(context, "Sign up failed: $error", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPasswordValidation() {
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                validatePassword(password)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun validatePassword(password: String) {
        val hasUpper = password.any { it.isUpperCase() }
        val hasLower = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        val hasLength = password.length > 8

        updateIndicator(binding.tvUpper, "At least one uppercase letter", hasUpper)
        updateIndicator(binding.tvLower, "At least one lowercase letter", hasLower)
        updateIndicator(binding.tvNumber, "At least one number", hasDigit)
        updateIndicator(binding.tvSpecial, "At least one special character", hasSpecial)
        updateIndicator(binding.tvLength, "More than 8 characters", hasLength)

        // Enable button only if all requirements are met
        binding.signUpButton.isEnabled = hasUpper && hasLower && hasDigit && hasSpecial && hasLength
    }

    private fun updateIndicator(textView: TextView, originalText: String, isValid: Boolean) {
        if (isValid) {
            textView.setTextColor(Color.parseColor("#4CAF50")) // Green
            textView.text = "✓ $originalText"
        } else {
            textView.setTextColor(Color.RED)
            textView.text = "• $originalText"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
