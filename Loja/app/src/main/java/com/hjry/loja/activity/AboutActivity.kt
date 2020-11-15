package com.hjry.loja.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjry.loja.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val actionBar =supportActionBar
        actionBar!!.title = resources.getString(R.string.about)
        actionBar.setDisplayHomeAsUpEnabled(true)

        txtEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "hjrysenac@gmail.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, resources.getString(R.string.send_email)))
        }
        txtSite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse("https://www.sp.senac.br/"))
            startActivity(intent)
        }



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}