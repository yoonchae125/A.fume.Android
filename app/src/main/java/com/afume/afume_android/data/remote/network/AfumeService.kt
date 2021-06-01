package com.afume.afume_android.data.remote.network

import com.afume.afume_android.data.vo.request.*
import com.afume.afume_android.data.vo.response.*
import io.reactivex.Single
import retrofit2.http.*

interface AfumeService {
    //Sign - validate email
    @GET("user/validate/email")
    suspend fun getValidateEmail(
        @Query("email") email : String
    ): ResponseBase<Boolean>

    //Sign - validate nickname
    @GET("user/validate/name")
    suspend fun getValidateNickname(
        @Query("nickname") nickname : String
    ): ResponseBase<Boolean>

    //Sign - sign up
    @POST("user/register")
    suspend fun postRegisterInfo(
        @Body body: RequestRegister
    ): ResponseBase<Boolean>

    //Sign - sign in
    @POST("user/login")
    suspend fun postLoginInfo(
        @Body body: RequestLogin
    ): ResponseBase<ResponseLogin>

    //Survey - series
    @GET("series")
    suspend fun getSeries(
    ): ResponseBase<ResponseSeries>

    //Survey - perfume
    @GET("perfume/survey")
    suspend fun getSurveyPerfume(
        @Header("x-access-token") token : String
    ):ResponseBase<ResponsePerfume>

    //Survey - keyword
    @GET("keyword")
    suspend fun getKeyword(
    ):ResponseBase<ResponseKeyword>

    @POST("user/survey")
    suspend fun postSurvey(
        @Header("x-access-token") token : String,
        @Body body: RequestSurvey
    ):ResponseBase<Int>

    //My
    @GET("user/{userIdx}/perfume/liked")
    suspend fun getLikedPerfume(
        @Header("x-access-token") token : String,
        @Path("userIdx") userIdx : Int
    ):ResponseBase<ResponsePerfume>

    @GET("user/{userIdx}/review")
    suspend fun getMyPerfume(
        @Header("x-access-token") token : String,
        @Path("userIdx") userIdx : Int
    ):ResponseBase<MutableList<ResponseMyPerfume>>

    // Edit - My info
    @PUT("user/{userIdx}")
    suspend fun putMyInfo(
        @Header("x-access-token") token : String,
        @Path("userIdx") userIdx : Int,
        @Body body : RequestEditMyInfo
    ):ResponseBase<ResponseEditMyInfo>

    // Edit - Password
    @PUT("user/changePassword")
    suspend fun putPassword(
        @Header("x-access-token") token : String,
        @Body body : RequestEditPassword
    ):ResponseMessage

    // Home - personal recommend
    @GET("perfume/recommend/personal")
    suspend fun getRecommendPerfumeList(
        @Header("x-access-token") token : String,
    ):ResponseBase<ResponseRecommendPerfumeList>

    // Home - common recommend
    @GET("perfume/recommend/common")
    suspend fun getCommonPerfumeList(
        @Header("x-access-token") token : String,
    ):ResponseBase<ResponseRecommendPerfumeList>

    // Home - recent perfume
    @GET("perfume/recent")
    suspend fun getRecentList(
        @Header("x-access-token") token : String,
    ):ResponseBase<ResponseRecentPerfumeList>

    // Home - New Perfume List
    @GET("perfume/new")
    suspend fun getNewPerfumeList(

    ):ResponseBase<ResponseNewPerfumeList>

    @GET("perfume/{perfumeIdx}")
    fun getPerfumeDetail(
        @Path("perfumeIdx") perfumeIdx : Int
    ): Single<ResponsePerfumeDetail>

    @GET("perfume/{perfumeIdx}/review")
    fun getPerfumeDetailWithReview(
        @Path("perfumeIdx") perfumeIdx : Int
    ): Single<ResponsePerfumeDetailWithReviews>

}