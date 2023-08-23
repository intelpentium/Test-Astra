package kedaiapps.projeku.testandroidastra.services.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

//home
@Keep
data class ResponseHome(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)


//home detail
@Keep
data class ResponseHomeDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("abilities") val abilities: List<ResponseAbilities>,
    @SerializedName("sprites") val sprites: ResponseSprites,
)

@Keep
data class ResponseAbilities(
    @SerializedName("ability") val ability: ResponseAbilitiesItem,
)

@Keep
data class ResponseAbilitiesItem(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

@Keep
data class ResponseSprites(
    @SerializedName("front_default") val front_default: String,
    @SerializedName("other") val other: ResponseSpritesOther,
)

@Keep
data class ResponseSpritesOther(
    @SerializedName("home") val home: ResponseSprites,
)

@Keep
data class ResponseSpritesOtherItem(
    @SerializedName("front_default") val front_default: String,
)


