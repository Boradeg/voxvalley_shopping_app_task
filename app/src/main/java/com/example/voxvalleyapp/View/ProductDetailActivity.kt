package com.example.voxvalleyapp.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.voxvalleyapp.databinding.ActivityProductDetailBinding
import com.example.voxvalleyapp.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProductDetailBinding
    private lateinit var  viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
         var title = intent.getStringExtra("title")
         val category = intent.getStringExtra("category")
        val brand = intent.getStringExtra("brand")
         val price = intent.getDoubleExtra("price", 0.0).toString()
        val availabilityStatus = intent.getStringExtra("availabilityStatus")
        val discountPercentage = intent.getFloatExtra("discountPercentage", 0.0f)
         val description = intent.getStringExtra("description")
        val minimumOrderQuantity = intent.getIntExtra("minimumOrderQuantity", 1)
        val rating = intent.getStringExtra("rating")
        val id = intent.getStringExtra("id")
//        val returnPolicy = intent.getStringExtra("returnPolicy", 0.0f)
//        val thumbnail = intent.getStringExtra("thumbnail", 0.0f)
//        val stock = intent.getFloatExtra("stock", 0.0f)
//        val warrantyInformation = intent.getFloatExtra("warrantyInformation", 0.0f)
//        val weight = intent.getFloatExtra("weight", 0.0f)
        val images = intent.getStringExtra("images")
        binding.titleTxt.text=title
        binding.priceTxt.text="$"+price.toString()
        binding.categoryTxt.text=category
        binding.brandTxt.text=brand
        binding.descriptionTxt.text=description
        binding.ratingTxt.text=rating.toString()+" Rating"
        binding.ratingBar.rating = rating.toString().toFloat()
        Picasso.get()
            .load(images)
            .resize(800, 600) // Resize to reduce memory usage
            .centerInside()
            .into(binding.viewpagerSlider)
       // Toast.makeText(this, ""+title, Toast.LENGTH_SHORT).show()
        binding.editBtn.setOnClickListener {
            startActivity(Intent(this@ProductDetailActivity, AddproductActivity::class.java)
                .putExtra("title",title)
                .putExtra("price",price.toString())
                .putExtra("description",description)
                .putExtra("category",category)
                .putExtra("id",id)
            )
        }
        viewModel= ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.deleteResult.observe(this, Observer {
            Log.d("result",it.toString())
            Toast.makeText(this, "delete product successfully.Response : $it", Toast.LENGTH_SHORT).show()
        })
        binding.deleteBtn.setOnClickListener {

                val builder = AlertDialog.Builder(this) // Create an AlertDialog builder
                    .setTitle("Alert!") // Set the dialog title
                    .setMessage("Do you want to delete this product.") // Set the dialog message
                    .setPositiveButton("Yes") { dialog, which -> // Set a positive button
                        viewModel.deleteProduct(id.toString())
                        dialog.dismiss() // Dismiss the dialog when clicked
                    }.setNegativeButton("No"){ dialog ,which ->
                        dialog.dismiss()
                    }

                builder.create().show() // Create and show the dialog

        }

    }

    fun backButton(view: View) {
        finish()
    }


}