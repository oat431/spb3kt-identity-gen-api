package panomete.project.spb3kotlin.payload.response

import java.time.LocalDateTime

data class IdentityDTO(
    val nationalID: String,
    val title : String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDateTime,
)