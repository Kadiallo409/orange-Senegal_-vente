package com.example.orange_vente

import com.example.orange_vente.Model.ProductModel
import com.example.orange_vente.Repository.singleton.databaseRef
import com.example.orange_vente.Repository.singleton.productList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.security.auth.callback.Callback

class Repository {

        object singleton {
            //se connecter a la base de donnes

            val databaseRef = FirebaseDatabase.getInstance().getReference("produits")
            //creer une liste qui va contenir nos produits
            val productList = arrayListOf<ProductModel>()
        }

        fun updateData(callback : () -> Unit) {
            //absorber les donnees depuis la base de donnees

            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //retirer les anciennes

                    productList.clear()

                    //recolter la liste
                    for (ds in snapshot.children) {
                        //construire un objet produit

                        val produit = ds.getValue(ProductModel::class.java)

                        //verifier que le produit n'est pas null
                        if (produit != null) {
                            //ajouter la produit a notre liste
                            productList.add(produit)

                        }

                    }
                    //actioner le callback
                    callback()
                }


                override fun onCancelled(error: DatabaseError) {
                    //au cas ou il ne trouve les donnees
                }

            })
        }

        fun updateProduct(produit: ProductModel) {
            databaseRef.child(produit.id).setValue(produit)
        }

    //methode pour supprimer une plante de la base de donnees

    fun deleteProduct(produit: ProductModel) = databaseRef.child(produit.id).removeValue()


    }
