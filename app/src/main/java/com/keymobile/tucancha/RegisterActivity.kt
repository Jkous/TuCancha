package com.keymobile.tucancha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.keymobile.tucancha.utils.UsuarioSesion

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private final var firebaseDatabase: FirebaseDatabase? = FirebaseDatabase.getInstance()
    private final var databaseReference: DatabaseReference? = null

    private var etEmail: EditText? = null
    private var etPass: EditText? = null
    private var etRepeatPass: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_register)
        asignarReferencias()
    }

    private fun asignarReferencias() {
        InicializarFirebase()

        etEmail = findViewById(R.id.username)
        etPass = findViewById(R.id.password)
        etRepeatPass = findViewById(R.id.password2)

        val btnRegistrar = findViewById<Button>(R.id.register)
        btnRegistrar.setOnClickListener { validateFields() }

    }

    private fun validateFields() {
  /*
        if (//etEmail?.text.isNullOrEmpty() || etPass?.text.isNullOrEmpty() || etRepeatPass?.text.isNullOrEmpty() ||
                etPass?.text != etRepeatPass?.text) {
            Toast.makeText(this, "Verifique los campos", Toast.LENGTH_SHORT).show()
            return
        }
*/
        RegistrarUsuario(etEmail?.text.toString(), etPass?.text.toString())

    }

    private fun RegistrarUsuario(username:String, password: String) {

        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task->
                    if (task.isSuccessful) {
                        Log.d("SIGNUP", "createUserWithEmail:success")
                        val user = auth.currentUser

                        RegistrarDatosAdicionalesusuario(user.uid, false)


                    } else {
                        Log.w("SIGNUP", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }

    }

    private fun InicializarFirebase() {

        FirebaseApp.initializeApp(this)
        //firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference

    }

    private fun RegistrarDatosAdicionalesusuario(uid:String, is_admin:Boolean) {

        val user = UsuarioSesion.configureUsuarioSesion(uid, is_admin)

        databaseReference?.child("InfoUsuario")?.child(user.uid)?.setValue(user)?.addOnCompleteListener {

            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

}