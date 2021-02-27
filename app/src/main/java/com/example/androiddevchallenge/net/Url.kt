package com.example.androiddevchallenge.net

import rxhttp.wrapper.annotation.DefaultDomain

class Url {
    companion object {
        @DefaultDomain
        const val BASE_URL = "https://api.thecatapi.com/"
    }
}