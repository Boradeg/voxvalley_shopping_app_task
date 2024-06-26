package com.example.voxvalleyapp.AdapterClass

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.voxvalleyapp.R
import com.example.voxvalleyapp.View.ProductDetailActivity
import com.example.voxvalleyapp.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(var context: Context, private var items: List<Product>) : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_products, parent, false)
        return ItemViewHolder(view)
    }
    // Method to update the list of products
    fun updateProductList(newProductList: List<Product>) {
        items = newProductList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.titleView.text = item.title
        holder.ratingTxt.text = item.rating.toString()
        holder.priceTxt.text = "$"+item.price.toString()
        holder.discountText.text = item.discountPercentage.toString()+"% Off"
        holder.ratingBar.rating = item.rating.toString().toFloat()
        val IMAGE_URL: String = "${item.images[0].toString()}"
        Picasso.get()
            .load(IMAGE_URL)
            .resize(800, 600) // Resize to reduce memory usage
            .centerInside()
            .into(holder.productImage)

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, ProductDetailActivity::class.java)
                .putExtra("title",item.title)
                .putExtra("id",item.id.toString())
                .putExtra("category",item.category)
                .putExtra("brand",item.brand)
                .putExtra("price",item.price)
                .putExtra("availabilityStatus",item.availabilityStatus)
                .putExtra("discountPercentage",item.discountPercentage)
                .putExtra("description",item.description)
                .putExtra("minimumOrderQuantity",item.minimumOrderQuantity)
                .putExtra("rating",item.rating.toString())
                .putExtra("returnPolicy",item.returnPolicy)
                .putExtra("thumbnail",item.thumbnail)
                .putExtra("stock",item.stock)
                .putExtra("warrantyInformation",item.warrantyInformation)
                .putExtra("weight",item.weight)
                .putExtra("images",item.images[0])
            )
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.titleTxt)
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val ratingTxt: TextView = view.findViewById(R.id.ratingTxt)
        val priceTxt: TextView = view.findViewById(R.id.priceTxt)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val discountText: TextView = view.findViewById(R.id.discountText)
    }
}