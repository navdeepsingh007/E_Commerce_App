package com.example.ecommerce.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LocationInputs {
    @SerializedName("lat")
    @Expose
    var lat : String? = null
    @SerializedName("longt")
    @Expose
    var longt : String? = null
}
