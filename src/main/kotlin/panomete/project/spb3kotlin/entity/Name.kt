package panomete.project.spb3kotlin.entity

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Name Entity")
data class Name (
    val nameEN: String,
    val nameTH: String
)