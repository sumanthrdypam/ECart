package com.sam.ecartapp.view.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.databinding.CategoryItemViewBinding
import com.sam.ecartapp.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter(val context: Context, val categories:List<Category>) :Adapter<CategoryViewHolder>() {
    private lateinit var navigator:(String,String)->Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemViewBinding.inflate(layoutInflater,parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder.item){
            categoryName.text = categories[position].categoryName
            Picasso.get().load("${AppConstants.BASE_IMAGE_URL}${categories[position].categoryImageUrl}").into(categoryImgview)
            root.setOnClickListener {
                navigator(categories[position].categoryId, categories[position].categoryName)
            }
        }
    }

    fun setNavigator(navigator:(String,String)->Unit ){
        this.navigator= navigator
    }

}