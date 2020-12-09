package com.hjry.loja.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hjry.loja.R
import com.hjry.loja.adapter.CarrinhoRecyclerViewAdapter
import com.hjry.loja.adapter.ProdutosRecyclerViewAdapter
import com.hjry.loja.model.CarrinhoProduto
import com.hjry.loja.model.Produto
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_lista_produto.*
import kotlinx.android.synthetic.main.cartao_lista_carrinho.view.*

class CarrinhoActivity : AppCompatActivity() {

    var adapter: CarrinhoRecyclerViewAdapter? = null
    var databaseUser: DatabaseReference? = null
    var databaseProdutos: DatabaseReference? = null
    var toggle: ActionBarDrawerToggle? = null
    var prod: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // vai receber um produtto do intent
        configureDatabase(prod?.nome)
        toggle = ActionBarDrawerToggle(
            this, DLCarrinho,
            R.string.Open, R.string.Close
        )
        toggle?.let {
            DLCarrinho.addDrawerListener(it)
            it.syncState()
        }

//        NVCarrinho.setNavigationItemSelectedListener {
//            DLCarrinho.closeDrawers()
//            if (it.itemId == R.id.Cell) {
//                val frag = Nav.newInstance("", "")
//                supportFragmentManager.beginTransaction().replace(R.id.fragCarrinho, frag).commit()
//
//                true
//            }
//            false
//        }

        //configureDatabase("")

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//val inflater: MenuInflater = menuInflater
//inflater.inflate(R.menu.menu_lista_produto, menu)
//val menuitem = menu?.findItem(R.id.btnCarrinho) as MenuItem
//val menuIte = menu?.findItem(R.id.about) as MenuItem


//            override fun onQueryTextChange(newText: String?): Boolean {
//                configureDatabase(newText)
//                return true
//            }

//        })

//        menuIte.setOnMenuItemClickListener {
//            val i = Intent(this,AboutActivity::class.java)
//            startActivity(i)
//            true
//        }


//        return true
//    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//
//        toggle?.let {
//            return it.onOptionsItemSelected(item)
//        }
//        return false
//    }
//
//
    fun configureDatabase(busca: String?) {

        databaseUser = FirebaseDatabase.getInstance().reference.child("user")


        Log.d("debug", "" + databaseUser)


        databaseUser?.let {


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
}