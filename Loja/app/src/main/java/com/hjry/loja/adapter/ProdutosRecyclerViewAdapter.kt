package com.hjry.loja.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.hjry.loja.R
import com.hjry.loja.activity.ListaProdutoActivity
import com.hjry.loja.activity.VisualizarProduto
import com.hjry.loja.model.Produto
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cartao_lista_produto.view.*
import java.security.AccessController.getContext
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
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + produto.id + ".jpg?alt=media").into(
                containerView.image
            )

            containerView.setOnClickListener {

                val activity = itemView.context as ListaProdutoActivity
                val i= Intent(activity, VisualizarProduto::class.java)

                i.putExtra("title", produto.nome.toString())
                i.putExtra("desc", produto.desc.toString())
                i.putExtra("id", produto.id.toString())
                i.putExtra("estoque", produto.estoque.toString())
                i.putExtra("preco", produto.preco.toString())


                activity.startActivity(i)
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