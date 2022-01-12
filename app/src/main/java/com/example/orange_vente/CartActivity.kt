package com.example.orange_vente

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.orange_vente.EventBus.UpdateCartEvent
import com.example.orange_vente.Listener.ICartLoadListener
import com.example.orange_vente.Listener.IProductLoadListener
import com.example.orange_vente.Model.CartModel
import com.example.orange_vente.Model.ProductModel
import com.example.orange_vente.adapter.MyProductAdapter
import com.example.orange_vente.adapter.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CartActivity : AppCompatActivity(), IProductLoadListener ,ICartLoadListener{
    lateinit var productLoadListener: IProductLoadListener
    lateinit var cartLoadListener: ICartLoadListener

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault().unregister(this)
    }
     @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
      fun onUpdateCartEvent(event:UpdateCartEvent)
     {
         countCartFromFirebase()
     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        init()
        loadProductFromFirebase()
        countCartFromFirebase()

    }

    private fun countCartFromFirebase() {
        val cartModels : MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")
             .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (cartSnapshot in snapshot.children)
                    {
                        val cartModel = cartSnapshot.getValue(CartModel::class.java)
                        cartModel!!.id = cartSnapshot.key
                        cartModels.add(cartModel)
                    }
                    cartLoadListener.onLoadCartSuccess(cartModels)
                }

                override fun onCancelled(error: DatabaseError) {
                    cartLoadListener.onLoadCartFailed(error.message)
                }

            })
    }

    private fun loadProductFromFirebase() {
        val productModels: MutableList<ProductModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("produits")
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                       for (productSnapshot in snapshot.children)
                       {
                           val productModel = productSnapshot.getValue(ProductModel::class.java)
                           productModel!!.id = productSnapshot.key.toString()
                           productModels.add(productModel)
                       }
                        productLoadListener.onProductLoadSuccess(productModels)
                    }
                    else
                        productLoadListener.onProductLoadFailed("Product item not exists")

                }

                override fun onCancelled(error: DatabaseError) {
                    productLoadListener.onProductLoadFailed(error.message)
                }

            })
    }

    private fun init(){
        productLoadListener = this
        cartLoadListener = this

        val gridLayoutManager = GridLayoutManager(this,2)
        recycler_drink.layoutManager = gridLayoutManager
        recycler_drink.addItemDecoration( SpaceItemDecoration())

        btnCart.setOnClickListener { startActivity(Intent(this,CartActivity2::class.java))
        }

    }

    override fun onProductLoadSuccess(productModelList: List<ProductModel>?) {
        val adapter = MyProductAdapter(this,productModelList!!,cartLoadListener)
        recycler_drink.adapter = adapter

    }
    override fun onProductLoadFailed(message: String?) {
        Snackbar.make(mainLayout,message!!,Snackbar.LENGTH_LONG).show()

    }

    override fun onLoadCartSuccess(cartModelList: List<CartModel>) {
        var cartSum = 0
        for (cartModel in cartModelList!!) cartSum +=cartModel.quantity
        badge!!.setNumber(cartSum)
    }

    override fun onLoadCartFailed(message: String?) {
        Snackbar.make(mainlayout,message!!,Snackbar.LENGTH_LONG).show()
    }
}


