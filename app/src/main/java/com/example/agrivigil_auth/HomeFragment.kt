package com.example.agrivigil_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agrivigil_auth.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepo = (requireActivity().application as MyApplication).authRepo

        // Set the welcome message
        binding.welcomeText.text = "Welcome to AgriVigil"

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_settings)
        }

        // Logout button logic
        binding.logoutButton.setOnClickListener {
            authRepo.logout()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
