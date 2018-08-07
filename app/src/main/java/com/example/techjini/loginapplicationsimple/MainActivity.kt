package com.example.techjini.loginapplicationsimple

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import com.example.techjini.loginapplicationsimple.databinding.ActivityMainBinding

/**
 * Created by Surya N
 */

open class MainActivity : AppCompatActivity() , MainContractor.View, View.OnClickListener{

    override fun showError(error: String?) {
        binding?.root?.let { error?.let { it1 -> Snackbar.make(it, it1,Snackbar.LENGTH_SHORT).show() } }
        isDataReady = true
        dataListener?.onDataLoaded()
    }

    override fun setInvalidError(view: Int, isError : Boolean) {
        when(view){
            R.id.emailtil -> setErrorMessage(binding?.emailtil,isError,getString(R.string.email_error))
        }
    }

    override fun setError(view: Int, isError : Boolean) {
        when(view){
            R.id.emailtil -> setErrorMessage(binding?.emailtil,isError,getString(R.string.email_empty))
            R.id.nametil -> setErrorMessage(binding?.nametil,isError, getString(R.string.name_empty))
        }
    }

    private fun setErrorMessage(view: TextInputLayout?, hasError: Boolean, error: String?) {
        view?.error = if (hasError) {
            error
        } else {
            null
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
           R.id.submit -> {
               binding?.username?.text = binding?.username?.text?.trim() as Editable?
               binding?.email?.text = binding?.email?.text?.trim() as Editable?
               binding?.email?.setSelection(binding?.email?.text?.length?:0)
               binding?.username?.setSelection(binding?.username?.text?.length?:0)
               presenter?.validateFields(binding?.username?.text?.toString(),
                       binding?.email?.text?.toString())
           }
        }
    }

    override fun showProgress() {
        initProgress()
        progressDialog?.show()
    }

    var dataListener : DataListener ? = null

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun updateUI() {
        ConfirmDialogFragment().show(supportFragmentManager,ConfirmDialogFragment::class.java.name)
        isDataReady = true
        dataListener?.onDataLoaded()
    }

    private var progressDialog: ProgressDialog? = null

    private fun initProgress() {
        progressDialog = ProgressDialog.show(this, null, getString(R.string.loading), true, false)
        progressDialog?.isIndeterminate = true
    }



    private var binding : ActivityMainBinding ? = null
    private var presenter : MainPresenter ? = null
    private var simpleAPI : SimpleAPI ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding?.clickHandler = this
        simpleAPI = SimpleAPICall.getSimpleAPI(this)
        presenter = MainPresenter(this, simpleAPI)
    }

    // To perform anything in test we annotate that function as VisibleForTesting
    @VisibleForTesting
    fun setSimpleAPI(simpleAPI: SimpleAPI){
        this.simpleAPI = simpleAPI
    }


    var isDataReady : Boolean = false

    interface DataListener{
        fun onDataLoaded()
    }
}
