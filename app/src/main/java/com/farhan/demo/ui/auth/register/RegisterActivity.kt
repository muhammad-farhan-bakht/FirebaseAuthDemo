package com.farhan.demo.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farhan.demo.databinding.ActivityRegisterBinding
import com.farhan.demo.ui.auth.login.LoginActivity
import com.farhan.demo.ui.home.HomeActivity
import com.farhan.demo.util.gone
import com.farhan.demo.util.navigateToWithClearBackStack
import com.farhan.demo.util.visible
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import timber.log.Timber

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.buttonRegister.setOnClickListener {
            if (validate()){
                showLoading()
                register(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        }

        binding.buttonLogin.setOnClickListener {
            this@RegisterActivity.navigateToWithClearBackStack(LoginActivity::class.java)
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("createUserWithEmail:success")
                    this@RegisterActivity.navigateToWithClearBackStack(HomeActivity::class.java)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w("createUserWithEmail:failure ${task.exception}")
                    Snackbar.make(binding.root, "Authentication failed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                hideLoading()
            }
    }

    private fun validate(): Boolean {
        var validate = true
        binding.etFullName.apply {
            validator()
                .nonEmpty()
                .addErrorCallback {
                    this.requestFocus()
                    this.error = it
                    validate = false
                }
                .check()
        }
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