package com.example.androiddevchallenge.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rxhttp.toList
import rxhttp.wrapper.param.RxHttp

class CatListViewModel : ViewModel() {

    suspend fun getCardList() = withContext(Dispatchers.IO) {
        RxHttp.get("v1/images/search?size=full&mime_types=jpg&format=json&has_breeds=true&page=0&limit=20")
            .toList<CatElement>()
            .await()
    }
    var cat:CatElement?=null
}