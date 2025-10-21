package panomete.project.spb3kotlin.common

data class ResponseDTO<T>(
    val data: T?,
    val error : ErrorDTO?,
) {}