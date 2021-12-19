package com.cherish.homeprojectapp.data.remote


import com.cherish.homeprojectapp.data.model.response.OrganizationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("organizations")
    suspend fun getOrganizations(): List<OrganizationResponse>
}