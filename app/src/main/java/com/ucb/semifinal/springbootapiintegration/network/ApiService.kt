package com.ucb.semifinal.springbootapiintegration.network

import com.ucb.semifinal.springbootapiintegration.models.ApiResponse
import com.ucb.semifinal.springbootapiintegration.models.Inventory
import com.ucb.semifinal.springbootapiintegration.models.LoginRequest
import com.ucb.semifinal.springbootapiintegration.models.SignUpRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("/loginAccount")
    fun loginAccount(@Body post: LoginRequest): Call<ApiResponse>

    @POST("/createAccount")
    fun createAccount(@Body post: SignUpRequest): Call<ApiResponse>

    @GET("/displayAllAccounts")
    fun getAllAccounts(): Call<List<SignUpRequest>>

    @DELETE("/deleteUserByEmail/{email}")
    fun deleteUserByEmail(@Path("email") email: String): Call<String>

    @POST("/loginAccount")
    fun createInventory(@Body post: Inventory): Call<Inventory>

    @GET("/displayAllInventory")
    fun getAllInventory(): Call<List<Inventory>>

    @DELETE("/deleteInventoryByEmail/{email}")
    fun deleteInventoryByEmail(@Path("email") email: String): Call<String>

}