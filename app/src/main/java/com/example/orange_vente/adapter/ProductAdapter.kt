package com.example.orange_vente.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orange_vente.MainActivity
import com.example.orange_vente.Model.ProductModel
import com.example.orange_vente.ProductPopup
import com.example.orange_vente.R
import com.example.orange_vente.Repository

class ProductAdapter (
    val context: MainActivity,
    private val productList : List<ProductModel>
        ): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    //boite pour ranger tous les composants a controller
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        //images du produit

        val image2 = view.findViewById<ImageView>(R.id.product2)
        val productName : TextView? = view.findViewById(R.id.product_name)
        val productDescription :TextView? = view.findViewById(R.id.product_description)
        val productPrice = view.findViewById<TextView>(R.id.product_price)
        val productLiked = view.findViewById<ImageView>(R.id.liked)
        val productPromotion = view.findViewById<TextView>(R.id.percent)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.produits,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuperer les informations des produits
        val currentProduct = productList[position]

        //recuperer le repository
        val repo = Repository()
        //utiliser glide pour recuperer l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentProduct.image)).into(holder.image2)

        //mettre a jour le nom de la plante
        holder.productName?.text = currentProduct.nom

        //mettre a jour la promotion

        holder.productPromotion.text = currentProduct.promotion
        //mettre a jour la description

        holder.productDescription?.text = currentProduct.description

        //mettre a jour le prix
        holder.productPrice.text = currentProduct.prix.toString()

        //verifier si liked ou no

        if(currentProduct.liked)
        {
            holder.productLiked.setImageResource(R.drawable.ic_baseline_star_24)
        }
        else
        {
            holder.productLiked.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
        //rajouter une interaction sur cette etoile
        holder.productLiked.setOnClickListener {
            //inverser si le bouton est like ou non
            currentProduct.liked = !currentProduct.liked

            //mettre a jour l'objet produit
            repo.updateProduct(currentProduct)
        }

        //une nouvelle interaction lors du clique sur un produit

        holder.itemView.setOnClickListener {
            //afficher la popup

            ProductPopup(this,currentProduct).show()
        }

    }

    override fun getItemCount(): Int {
       return productList.size
    }


}