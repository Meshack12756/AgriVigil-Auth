package com.example.agrivigil_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agrivigil_auth.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepo = (requireActivity().application as MyApplication).authRepo

        binding.sendResetLinkButton.setOnClickListener {
            val email = binding.resetEmailInput.text.toString()
            if (email.isNotEmpty()) {
                authRepo.resetPassword(email) { success, error ->
                    if (success) {
                        Toast.makeText(context, "Reset link sent to your email", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.loginFragment)
                    } else {
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backToLoginButton.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
