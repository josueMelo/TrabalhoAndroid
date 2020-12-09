package com.hjry.loja.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hjry.loja.R
import com.hjry.loja.adapter.CarrinhoRecyclerViewAdapter
import com.hjry.loja.model.CarrinhoProduto
import com.hjry.loja.model.Produto
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_carrinho.view.*
import kotlinx.android.synthetic.main.activity_lista_produto.*
import kotlinx.android.synthetic.main.cartao_lista_carrinho.view.*

class CarrinhoActivity : AppCompatActivity() {

    var adapter: CarrinhoRecyclerViewAdapter? = null
    var databaseUser: DatabaseReference? = null
    var database: DatabaseReference? = null
    var toggle: ActionBarDrawerToggle? = null
    var users: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
            this, containerCarrinho,
            R.string.Open, R.string.Close
        )
        toggle?.let {
            containerCarrinho.addDrawerListener(it)
            it.syncState()
        }
        configureDatabase()

    }
    fun configureDatabase() {

        database = FirebaseDatabase.getInstance().reference.child("user")
        val users = FirebaseDatabase.getInstance().reference.child("user")

        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                handleData(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database?.addValueEventListener(valueListener)


        Log.d("debug", "" + database)


        database?.let {

        }
    }

    fun refreshUI(list: List<Produto>) {
        containerCarrinho.removeAllViews()

        list.forEach {

            val card = layoutInflater.inflate(R.layout.cartao_lista_carrinho, containerCarrinho, false)

            card.txtNomeCarrinho.text = it.nome
            card.txtDescCarrinho.text = it.desc
            card.txtPrecoCarrinho.text = it.preco.toString()
            card.txtQtdCarrinho.text = it.estoque.toString()
            containerCarrinho.addView(card)
        }
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter?.startListening()
    }


    private fun handleData(dataSnapshot: DataSnapshot) {
        val user = FirebaseAuth.getInstance().currentUser
        var itemListProd = arrayListOf<Produto>()
        user?.let {
            val itemList = arrayListOf<CarrinhoProduto>()
            dataSnapshot.child("user").children.forEach { db->

                val email = it.email.toString()

                val currentItem = db
                val map = currentItem.getValue() as HashMap<String, Any>

                var idUser: String = map.get("idUser") as String

                if(idUser==email) {
                    var id: String? = currentItem.key
                    var idProduto: String = map.get("idProduto") as String
                    var qtd: String = map.get("qtd") as String

                    val prod = CarrinhoProduto(id, idProduto, idUser, qtd)
                    itemList.add(prod);
                }
            }

            itemListProd = arrayListOf<Produto>()
            dataSnapshot.child("produto").children.forEach { db->
                val currentItem = db
                val map = currentItem.getValue() as HashMap<String, Any>

                var id: String? = currentItem.key

                itemList.forEach{ cr->
                    if(id == cr.idProduto){
                        var nome: String = map.get("nome") as String
                        var desc: String?  = map.get("desc") as String
                        var estoque: Int = cr.qtd as Int
                        var preco: Double  = map.get("preco") as Double
                        var desconto: Int? = map.get("desconto") as Int

                        val car = Produto(id, nome, desc, null, estoque, preco, desconto)
                        itemListProd.add(car)
                    }
                }
            }
        }
        refreshUI(itemListProd)
    }
}