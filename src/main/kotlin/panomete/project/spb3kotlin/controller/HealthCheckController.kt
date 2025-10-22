package panomete.project.spb3kotlin.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import panomete.project.spb3kotlin.common.ResponseDTO

@RestController
@Tag(name = "Health Check API", description = "The API to check service health status")
@RequestMapping("/api/v1/health-check")
class HealthCheckController {
    @GetMapping
    fun healthCheck(): ResponseEntity<ResponseDTO<String>> {
        val response = ResponseDTO(
            data = "Service is up and running",
            error = null
        )
        return ResponseEntity.ok(response)
    }
}