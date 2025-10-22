package panomete.project.spb3kotlin.common

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Error Data Transfer Object")
data class ErrorDTO(val message: String, val errorCode: Int)