package com.example.voxvalleyapp.View

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.voxvalleyapp.AdapterClass.ItemAdapter
import com.example.voxvalleyapp.AdapterClass.ProductAdapter
import com.example.voxvalleyapp.R
import com.example.voxvalleyapp.model.Item
import com.example.voxvalleyapp.model.Product
import com.example.voxvalleyapp.model.ProductData
import com.example.voxvalleyapp.databinding.ActivityMainBinding
import com.example.voxvalleyapp.viewmodel.ProductViewModel
import com.google.gson.reflect.TypeToken
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var list:List<Product>
    private lateinit var adapter:ProductAdapter
    private lateinit var binding:ActivityMainBinding
    private lateinit var  viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.products.observe(this, Observer { products ->
            binding.progressBarAll.visibility=(View.GONE)
            adapter.updateProductList(products)
        })
        viewModel.loadProducts()
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this@MainActivity, AddproductActivity::class.java))
        }
        list=ArrayList()
        adapter = ProductAdapter(this,list)
        binding.recyclerViewProduct.adapter = adapter
        binding.recyclerViewProduct.setLayoutManager(GridLayoutManager(this@MainActivity, 2))
        setDrawable()
        loadBanner()
        loadCategory()
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //visitorsAdapter.filterList(s.toString());
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
    private fun loadCategory() {
        val items = listOf(
            Item(R.drawable.img_1, "Nike"),
            Item(R.drawable.img_2, "Puma"),
            Item(R.drawable.img_3, "Puma"),
            Item(R.drawable.img_4, "Gucci"),
            Item(R.drawable.img_3, "Zara"),
        )

        // Set up the adapter and RecyclerView
        val adapter = ItemAdapter(items)
        binding.recyclerViewOffical.adapter = adapter
        binding.recyclerViewOffical.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
    private fun loadBanner() {
        val slideModels: ArrayList<SlideModel> = ArrayList<SlideModel>()
        slideModels.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        slideModels.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        binding.imgSlider.setImageList(slideModels, ScaleTypes.FIT)
    }
    private fun setDrawable() {
        val drawable = ContextCompat.getDrawable(this, R.drawable.search_icon)
        drawable?.setBounds(0, 0, 50, 50)
        binding.editTextText.setCompoundDrawables(drawable, null, null, null)
    }
    fun filter(text: String) {
        val filteredList: ArrayList<Product> = ArrayList<Product>()
        viewModel.products.observe(this, Observer { products ->
            val lowerCaseText = text.lowercase(Locale.getDefault())
            for (user in products) {
                if (user.title.toLowerCase().contains(lowerCaseText)) {
                    filteredList.add(user)
                }
            }
        })
        adapter.updateProductList(filteredList)


    }
}


