package com.example.orange_vente

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.orange_vente.Model.ProductModel
import com.example.orange_vente.adapter.ProductAdapter

class ProductPopup(private val adapter: ProductAdapter,
                   private val currentProduct: ProductModel
) : Dialog(adapter.context) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.product_details)

        setupComponents()
        setupCloseButton()
        //setupDeleteButton()
        setupStarButton()
    }
    private fun updateStar(button: ImageView)
    {
        if (currentProduct.liked)
        {
            button.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
        else {
            button.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    private fun setupStarButton() {
      //recuperer
        val starButton = findViewById<ImageView>(R.id.star)
        updateStar(starButton)
        //interaction
        starButton.setOnClickListener {
            currentProduct.liked = !currentProduct.liked
            val repo = Repository()
            repo.updateProduct(currentProduct)
            updateStar(starButton)
        }
    }

    //private fun setupDeleteButton() {
        //findViewById<ImageView>(R.id.).setOnClickListener {
        //    //supprimer la plante de la base de donnees
          //  val repo = Repository()
           // repo.deleteProduct(currentProduct)
    //dismiss()
     //   }
    //}

    private fun setupComponents() {
        //actualiser l'image du produit
        val productImage = findViewById<ImageView>(R.id.image_live)
        Glide.with(adapter.context).load(Uri.parse(currentProduct.image)).into(productImage)
        //actualiser le nom de la plante
        findViewById<TextView>(R.id.name).text = currentProduct.nom
        //actualiser la description

        findViewById<TextView>(R.id.descript).text = currentProduct.description
        //actualiser le prix
        findViewById<TextView>(R.id.prix).text = currentProduct.prix.toString()
        //actualiser la promotion
        findViewById<TextView>(R.id.promotion).text =currentProduct.promotion
    }
   private fun setupCloseButton(){
        findViewById<ImageView>(R.id.close).setOnClickListener {
            //fermer la fenetre popup
            dismiss()
        }
    }
}