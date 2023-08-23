package kedaiapps.projeku.testandroidastra.services.rest

import kedaiapps.projeku.testandroidastra.services.Response
import kedaiapps.projeku.testandroidastra.services.entity.*
import retrofit2.http.*

interface MainRest {

    //home
    @GET("pokemon")
    suspend fun home() : Response<List<ResponseHome>>

    //search
    @GET("pokemon/{name}")
    suspend fun homeDetail(
        @Path("name") id: String,
    ) : ResponseHomeDetail
}