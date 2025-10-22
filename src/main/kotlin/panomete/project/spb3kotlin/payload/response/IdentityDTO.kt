package panomete.project.spb3kotlin.payload.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Identity Response")
data class IdentityDTO(
    val nationalID: String,
    val title : String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDateTime,
)