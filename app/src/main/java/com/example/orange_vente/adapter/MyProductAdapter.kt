package com.example.orange_vente.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orange_vente.EventBus.UpdateCartEvent
import com.example.orange_vente.Listener.ICartLoadListener
import com.example.orange_vente.Listener.IRecyclerClickListener
import com.example.orange_vente.Model.CartModel
import com.example.orange_vente.Model.ProductModel
import com.example.orange_vente.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus

class MyProductAdapter(private val context: Context,
                       private val list:List<ProductModel>,
                       private val cartListener: ICartLoadListener
): RecyclerView.Adapter<MyProductAdapter.MyProductAdapterViewHolder>(){

    class MyProductAdapterViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var imageView :ImageView?=null
        var txtName:TextView?=null
        var txtPrice: TextView?=null

        private var clickListener: IRecyclerClickListener? = null
        fun setClickListener(clickListener: IRecyclerClickListener)
        {
            this.clickListener =clickListener
        }

        init{
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            txtName = itemView.findViewById(R.id.txtName) as TextView
            txtPrice = itemView.findViewById(R.id.txtPrice) as TextView

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           clickListener?.onItemClickListener(v,adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductAdapterViewHolder {
        return MyProductAdapterViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layouy_product_item,parent,false))
    }

     override fun onBindViewHolder(holder: MyProductAdapterViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].image)
            .into(holder.imageView!!)
        holder.txtName!!.text = StringBuilder().append(list[position].nom)
        holder.txtPrice!!.text = StringBuilder().append(list[position].prix)

         holder.setClickListener(object:IRecyclerClickListener{
             override fun onItemClickListener(view: View?, podition: Int) {
                addToCart(list[position])
             }

         })

     }

    private fun addToCart(productModel: ProductModel) {
        val userCart = FirebaseDatabase.getInstance()
            .getReference("Cart")
            .child("UNIQUE_USER_ID")

        userCart.child(productModel.id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        val cartModel = snapshot.getValue(CartModel::class.java)
                        val updateData: MutableMap<String,Any> = HashMap()
                        cartModel!!.quantity = cartModel!!.quantity +1
                        updateData["quantity"] = cartModel!!.quantity + 1
                         updateData["totalPrice"] = cartModel!!.quantity * cartModel.price!!.toFloat()
                        userCart.child(productModel.id)
                            .updateChildren(updateData )
                            .addOnSuccessListener {
                                EventBus.getDefault().postSticky(UpdateCartEvent())
                                cartListener.onLoadCartFailed("Succes add To Cart")
                            }
                            .addOnFailureListener { e ->   cartListener.onLoadCartFailed(e.message)


                            }
                    }
                    else
                    {
                        val cartModel = CartModel()
                        cartModel.id = productModel.id
                        cartModel.name = productModel.nom
                        cartModel.image = productModel.image
                        cartModel.price = productModel.prix.toString()
                        cartModel.quantity = 1
                        cartModel.totalPrice = productModel.prix.toFloat()


                        userCart.child(productModel.id)
                            .setValue(cartModel)
                            .addOnSuccessListener {
                                EventBus.getDefault().postSticky(UpdateCartEvent())
                                cartListener.onLoadCartFailed("Success add To Cart")
                            }
                            .addOnFailureListener { e ->   cartListener.onLoadCartFailed(e.message)


                            }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                   cartListener.onLoadCartFailed(error.message)
                }

            })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}