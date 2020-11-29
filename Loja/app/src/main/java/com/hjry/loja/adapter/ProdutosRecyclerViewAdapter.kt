package com.hjry.loja.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.ObservableSnapshotArray
import com.hjry.loja.R
import com.hjry.loja.model.Produto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cartao_lista_produto.view.*
import java.text.NumberFormat

//todo 40m
class ProdutosRecyclerViewAdapter(options: FirebaseRecyclerOptions<Produto>) :
    FirebaseRecyclerAdapter<Produto, ProdutosRecyclerViewAdapter.ProdutoViewHolder>(options) {

    class ProdutoViewHolder(
        override val containerView: View,
    ) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        val formatter = NumberFormat.getCurrencyInstance()

        fun bind(produto: Produto) {

            containerView.txtNome.text = produto.nome
            containerView.txtDesc.text = produto.desc
            containerView.txtPreco.text = formatter.format(produto.preco)

            containerView.setOnClickListener {
                Toast.makeText(containerView.context, "Clicou no " + containerView.txtNome.text, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardItem = inflater.inflate(R.layout.cartao_lista_produto, parent, false)
        return ProdutoViewHolder(cardItem)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int, produto: Produto) {
        holder.bind(produto)
    }
}