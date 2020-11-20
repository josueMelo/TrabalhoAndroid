package com.hjry.loja.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hjry.loja.R
import com.hjry.loja.adapter.ProdutosRecyclerViewAdapter
import com.hjry.loja.model.Produto
import kotlinx.android.synthetic.main.activity_lista_produto.*

class ListaProdutoActivity : AppCompatActivity() {

    var adapter: ProdutosRecyclerViewAdapter? = null
    var database: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produto)

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        configureDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lista_produto, menu)
        val menuitem = menu?.findItem(R.id.btnBuscaProdutoMenu) as MenuItem
        val btnBusca = menuitem.actionView as SearchView
        btnBusca.queryHint = "Nome do Produto"

        btnBusca.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@ListaProdutoActivity, "submit: $query", Toast.LENGTH_SHORT)
                    .show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@ListaProdutoActivity, "change: $newText", Toast.LENGTH_SHORT)
                    .show()
                return true
            }

        })

        return true
    }

    fun configureDatabase() {

        database = FirebaseDatabase.getInstance().reference
        database?.let {
            val options = FirebaseRecyclerOptions.Builder<Produto>()
                .setQuery(it.child("produtos"), Produto::class.java)
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