package campalans.m8.apptranslate

import campalans.m8.apptranslate.ui.theme.DetecitonResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api_Service {
    @GET("/0.2/languages")
    suspend fun getLaunguage():Response<List<Language>>

    @Headers("Authorization: Bearer 6f7252a80b6c340b86f3a56b7b3ed679")
    @FormUrlEncoded
    @POST("/0.2/detect")
    suspend fun getTextLanguage(@Field ("q")text:String):Response<DetecitonResponse>

}
