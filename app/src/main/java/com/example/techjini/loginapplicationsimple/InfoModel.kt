package com.example.techjini.loginapplicationsimple

import com.google.gson.annotations.SerializedName

/**
 * Created by Surya N on 03/08/18.
 */
open class InfoModel {

    @SerializedName("username")
    var username: String? = null


    @SerializedName("email")
    var email: String? = null
}