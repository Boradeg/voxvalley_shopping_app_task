package com.example.voxvalleyapp.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.voxvalleyapp.databinding.ActivityAddproductBinding
import com.example.voxvalleyapp.viewmodel.ProductViewModel

class AddproductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddproductBinding
    private lateinit var viewModel: ProductViewModel
    var flag=0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddproductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.materialToolbar.setNavigationOnClickListener { finish() }
        val title = intent.getStringExtra("title")
        val category = intent.getStringExtra("category")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val id = intent.getStringExtra("id")
        binding.prodTitle.setText(title)
        binding.productDesc.setText(description)
        binding.productPrice.setText(price)
        binding.productCategory.setText(category)
        if(!binding.prodTitle.text!!.isEmpty()){
            flag=1
            binding.SubmitBtn.text="Update Product"
            binding.materialToolbar.title="Update Product"
        }
        viewModel= ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.addResult.observe(this, Observer {
            Log.d("result",it.toString())
            Toast.makeText(this, "add product successfully.Response : $it", Toast.LENGTH_SHORT).show()
        })
        viewModel.updateResult.observe(this, Observer {
            Log.d("result",it.toString())
            Toast.makeText(this, "Update product successfully.Response : $it", Toast.LENGTH_SHORT).show()
        })
        binding.SubmitBtn.setOnClickListener {
            validation(id)
        }
    }

    private fun validation(id: String?) {
        if (!binding.prodTitle.text.toString().isEmpty()) {

            if (!binding.productCategory.text.toString().isEmpty()) {


                if (!binding.productDesc.text.toString().isEmpty()) {

                    if (!binding.productPrice.text.toString().isEmpty()) {
                        if(flag==1){
                            viewModel.updateProduct(id.toString(),binding.prodTitle.text.toString(),binding.productDesc.text.toString(),binding.productPrice.text.toString(),binding.productCategory.text.toString())
                        }else{
                            viewModel.addProduct(binding.prodTitle.text.toString(),binding.productDesc.text.toString(),binding.productPrice.text.toString(),binding.productCategory.text.toString())
                        }

                    } else {
                        binding.productPrice.error = "Add Price"
                    }

                } else {
                    binding.productDesc.error = "Add Description"

                }

            } else {
                binding.productCategory.error = "Add Category"
            }
        } else {
            binding.prodTitle.error = "Add Title"

        }

    }



}