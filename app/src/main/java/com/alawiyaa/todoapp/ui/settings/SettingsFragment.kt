package com.alawiyaa.todoapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alawiyaa.todoapp.databinding.FragmentSettingsBinding
import com.alawiyaa.todoapp.ui.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener


class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding?=null
    private val binding get() = _binding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        binding?.btnLogOut?.setOnClickListener {
            activity?.let { it1 ->
                mGoogleSignInClient.signOut()
                    .addOnCompleteListener(it1, OnCompleteListener<Void?> {
                        Toast.makeText(activity,"Logout",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity,LoginActivity::class.java))
                        activity?.finish()
                    })
            }
        }
    }





}