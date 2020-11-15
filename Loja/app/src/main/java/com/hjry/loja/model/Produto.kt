package com.hjry.loja.model

data class Produto(
    var id: String? = null,
    var nome: String,
    var desc: String? = null,
    var img: String? = null,
    var estoque: Int,
    var preco: Double,
    var desconto: Int? = null

) {
    constructor() : this(
        nome = "",
        estoque = 0,
        preco = 0.0,
    )
}