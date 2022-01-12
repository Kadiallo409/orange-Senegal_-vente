package com.example.orange_vente.Model

class ProductModel(
    var id :String = "product0",
    val nom : String = "Livebox",
    val description: String ="Petite description",
    val image : String = "https://dimelo-answers-production.s3-eu-west-1.amazonaws.com/268/943d9f7e9171dd25/modem_fun_box_orange_s_n_gal_comment_g_rer_les_quipements_qui_se_connectent_sur_mon_w_original.jpg?109f72e",
    var liked : Boolean = false,
    var promotion : String = "25%",
    val prix : Int = 25000

)