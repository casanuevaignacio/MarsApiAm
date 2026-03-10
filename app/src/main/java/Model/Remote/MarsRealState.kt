package Model.Remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


// partimos por el pojo 1
@Entity( tableName = "mars_table")
data class MarsRealState (

    @SerializedName("id")
    @PrimaryKey
    val id: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("type")
    val type: String,

    @SerializedName("img_src")
    val img_src: String
)