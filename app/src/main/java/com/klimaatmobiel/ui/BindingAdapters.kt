package com.klimaatmobiel.ui

import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.projecten3android.R
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.adapters.ProductListAdapter


@BindingAdapter("listDataProducts")
fun listDataProductsBinding(recyclerView: RecyclerView, data: List<Product>) {
    val adapter = recyclerView.adapter as ProductListAdapter

    //adapter.convertAndSubmit(data)
}
@BindingAdapter("listDataOrderPreview")
fun listDataOrderPreviewBinding(recyclerView: RecyclerView, data: List<OrderItem>?) {
    val adapter = recyclerView.adapter as OrderPreviewListAdapter
    adapter.submitList(if (data != null) ArrayList(data!!) else null)
}

@BindingAdapter("projectBudgetBinding")
fun projectBudgetBinding(txtView: TextView,  budget: Double) {
    txtView.text = "Winkelmandje - €" + budget.toString() +" budget"
}

@BindingAdapter("groupSpentBinding")
fun groupSpentBinding(txtView: TextView,  totalOrderPrice: Double) {
    txtView.text = "Totaal: €" + totalOrderPrice.toString()
}

@BindingAdapter("productNameBinding")
fun productNameBinding(txtView: TextView, name: String?) {
 txtView.text = name
}

@BindingAdapter("productNameAndCategoryBinding")
fun productNameAndCategoryBinding(txtView: TextView, product: Product?) {
   // Timber.i("${product.productName} (${product.category?.categoryName})")
    if(product != null) txtView.text = "${product.productName} (${product.category?.categoryName})"

}

@BindingAdapter("productPriceBinding")
fun productPriceBinding(txtView: TextView, price: Double) {
    txtView.text = "Prijs: €" + price.toString()
}

@BindingAdapter("projectMaxBudgetBinding")
fun projectMaxBudgetBinding(txtView: TextView, budget: Double) {
    txtView.text = "€" + budget.toString()
}

@BindingAdapter("projectApplicationDomainBinding")
fun projectApplicationDomainBinding(txtView: TextView, domain : String) {
    if(domain == null){
        txtView.text = "Toepassingsgebied: Onbekend"

    }else{
        txtView.text = "Toepassingsgebied: " + domain

    }
}

@BindingAdapter("projectNameBinding")
fun projectNameBinding(txtView: TextView, name: String?) {
    txtView.text = name
}

@BindingAdapter("orderItemAmountBinding")
fun orderItemAmountBinding(txtView: TextView, amount: Int) {
    txtView.text = amount.toString()
}

@BindingAdapter("btnCheckoutOrderBinding")
fun btnCheckoutOrderBinding(btn: Button, totalPrice: Double) {
    btn.text = "Voltooi bestelling van €" + totalPrice.toString()
}

@BindingAdapter("orderItemTotalPriceBinding")
fun orderItemTotalPriceBinding(txtView: TextView, oi: OrderItem) {
    txtView.text = "€" + (oi.amount * oi.product!!.price).toString()
}

@BindingAdapter("orderTotalScoreBinding")
fun orderTotalScoreBinding(parent: LinearLayout, score: Double) {
    parent.removeAllViews()

    val lp20 = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)

    lp20.setMargins(20, 0, 20, 0)

    val aantalDraws = (score/2).toInt()

    var res: Int = 0
    when(aantalDraws) {
        1 -> res = R.drawable.score_bloem_1
        2 -> res = R.drawable.score_bloem_2
        3 -> res = R.drawable.score_bloem_3
        4 -> res = R.drawable.score_bloem_4
        5 -> res = R.drawable.score_bloem_5
    }

    for (i in 1..aantalDraws) {
    val iv = ImageView(parent.context)
    val lp = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)

    lp.setMargins(8, 0, 8, 0)

    iv.setBackgroundResource(res)


    parent.addView(iv, lp)
    }

    for (i in 1..(5-aantalDraws)) {
        val iv = ImageView(parent.context)

        iv.setBackgroundResource(R.drawable.score_dot)

        parent.addView(iv, lp20)
    }
}

@BindingAdapter("klimaatScoreBinding")
fun klimaatScoreBinding(parent: LinearLayout, score: Double) {
    parent.removeAllViews()

    val lp20 = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)

    lp20.setMargins(10, 0, 10, 0)

    val aantalDraws = (score/2).toInt()

    var res: Int = 0
    when(aantalDraws) {
        1 -> res = R.drawable.score_bloem_1
        2 -> res = R.drawable.score_bloem_2
        3 -> res = R.drawable.score_bloem_3
        4 -> res = R.drawable.score_bloem_4
        5 -> res = R.drawable.score_bloem_5
    }

    for (i in 1..aantalDraws) {
        val iv = ImageView(parent.context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        lp.setMargins(8, 0, 8, 0)

        iv.setBackgroundResource(res)
        parent.addView(iv, lp)
    }

    for (i in 1..(5-aantalDraws)) {
        val iv = ImageView(parent.context)

        iv.setBackgroundResource(R.drawable.score_dot)

        parent.addView(iv, lp20)
    }
}

/**
 * Bind the categoryName to the [TextView]
 */
@BindingAdapter("categorieNameBinding")
fun categoryNameBinding(txtView: TextView, name: String) {
    txtView.text = name
}

/**
 * Convert imgUrl to a URI with the https scheme
 * Use Glide to download the image display it in imgView
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image))
            .into(imgView)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: KlimaatMobielApiStatus?) {
    if(status != null) {
        when (status) {
            KlimaatMobielApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }
            KlimaatMobielApiStatus.DONE -> {
                statusImageView.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("orderStatusBinding")
fun bindOrderStatus(orderStatusTextView: TextView, status: Boolean?) {
    val statusText: String
    if(status != null) {
        when(status){
            true -> statusText =  "Ingediend"
            false -> statusText = "Bestelling bezig"
        }

        orderStatusTextView.text = statusText
    }
}