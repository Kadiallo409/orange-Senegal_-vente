package com.example.orange_vente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.orange_vente.Repository.singleton.productList
import com.example.orange_vente.adapter.ProductAdapter
import com.example.orange_vente.adapter.productItemDecoration


class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        //creer une liste qui va stocker ces produits

        //val productList = arrayListOf<ProductModel>()


        //enregistrer une premiere produit dans notre liste

       // productList.add(
         //   ProductModel(
           //     "Livebox 1",
             //   "les premiers liveboxs",
               // "https://cdn.woopic.com/v1/AUTH_c10f167280f2414abb346a5347e1ecd9/prod/binaries/images/3563632-livebox-sagem_screenshot.jpg",
                //true,
                //"25%",
                //25000

        //recuperer le recyclerview
        val recyclerview = view.findViewById<RecyclerView>(R.id.recycler1)
        recyclerview.adapter = ProductAdapter(context,productList)
        recyclerview.addItemDecoration(productItemDecoration())
        return view
    }
}