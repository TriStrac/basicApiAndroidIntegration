package com.ucb.semifinal.springbootapiintegration.models

data class LoginRequest (
    val email:String,
    val password:String,
)

data class ApiResponse(
    val Message: String
)

data class SignUpRequest(
    val barangay: String,
    val birthdate: String,
    val city: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val password: String,
    val province: String,
    val street: String,
    val type: String
)

data class Inventory(
    val userEmail: String,
    val gallons: String
)

data class Subscription(
    val customerEmail: String,
    val stationEmail: String
)