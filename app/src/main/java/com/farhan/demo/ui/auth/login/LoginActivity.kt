package com.farhan.demo.ui.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.farhan.demo.databinding.ActivityLoginBinding
import com.farhan.demo.ui.auth.register.RegisterActivity
import com.farhan.demo.ui.home.HomeActivity
import com.farhan.demo.util.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import timber.log.Timber

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.buttonLogin.setOnClickListener {
            if (validate()) {
                showLoading()
                authenticate(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        }

        binding.buttonRegister.setOnClickListener {
            this@LoginActivity.navigateTo(RegisterActivity::class.java)
        }

        binding.buttonReset.setOnClickListener {
            throw IllegalArgumentException("Test Crash")
        }
    }

    private fun validate(): Boolean {
        var validate = true
        binding.etEmail.apply {
            validator()
                .nonEmpty()
                .addErrorCallback {
                    this.requestFocus()
                    this.error = it
                    validate = false
                }
                .check()
        }
        binding.etPassword.apply {
            validator()
                .nonEmpty()
                .addErrorCallback {
                    this.requestFocus()
                    this.error = it
                    validate = false
                }
                .check()
        }
        return validate
    }

    private fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.e("signInWithEmail:success")
                    this@LoginActivity.navigateToWithClearBackStack(HomeActivity::class.java)
                } else {
                    // If sign in fails, display a message to the user.
                    Snackbar.make(binding.root, "Authentication failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                hideLoading()
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            this@LoginActivity.navigateToWithClearBackStack(HomeActivity::class.java)
        }
    }

    private fun showLoading(){
        with(binding){
            progressBar.visible()
            buttonLogin.gone()
            buttonRegister.gone()

        }
    }

    private fun hideLoading(){
        with(binding){
            progressBar.gone()
            buttonLogin.visible()
            buttonRegister.visible()
        }
    }

}
