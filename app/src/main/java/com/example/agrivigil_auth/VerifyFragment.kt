package com.example.agrivigil_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agrivigil_auth.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepo = (requireActivity().application as MyApplication).authRepo

        binding.verifyButton.setOnClickListener {
            // Logic temporarily disabled while skipping 2FA
            Toast.makeText(context, "Verification is currently disabled", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_verifyFragment_to_loginFragment)
        }

        binding.resendCodeLink.setOnClickListener {
            Toast.makeText(context, "Resend not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
