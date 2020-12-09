package com.hjry.loja.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.hjry.loja.R
import com.hjry.loja.activity.CarrinhoActivity
import com.hjry.loja.model.Produto
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cartao_lista_carrinho.view.*

import java.text.NumberFormat

class CarrinhoRecyclerViewAdapter (options: FirebaseRecyclerOptions<Produto>) :
    FirebaseRecyclerAdapter<Produto, CarrinhoRecyclerViewAdapter.CarrinhoViewHolder>(options) {

    class CarrinhoViewHolder(
        override val containerView: View,
    ) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        val formatter = NumberFormat.getCurrencyInstance()

        fun bind(produto: Produto) {

            containerView.txtNomeCarrinho.text = produto.nome
            containerView.txtDescCarrinho.text = produto.desc
            containerView.txtPrecoCarrinho.text = formatter.format(produto.preco)
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/projetopdm-3d833.appspot.com/o/photos%2F" + produto.id + ".jpg?alt=media").into(
                containerView.imageCarrinho
            )

            containerView.setOnClickListener {

                val activity = itemView.context as CarrinhoActivity
                val i= Intent(activity, CarrinhoActivity::class.java)

                i.putExtra("title", produto.nome.toString())
                i.putExtra("desc", produto.desc.toString())
                i.putExtra("id", produto.id.toString())
                i.putExtra("estoque", produto.estoque.toString())
                i.putExtra("preco", produto.preco.toString())


                activity.startActivity(i)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardItem = inflater.inflate(R.layout.cartao_lista_carrinho, parent, false)
        return CarrinhoViewHolder(cardItem)
    }

    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int, produto: Produto) {
        holder.bind(produto)
    }
}