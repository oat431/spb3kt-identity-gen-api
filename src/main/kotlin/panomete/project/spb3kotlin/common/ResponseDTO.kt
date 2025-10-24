package panomete.project.spb3kotlin.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Common Response Data Transfer Object")
data class ResponseDTO<T>(
    val data: T?,
    val error : ErrorDTO?,
)
