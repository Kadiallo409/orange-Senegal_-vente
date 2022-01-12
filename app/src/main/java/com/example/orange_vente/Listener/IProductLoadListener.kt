package com.example.orange_vente.Listener

import com.example.orange_vente.Model.ProductModel

interface  IProductLoadListener {

    fun onProductLoadSuccess(productModelList:List<ProductModel>?)
    fun onProductLoadFailed(message:String?)


}