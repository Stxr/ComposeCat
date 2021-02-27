/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    var cat: CatElement? = null
}
