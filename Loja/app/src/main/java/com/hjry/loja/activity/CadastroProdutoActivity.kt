package com.hjry.loja.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hjry.loja.R
import com.hjry.loja.adapter.ProdutosRecyclerViewAdapter
import com.hjry.loja.model.Produto
import kotlinx.android.synthetic.main.activity_cadastro_produto.*
import kotlinx.android.synthetic.main.activity_lista_produto.*
import kotlinx.android.synthetic.main.cartao_lista_produto.*

class CadastroProdutoActivity : AppCompatActivity() {

    var database: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)

        configureDatabase()

        fabSalvarProduto.setOnClickListener {
            salvaProduto()
            val intent = Intent(this, ListaProdutoActivity::class.java)
            startActivity(intent)
        }

    }

    private fun salvaProduto() {

        val prod = Produto(
            nome = etNome.text.toString(),
            desc = etDesc.text.toString(),
            preco = etPreco.text.toString().toDouble(),
            estoque = etEstoque.text.toString().toInt(),
            desconto = etDesconto.text.toString().toInt()
        )
        val novoProduto = database?.push()
        prod.id = novoProduto?.key
        novoProduto?.setValue(prod)
    }

    fun configureDatabase() {
        database = FirebaseDatabase.getInstance().reference.child("produtos")
    }
}
