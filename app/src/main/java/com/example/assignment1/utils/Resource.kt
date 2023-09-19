package com.example.assignment1.utils

sealed class Resource<N>(val status: Status, val data: N? = null, val message: String? = null) {

    class Success<N>(data: N?= null) : Resource<N>(Status.SUCCESS,data)
    class Error<N>(message: String,data: N? = null) : Resource<N>(Status.ERROR,data,message)
    class Loading<N> : Resource<N>(Status.LOADING)

}