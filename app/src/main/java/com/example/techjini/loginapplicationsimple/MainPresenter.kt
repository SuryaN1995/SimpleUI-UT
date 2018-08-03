package com.example.techjini.loginapplicationsimple

import android.content.Context
import android.text.Editable
import android.widget.EditText
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.internal.operators.OperatorReplay.observeOn
import rx.schedulers.Schedulers
import java.util.regex.Pattern

const val REG_EXP_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

/**
 * Created by Surya N on 03/08/18.
 */
class MainPresenter(var view: MainContractor.View, var context: Context) : MainContractor.Presenter {

    override fun validateFields(username: String?, email: String?) {
        val model = InfoModel()
        model.email = email
        model.username = username
        checkFieldEmpty(model.email, R.id.emailtil)
        checkFieldEmpty(model.username,R.id.nametil)
        if (model.email?.isNotEmpty() == true && isValid(REG_EXP_EMAIL, model.email!!) == false)
            view.setInvalidError(R.id.emailtil,true)
        else
            checkFieldEmpty(model.email, R.id.emailtil)
        if (email?.isNotEmpty() == true && email?.let { isValid(REG_EXP_EMAIL, it) } == true && username?.isNotEmpty() == true) {
            view?.showProgress()
            makeAPI(model)
        }
    }

    private fun checkFieldEmpty(field: String?, id: Int) {
        view.setError(id, field == null || field.isEmpty())
    }

    private var simpleAPI : SimpleAPI ? = null

    private fun makeAPI(model: InfoModel) {
        simpleAPI = SimpleAPICall.getSimpleAPI(context)
        simpleAPI?.getInfo()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(SubmitSubscriber())
    }


    private fun isValid(expression: String, email: String): Boolean {
        var isValid = false
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }


    private inner class SubmitSubscriber : Subscriber<InfoModel>(){
        override fun onNext(info : InfoModel?) {
            view?.hideProgress()
            if(info?.email?.isNotEmpty() == true){
                view?.updateUI()
            }
        }

        override fun onCompleted() {
        }

        override fun onError(error : Throwable?) {
            view?.hideProgress()
        }

    }

}