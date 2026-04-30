package com.example.agrivigil_auth

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agrivigil_auth.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var authRepo: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepo = (requireActivity().application as MyApplication).authRepo

        // 1. Sync the switch state with current theme BEFORE setting the listener
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        binding.darkModeSwitch.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        // 2. Attach listeners
        setupListeners()
    }

    private fun setupListeners() {
        // Notification Switch
        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Enabled" else "Disabled"
            Toast.makeText(context, "Notifications $status", Toast.LENGTH_SHORT).show()
        }

        // Dark Mode Switch
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Logout Button
        binding.logoutButton.setOnClickListener {
            authRepo.logout()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

        // Placeholders for other buttons
        binding.editProfileBtn.setOnClickListener {
            Toast.makeText(context, "Edit Profile coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.changePasswordBtn.setOnClickListener {
            Toast.makeText(context, "Change Password coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
