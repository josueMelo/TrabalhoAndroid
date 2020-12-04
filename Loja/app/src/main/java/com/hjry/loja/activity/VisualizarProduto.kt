package com.hjry.loja.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hjry.loja.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_visualizar_produto.*
import kotlinx.android.synthetic.main.cartao_lista_produto.view.*

class VisualizarProduto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_produto)
        var id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val qtd = intent.getStringExtra("estoque")
        val preco = intent.getStringExtra("preco")

        txtNomeVisualizar.text = title
        textDescProduto.text = desc
        txtPrecoVisualizar.text = preco
        txtQtd.text = qtd
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + id + ".jpg?alt=media").into(
            imageProduto
        )
    }
}