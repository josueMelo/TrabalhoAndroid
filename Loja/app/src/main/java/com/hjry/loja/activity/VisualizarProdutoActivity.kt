package com.hjry.loja.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hjry.loja.R
import com.hjry.loja.adapter.CarrinhoRecyclerViewAdapter
import com.hjry.loja.model.CarrinhoProduto
import com.hjry.loja.model.Produto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_visualizar_produto.*
import kotlinx.android.synthetic.main.cartao_lista_produto.view.*
import java.text.NumberFormat

class VisualizarProdutoActivity : AppCompatActivity() {
    var database: DatabaseReference? = null
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
        val desco = intent.getStringExtra("desconto")

        var i = 1

        etQnt.setText(i.toString())
        txtNomeVisualizar.text = title
        txtDescProduto.text = desc
        txtPrecoVisualizar.text = formatter.format(preco!!.toFloat())
        txtDescontoVisualizar.text = desco
        txtQtd.text = estoque
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + id + ".jpg?alt=media").into(
            imageProduto
        )


        btnMinus.setOnClickListener {
            if (Integer.parseInt(etQnt.text.toString()) > 1) {
                i = Integer.parseInt(etQnt.text.toString()) - 1
                etQnt.setText((i).toString())
                btnAdd.text = "Add R$" + (Integer.parseInt(etQnt.text.toString()) * preco.toFloat())
            }
        }
        btnPlus.setOnClickListener {
            if (Integer.parseInt(etQnt.text.toString()) < Integer.parseInt(estoque)) {
                i = Integer.parseInt(etQnt.text.toString()) + 1
                etQnt.setText((i).toString())
                btnAdd.setText("Add R$" + (Integer.parseInt(etQnt.text.toString()) * preco.toFloat()))
            }
        }

        btnAdd.setOnClickListener {
            etQnt.text?.let {
                configureDatabase(it.toString(),id)
                onBackPressed()
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun configureDatabase(qtdP: String?, idTxt: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        database = FirebaseDatabase.getInstance().reference.child("")

        user?.let {
            var id = user.email.toString()
            database?.let {


                val prod = CarrinhoProduto(idUser = id, idProduto = idTxt, qtd = qtdP)
                it?.child("user").parent
                val newProduct = it?.child("user")?.push()
                prod.id = newProduct?.key
                newProduct?.setValue(prod)
            }
        }


    }


}