package ir.matiran.kotlin_sample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(val size: String,val price: String, val side: String, val timestamp: String) {

    // for using below: remove data, and parameters and add : Serializable end of class
/*
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
*/

}