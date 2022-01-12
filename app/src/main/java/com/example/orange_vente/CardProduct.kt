package com.example.orange_vente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orange_vente.R
import com.example.orange_vente.Repository.singleton.productList
import com.example.orange_vente.adapter.ProductAdapter
import com.example.orange_vente.adapter.productItemDecoration

class CardProduct ( private val context: MainActivity): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater?.inflate(R.layout.card,container,false)
        //recuperer le recyclerview
        val cardRecyclerview = view.findViewById<RecyclerView>(R.id.recycler2)
        cardRecyclerview.adapter =ProductAdapter(context, productList)
        cardRecyclerview.layoutManager = LinearLayoutManager(context)
        cardRecyclerview.addItemDecoration(productItemDecoration())
        return view
    }
}