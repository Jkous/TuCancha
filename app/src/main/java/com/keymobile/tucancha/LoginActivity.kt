package com.keymobile.tucancha

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    var etEmail: EditText? = null
    var etPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_login)

        etEmail = findViewById<EditText>(R.id.username)
        etPassword = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)

        login.setOnClickListener { loginUser() }
        signup.setOnClickListener { view ->
            val intent = Intent(applicationContext,RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null)
            LoginSuccess()
    }

    private fun loginUser() {

        val email = etEmail?.text.toString()
        val password = etPassword?.text.toString()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SUCCESS", "signInWithEmail:success")
                        //val user = auth.currentUser
                        LoginSuccess()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("ERROR", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Usuario y/o contraseña incorrecta.",
                                Toast.LENGTH_SHORT).show()


                    }
                }

        /*
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("mensaje", "hola")
        }

        startActivity(intent)
        */

    }

    private fun LoginSuccess() {

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
