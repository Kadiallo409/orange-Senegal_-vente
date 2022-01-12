package com.example.orange_vente.Listener

import com.example.orange_vente.Model.CartModel

interface ICartLoadListener {

    fun onLoadCartSuccess(cartModelList: List<CartModel>)
    fun onLoadCartFailed(message: String?)

}