package com.example.orange_vente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.orange_vente.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            //importer la bottomnavigationview

             val navigattionview = findViewById<BottomNavigationView>(R.id.design_navigation_view)
           navigattionview.setOnNavigationItemSelectedListener {
             when(it.itemId)
                {
                     R.id.homeFragment -> {
                         loadFragment(HomeFragment(this))
                         return@setOnNavigationItemSelectedListener true

                     }
                     R.id.cartFragment ->{
                         loadFragment(CartFragment(this))
                         return@setOnNavigationItemSelectedListener true
                     }
                     R.id.ordersFragment ->{
                         loadFragment(OrdersFragment(this))
                         return@setOnNavigationItemSelectedListener true
                     }
                     R.id.ordersFragment ->{
                         loadFragment(AccountFragment(this))
                         return@setOnNavigationItemSelectedListener true
                     }
                     else ->false

                 }
         }
        loadFragment(HomeFragment(this))
    }

      //

    private fun loadFragment(fragment: Fragment) {
        //charger notre Repository

        val repo = Repository()

        //mettre a jour la liste de produit

        repo.updateData {

            //injecter
            val transaction =supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout,HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()

        }

    }
}

