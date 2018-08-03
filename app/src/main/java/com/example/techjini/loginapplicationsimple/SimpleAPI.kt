package com.example.techjini.loginapplicationsimple


import retrofit2.http.PUT
import rx.Observable

interface SimpleAPI {

    @PUT("5b640c222e00005200413ddf")
    fun getInfo()  : Observable<InfoModel>
}
