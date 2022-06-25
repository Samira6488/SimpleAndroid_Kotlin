package ir.matiran.kotlin_sample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Profile: Serializable {
    @Expose
    @SerializedName("size")
    var size: String? = null

    @Expose
    @SerializedName("price")
    var price: String? = null

    @Expose
    @SerializedName("side")
    var side: String? = null

    @Expose
    @SerializedName("timestamp")
    var timestamp: String? = null


}