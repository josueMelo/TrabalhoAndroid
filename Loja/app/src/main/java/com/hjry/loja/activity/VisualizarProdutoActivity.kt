package com.hjry.loja.activity

import android.R.attr.name
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.hjry.loja.R
import com.hjry.loja.model.CarrinhoProduto
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

        btnAdd.text = "Add " + formatter.format(preco!!.toFloat())
        etQnt.setText(i.toString())
        txtNomeVisualizar.text = title
        txtDescProduto.text = desc
        txtPrecoVisualizar.text = formatter.format(preco!!.toFloat())
        txtDescontoVisualizar.text = desco
        txtQtd.text = "Available: " + estoque.toString()
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + id + ".jpg?alt=media").into(
            imageProduto
        )

        if(Integer.parseInt(estoque) < 1) {
            btnMinus.isEnabled = false
            btnPlus.isEnabled = false
            btnAdd.isEnabled = false
        }

            btnMinus.setOnClickListener {
                if (Integer.parseInt(etQnt.text.toString()) > 1) {
                    i = Integer.parseInt(etQnt.text.toString()) - 1
                    etQnt.setText((i).toString())
                    btnAdd.text =
                        "Add " + formatter.format(Integer.parseInt(etQnt.text.toString()) * preco!!.toFloat())
                }
            }
            btnPlus.setOnClickListener {
                if (Integer.parseInt(etQnt.text.toString()) < Integer.parseInt(estoque)) {
                    i = Integer.parseInt(etQnt.text.toString()) + 1
                    etQnt.setText((i).toString())
                    btnAdd.text =
                        "Add " + formatter.format(Integer.parseInt(etQnt.text.toString()) * preco!!.toFloat())
                }
            }

            btnAdd.setOnClickListener {
                etQnt.text?.let {
                    configureDatabase(it.toString(), id)
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
        var teste = false
        user?.let {
            var id = user.email.toString()
            database?.let {
                if(!teste) {
                    val prod = CarrinhoProduto(idUser = id, idProduto = idTxt, qtd = qtdP)
                    it?.child("user").parent
                    val newProduct = it?.child("user")?.push()
                    prod.id = newProduct?.key
                    newProduct?.setValue(prod)
                }
                else{

                }
            }
        }


    }


}