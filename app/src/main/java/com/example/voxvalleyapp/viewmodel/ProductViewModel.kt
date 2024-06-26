package com.example.voxvalleyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.voxvalleyapp.model.Product
import com.example.voxvalleyapp.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepository(application)
    val products: LiveData<List<Product>> = repository.products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

     fun loadProducts() {
         viewModelScope.launch {
             repository.fetchProducts()
         }

    }

    private val _deleteResult = MutableLiveData<Result<String>>()
    val deleteResult: LiveData<Result<String>> get() = _deleteResult

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            repository.deleteProduct(productId) {
                _deleteResult.postValue(it)
            }
        }

    }
    private val _addResult = MutableLiveData<Result<String>>()
    val addResult: LiveData<Result<String>> get() = _addResult

    fun addProduct(title:String, desc:String, price:String,category:String) {
        viewModelScope.launch {
            repository.addProduct(title, desc, price,category) {
                _addResult.postValue(it)
            }
        }

    }

    private val _updateResult = MutableLiveData<Result<String>>()
    val updateResult: LiveData<Result<String>> get() = _updateResult

    fun updateProduct(
        id: String,
        title: String,
        desc: String,
        price: String,
        category: String
    ) {
        viewModelScope.launch {
            repository.updateProduct(id,title,desc,price,category) {
                _updateResult.postValue(it)
            }
        }

    }
}

