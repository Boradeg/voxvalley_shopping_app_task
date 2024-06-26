package com.example.voxvalleyapp.repository

// repository/ProductRepository.kt
import android.util.Log
import android.widget.Toast
import com.example.voxvalleyapp.model.Product
import com.example.voxvalleyapp.model.ProductData
import com.google.gson.reflect.TypeToken
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository(private val context: android.content.Context) {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts() {
        val url = "https://dummyjson.com/products"
        Ion.with(context)
            .load(url)
            .`as`(object : TypeToken<ProductData>() {})
            .withResponse()
            .setCallback(object : FutureCallback<Response<ProductData>> {
                override fun onCompleted(e: Exception?, result: Response<ProductData>?) {

                    val httpStatusCode = result?.headers?.code() ?: return
                    if (httpStatusCode == 200) {
                        _products.postValue(result.result.products)
                    } else {
                        _products.postValue(emptyList())
                    }
                }
            })
    }
    fun deleteProduct(productId: String, callback: (Result<String>) -> Unit) {
        val url = "https://dummyjson.com/products/$productId"

        Ion.with(context)
            .load("DELETE", url)
            .asString()
            .setCallback { e, result ->
                if (e != null) {
                    callback(Result.failure(e))
                } else {
                    callback(Result.success(result))
                }
            }
    }
    fun addProduct(title:String, desc:String, price:String,category:String,callback: (Result<String>) -> Unit) {
        val jsonObject = JsonObject().apply {
            addProperty("title", title)
            addProperty("description",desc)
            addProperty("price",price)
            addProperty("category", category)
        }
        var url = "https://dummyjson.com/products/add"
        Ion.with(context).load("POST", url).setHeader("Content-Type", "application/json")
            .setJsonObjectBody(jsonObject).asString().setCallback { e, result ->
                if (e != null) {
                    callback(Result.failure(e))
                } else {
                    callback(Result.success(result))
                }
            }
    }
     fun updateProduct(productId: String,title:String, desc:String, price:String,category:String,callback: (Result<String>) -> Unit) {
        val url = "https://dummyjson.com/products/$productId"

        val jsonBody = JsonObject().apply {
            addProperty("title", title)
            addProperty("description", desc)
            addProperty("price",price)
            addProperty("category", category)
        }

        Ion.with(context)
            .load("PUT", url)
            .setJsonObjectBody(jsonBody)
            .asJsonObject()
            .withResponse()
            .setCallback(object : FutureCallback<Response<JsonObject>> {
                override fun onCompleted(e: Exception?, result: Response<JsonObject>?) {
                    if (e != null) {
                       callback(Result.failure(e))
                    }
                    else{
                        callback(Result.success(result?.result.toString()))
                    }


                }
            })
    }

}
