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
        val estoque = intent.getStringExtra("estoque")
        val preco = intent.getStringExtra("preco")
        val desconto = intent.getStringExtra("desconto")
        var i = 1

        etQnt.setText(i.toString())
        txtNomeVisualizar.text = title
        textDescProduto.text = desc
        txtPrecoVisualizar.text = formatter.format(preco!!.toFloat())
        txtDescontoVisualizar.text = "desconto"
        txtQtd.text = estoque
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F$id.jpg?alt=media").into(
            imageProduto
        )


        btnMinus.setOnClickListener {
            if (Integer.parseInt(etQnt.text.toString()) > 1) {
                i = Integer.parseInt(etQnt.text.toString()) - 1
                etQnt.setText((i).toString())
                btnAdd.setText("Add R$" + (Integer.parseInt(etQnt.text.toString()) * preco.toFloat()))
            }
        }
        btnPlus.setOnClickListener {
            if (Integer.parseInt(etQnt.text.toString()) < Integer.parseInt(estoque)) {
                i = Integer.parseInt(etQnt.text.toString()) + 1
                etQnt.setText((i).toString())
                btnAdd.setText("Add R$" + (Integer.parseInt(etQnt.text.toString()) * preco.toFloat()))
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}