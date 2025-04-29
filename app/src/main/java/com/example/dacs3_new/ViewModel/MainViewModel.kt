package com.example.dacs3_new.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dacs3_new.Model.CategoryModel
import com.example.dacs3_new.Model.FoodModel
import com.example.dacs3_new.Repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadCategory(): LiveData<MutableList<CategoryModel>>{
        return repository.loadCategory()
    }

    fun loadPopular(): LiveData<MutableList<FoodModel>>{
        return repository.loadPopular()
    }
}