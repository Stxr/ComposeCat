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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.model.CatElement
import com.example.androiddevchallenge.model.CatListViewModel
import com.example.androiddevchallenge.model.NaviModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.glide.GlideImage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            setContent {
                MyTheme {
                    MyApp()
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    Surface(color = MaterialTheme.colors.background) {
//        CatListPage()
        MyNavigator()
    }
}

@Composable
private fun CatListPage(navi: NavHostController) {
    val rememberCoroutineScope = rememberCoroutineScope()
    var list = mutableStateListOf<CatElement>()

    val model: CatListViewModel = viewModel()
    rememberCoroutineScope.launch {
        if (NaviModel.list == null) {
            NaviModel.list = model.getCardList()
        }
        list.addAll(NaviModel.list!!)
    }
    CatList(list)
}

@Composable
private fun CatDetailPage(navi: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cat Detail")
                },
                navigationIcon = {
                    Icon(
                        painterResource(R.drawable.ic_back),
                        "back",
                        Modifier
                            .padding(16.dp)
                            .clickable {
                                navi.navigateUp()
                            }
                    )
                }
            )
        }
    ) {

        LazyColumn {
            item {
                Card(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        GlideImage(NaviModel.cat!!.url, "")
                        Box(Modifier.padding(16.dp)) {
                            Column {
                                Text(
                                    text = NaviModel.cat!!.breeds[0].name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = NaviModel.cat!!.breeds[0].description)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyNavigator() {
    val navController = rememberNavController()
    NaviModel.navi = navController
    NavHost(navController = navController, startDestination = "catList") {
        composable("catList") {
            CatListPage(navController)
        }
        composable("catDetail") {
            CatDetailPage(navController)
        }
    }
}

@Composable
private fun CatList(cats: List<CatElement>) {
    val rememberLazyListState = rememberLazyListState()
    Box {
        LazyColumn(
            state = rememberLazyListState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cats) { cat ->
                CatItem(cat)
            }
        }
    }
}

@Composable
private fun CatItem(cat: CatElement) {
    val model: CatListViewModel = viewModel()
    Card(
        Modifier
            .fillMaxWidth()
            .clickable {
                NaviModel.cat = cat
                NaviModel.navi?.navigate("catDetail") {
                }
            }
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column {
            GlideImage(cat.url, "")
//                Card(
//                    backgroundColor = Color(0xe0e0e0),
//                    contentColor = contentColorFor(backgroundColor = Color(0xe0e0e0)),
//                    elevation = 1.dp
//                ) {
//                    Text(
//                        text = "America",
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(8.dp, 4.dp)
//                    )
//                }
            Box(Modifier.padding(16.dp)) {
                Text(text = cat.breeds[0].name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// @Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

// @Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
