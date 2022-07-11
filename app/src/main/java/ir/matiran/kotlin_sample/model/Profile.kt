package ir.matiran.kotlin_sample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Profile(val size: String,val price: String, val side: String, val timestamp: String) {

}