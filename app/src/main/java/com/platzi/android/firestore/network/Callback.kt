package com.platzi.android.firestore.network

import java.lang.Exception

//callbacks notify if operations are successful or not
interface Callback<T> {
    fun onSuccess(result:T?)


    fun onFailed(exception: Exception)

}