package panomete.project.spb3kotlin.payload.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Identity Request Payload")
data class IdentityRequest(
    val province: String?,
    val district: String?,
    val type: String?, // replace with enum later
    val title: String?, // replace with enum later
)