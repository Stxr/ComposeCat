package com.example.androiddevchallenge.model

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

object NaviModel{
    @SuppressLint("StaticFieldLeak")
    var navi: NavController?=null
    var cat: CatElement?=null
    var list:List<CatElement>?=null
}