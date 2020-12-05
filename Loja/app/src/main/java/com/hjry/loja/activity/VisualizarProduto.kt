package com.hjry.loja.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hjry.loja.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_visualizar_produto.*
import kotlinx.android.synthetic.main.cartao_lista_produto.view.*
import java.text.NumberFormat

class VisualizarProduto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar =supportActionBar
        actionBar!!.title = resources.getString(R.string.Product)
        actionBar.setDisplayHomeAsUpEnabled(true)


        setContentView(R.layout.activity_visualizar_produto)

        val formatter = NumberFormat.getCurrencyInstance()

        var id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val qtd = intent.getStringExtra("estoque")
        val preco = intent.getStringExtra("preco")


        txtNomeVisualizar.text = title
        textDescProduto.text = desc
        txtPrecoVisualizar.text = formatter.format(preco!!.toFloat())
        txtQtd.text = qtd
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + id + ".jpg?alt=media").into(
            imageProduto
        )
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}