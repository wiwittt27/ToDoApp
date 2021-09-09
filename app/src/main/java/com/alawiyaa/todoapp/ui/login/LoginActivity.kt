package com.alawiyaa.todoapp.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alawiyaa.todoapp.databinding.ActivityLoginBinding
import com.alawiyaa.todoapp.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity(){

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding

    companion object {
        const val RC_SIGN_IN = 123
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
       val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)

        binding?.btnSignIn?.setSize(SignInButton.SIZE_STANDARD)

        binding?.btnSignIn?.setOnClickListener{
            val signInIntent: Intent? = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    private fun updateUI(account: GoogleSignInAccount?) {
    binding?.tvSignIn?.text = account?.displayName

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            Toast.makeText(this,"Welcome ${account?.displayName}",Toast.LENGTH_SHORT).show()
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            val account: GoogleSignInAccount? = task?.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
           startActivity(Intent(this@LoginActivity,HomeActivity::class.java))

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }

}