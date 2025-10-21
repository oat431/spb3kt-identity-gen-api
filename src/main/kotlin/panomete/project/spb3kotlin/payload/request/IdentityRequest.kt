package panomete.project.spb3kotlin.payload.request

data class IdentityRequest(
    val province: String?,
    val district: String?,
    val type: String?, // replace with enum later
    val title: String?, // replace with enum later
)