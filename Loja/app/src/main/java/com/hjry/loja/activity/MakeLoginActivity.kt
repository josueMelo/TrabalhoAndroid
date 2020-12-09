package com.hjry.loja.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hjry.loja.R
import kotlinx.android.synthetic.main.activity_make_login.*

class MakeLoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_login)

        auth = FirebaseAuth.getInstance()

        register()
        if(auth?.currentUser == null) {
            login()
        } else {
            Toast.makeText(this, "Autenticado", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ListaProdutoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

   private fun login() {
        btnEntrar.setOnClickListener{
            if(TextUtils.isEmpty(etEmailLogin.text.toString())){
                etEmailLogin.setError("Por favor coloque seu email")
                return@setOnClickListener
            } else if(TextUtils.isEmpty(etPassowrdLogin.text.toString())){
                etPassowrdLogin.setError("Por favor coloque sua senha")
                return@setOnClickListener
            }
            auth?.signInWithEmailAndPassword(etEmailLogin.text.toString(), etPassowrdLogin.text.toString())?.addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Autenticado", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ListaProdutoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao fazer o login", Toast.LENGTH_LONG).show()
                }
            }
        }
   }

    private fun register() {
        btnCriarConta.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}