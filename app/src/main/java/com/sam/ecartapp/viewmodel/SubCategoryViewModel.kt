package com.sam.ecartapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.remote.RetrofitBuilder
import com.sam.ecartapp.model.SubCategoryListResponse
import com.sam.ecartapp.model.SubCategoryResponse
import com.sam.ecartapp.view.productlist.ProductListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryViewModel(application: Application): AndroidViewModel(application) {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val _subCategoryResponse = MutableLiveData<SubCategoryResponse>()
    val subCategoryResponse:LiveData<SubCategoryResponse> = _subCategoryResponse

    private val _subCategoryListResponse = MutableLiveData<SubCategoryListResponse>()
    val subCategoryListResponse:LiveData<SubCategoryListResponse> = _subCategoryListResponse

    private val _listOfFragments = MutableLiveData<MutableList<ProductListFragment>>()
    val listOfFragment:LiveData<MutableList<ProductListFragment>> = _listOfFragments


    fun getSubCategory(id:String){
        apiService.getSubCategory(id).enqueue(object: Callback<SubCategoryResponse> {
            override fun onResponse(
                call: Call<SubCategoryResponse>,
                response: Response<SubCategoryResponse>
            ) {
                if(response.isSuccessful){
                    _subCategoryResponse.value = response.body()
                }else{
                    Log.i("tag","Sub Category Get Failed")
                }
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                Log.i("tag","Sub Category Request Sending Failed")
            }

        })
    }

    fun getListOfSubCategory(id:String){
        apiService.getListOfSubCategories(id).enqueue(object : Callback<SubCategoryListResponse>{
            override fun onResponse(
                call: Call<SubCategoryListResponse>,
                response: Response<SubCategoryListResponse>
            ) {
                if(response.isSuccessful){
                    _subCategoryListResponse.value = response.body()
                }else{
                    Log.i("tag","Sub Category List Get Failed")
                }
            }

            override fun onFailure(call: Call<SubCategoryListResponse>, t: Throwable) {
                Log.i("tag","Sub Category List Request Sending Failed")

            }

        })
    }

/*    fun generateFragments(products: SubCategoryListResponse) {
        val fragmentList = mutableListOf<ProductListFragment>()
        val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
        var count = 0
        for(i in products.subcategories){
            Log.i("***************************","${i} enqued")
            apiService.getSubCategory(i.subcategoryId).enqueue(object:
                Callback<SubCategoryResponse> {
                override fun onResponse(
                    call: Call<SubCategoryResponse>,
                    response: Response<SubCategoryResponse>
                ) {
                    count++
                    if(response.isSuccessful) {
                        fragmentList.add(ProductListFragment(response.body()!!.products))

                        if (count == products.subcategories.size) {
                            //onSubCategoryListResponseReceived(products)
                            _listOfFragments.value = fragmentList
                        }
                    }
                }

                override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                    Log.i("tag", "Failed")
                }
            })
        }
    }*/
fun generateFragments(products: SubCategoryListResponse) {
    val fragmentList = mutableListOf<ProductListFragment>()
    val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)

    // Start processing the first subcategory
    processNextSubCategory(apiService, products, fragmentList, 0)
}

    private fun processNextSubCategory(
        apiService: ApiService,
        products: SubCategoryListResponse,
        fragmentList: MutableList<ProductListFragment>,
        currentIndex: Int
    ) {
        if (currentIndex >= products.subcategories.size) {
            _listOfFragments.value = fragmentList
            return
        }

        val subcategory = products.subcategories[currentIndex]
        Log.i("tag", "${subcategory.subcategoryId} enqueued")

        apiService.getSubCategory(subcategory.subcategoryId).enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(
                call: Call<SubCategoryResponse>,
                response: Response<SubCategoryResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { subCategoryResponse ->
                        fragmentList.add(ProductListFragment(subCategoryResponse.products))
                    }
                }
                // Process the next subcategory
                processNextSubCategory(apiService, products, fragmentList, currentIndex + 1)
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                Log.i("tag", "Failed to fetch subcategory ${subcategory.subcategoryId}")
                // Even on failure, process the next subcategory
                processNextSubCategory(apiService, products, fragmentList, currentIndex + 1)
            }
        })
    }


}