package com.hjry.loja.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hjry.loja.Nav
import com.hjry.loja.R
import com.hjry.loja.adapter.ProdutosRecyclerViewAdapter
import com.hjry.loja.model.Produto
import kotlinx.android.synthetic.main.activity_lista_produto.*

class ListaProdutoActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null;
    var adapter: ProdutosRecyclerViewAdapter? = null
    var database: DatabaseReference? = null
    var toggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produto)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()
        toggle = ActionBarDrawerToggle(
            this, DL,
            R.string.Open, R.string.Close
        )
        toggle?.let {
            DL.addDrawerListener(it)
            it.syncState()
        }
        NV.setNavigationItemSelectedListener {
            DL.closeDrawers()
            val frag = Nav.newInstance("", "")
            supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()

            if (it.title == "Todos") {
                configureDatabase("nome", "")
            } else {
                configureDatabase("categoria", it.title.toString());
            }

            false
        }
        configureDatabase("nome", "")
        buscarCategorias()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lista_produto, menu)
        val menuitem = menu?.findItem(R.id.btnBuscaProdutoMenu) as MenuItem
        val menuIte = menu?.findItem(R.id.about) as MenuItem

        val btnBusca = menuitem.actionView as SearchView

        btnBusca.queryHint = getString(R.string.NameProduct)

        btnBusca.setOnQueryTextListener(object
            : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                btnBusca.clearFocus()
                configureDatabase("nome", query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                configureDatabase("nome", newText)
                return true
            }

        })

        menuIte.setOnMenuItemClickListener {
            val i = Intent(this, AboutActivity::class.java)
            startActivity(i)
            true
        }


        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sair){
            auth?.signOut()
            val intent = Intent(this, MakeLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        toggle?.let {
            return it.onOptionsItemSelected(item)
        }
        return false
    }

    private fun criaOpcoesNavigation(categorias: MutableSet<String>) {
        NV.menu.add(1, 0, 1, "Todos")
        if (categorias.isNotEmpty()) {
            for (n in 0 until categorias.size) {
                NV.menu.add(1, n, n + 1, categorias.elementAt(n))
            }
        }
    }

    fun buscarCategorias() {
        var produtos: List<*>
        var categorias: MutableSet<String>
        database = FirebaseDatabase.getInstance().reference.child("produtos")
        val valueListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                produtos = snapshot.value as List<*>
                categorias = pegarCategoriasDosProdutos(produtos)
                criaOpcoesNavigation(categorias)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database?.addValueEventListener(valueListener)
    }

    private fun pegarCategoriasDosProdutos(produtos: List<*>): MutableSet<String> {
        val categorias: MutableSet<String> = mutableSetOf()

        for (produto in produtos) {
            produto as HashMap<*, *>
            val categoria = produto["categoria"] as String
            categorias.add(categoria)
        }
        return categorias
    }


    fun configureDatabase(tpBusca: String?, busca: String?) {

        database = FirebaseDatabase.getInstance().reference.child("produtos")


        database?.let {
            val options = FirebaseRecyclerOptions.Builder<Produto>()
                .setQuery(
                    it.orderByChild(tpBusca!!).startAt(busca).endAt(busca + "\uf8ff"),
                    Produto::class.java
                )
                .build()
            adapter = ProdutosRecyclerViewAdapter(options)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            adapter?.startListening()
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
