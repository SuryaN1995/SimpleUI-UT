package com.example.techjini.loginapplicationsimple

/**
 * Created by Surya N on 03/08/18.
 */
object MainContractor {


    interface Presenter{
        fun validateFields(username: String?, email: String?)
    }


    interface View{
        fun showProgress()
        fun hideProgress()
        fun updateUI()
        fun setError(view : Int,isError:Boolean)
        fun setInvalidError(view : Int,isError:Boolean)
    }
}