package panomete.project.spb3kotlin.entity

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Province Entity")
data class Province(
    val code : Int,
    val name: String,
    val districtAmt: Int
)
