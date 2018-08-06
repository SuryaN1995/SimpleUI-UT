package com.example.techjini.loginapplicationsimple.test

import com.example.techjini.loginapplicationsimple.InfoModel
import com.example.techjini.loginapplicationsimple.MainContractor
import com.example.techjini.loginapplicationsimple.MainPresenter
import com.example.techjini.loginapplicationsimple.SimpleAPI
import com.example.techjini.loginapplicationsimple.utility.BaseTest
import com.example.techjini.loginapplicationsimple.utility.JsonToObject
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.mock
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.Observable

/**
 * Created by Surya N on 06/08/18.
 */
class PresenterTest : BaseTest() {

    private var presenter : MainPresenter ? = null

    @Mock
    private lateinit var view : MainContractor.View

    private var model : InfoModel ?= null

    @Mock
    private lateinit var simpleAPI : SimpleAPI

    private var subscriber : MainPresenter.SubmitSubscriber ? = null

    private val httpException = HttpException(Response.error<Any>(409, mock<ResponseBody>(ResponseBody::class.java)))

    override fun setUp(){
        super.setUp()
        model = JsonToObject.getResponse("response",InfoModel::class.java) as InfoModel
        presenter = MainPresenter(view, simpleAPI)
        subscriber = presenter?.SubmitSubscriber()
    }

    @Test
    fun verifyUserNameAndEmailFailureCase(){
        presenter?.validateFields(null,null)
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        presenter?.validateFields(null,"")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        presenter?.validateFields("",null)
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        presenter?.validateFields("","")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        presenter?.validateFields("MainPresenter","")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
    }


    @Test
    fun verifyEmailInvalid(){
        presenter?.validateFields("","Hello")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        verify(view, atLeast(1)).setInvalidError(anyInt(), anyBoolean())
        presenter?.validateFields(null,"Hello")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        verify(view, atLeast(1)).setInvalidError(anyInt(), anyBoolean())
        presenter?.validateFields("MainPresenter","Hello")
        verify(view, atLeast(1)).setInvalidError(anyInt(), anyBoolean())
    }


    @Test
    fun verifyEmailValidAndUserNameEmpty(){
        presenter?.validateFields("","google@gmail.com")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        verify(view, never()).setInvalidError(anyInt(), anyBoolean())
        presenter?.validateFields(null,"google@gmail.com")
        verify(view, atLeast(2)).setError(anyInt(), anyBoolean())
        verify(view, never()).setInvalidError(anyInt(), anyBoolean())
    }


    @Test
    fun verifyAPIFailureCase(){
        Mockito.`when`(simpleAPI.getInfo()).thenAnswer {
            Observable.error<HttpException>(httpException)
        }
        presenter?.validateFields("MainPresenter","google@gmail.com")
        verify(view, never()).setInvalidError(anyInt(), anyBoolean())
        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).showError(anyString())
    }

    @Test
    fun verifyAPISuccessCase(){
        Mockito.`when`(simpleAPI.getInfo()).thenReturn(Observable.just(model))
        presenter?.validateFields("MainPresenter","google@gmail.com")
        verify(view, never()).setInvalidError(anyInt(), anyBoolean())
        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).updateUI()
    }

    @Test
    fun verifyOnNext(){
        subscriber?.onNext(model)
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).updateUI()
    }

    @Test
    fun verifyOnError(){
        subscriber?.onError(httpException)
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).showError(anyString())
    }

}
