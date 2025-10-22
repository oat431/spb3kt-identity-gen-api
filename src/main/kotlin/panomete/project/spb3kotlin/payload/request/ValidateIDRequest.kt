package panomete.project.spb3kotlin.payload.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "ID Request")
data class ValidateIDRequest(
    val idNumber: String
)