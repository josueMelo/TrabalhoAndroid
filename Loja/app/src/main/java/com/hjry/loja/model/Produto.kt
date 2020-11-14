package com.hjry.loja.model

import java.math.BigDecimal

data class Produto(
    var id: String? = null,
    var nome: String,
    var desc: String? = null,
    var img: String? = null,
    var estoque: Int,
    var preco: BigDecimal,
    var desconto: Int? = null

    )