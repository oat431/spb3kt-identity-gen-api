package panomete.project.spb3kotlin.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import panomete.project.spb3kotlin.common.ErrorDTO
import panomete.project.spb3kotlin.common.ResponseDTO
import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.request.ValidateIDRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO
import panomete.project.spb3kotlin.service.IdentityService

@RestController
@RequiredArgsConstructor
@Tag(name = "Gen Identity API", description = "The API to access an identity request")
@RequestMapping("/api/v1/identity")
class IdentityController(val identityService: IdentityService) {


    @GetMapping
    @Operation(summary = "Create Random Identity")
    fun createRandomIdentity(): ResponseEntity<ResponseDTO<IdentityDTO>> {
        val response :IdentityDTO = identityService.genIdentity(null)
        val responseDTO = ResponseDTO(
            data = response,
            error = null
        )
        return ResponseEntity.ok(responseDTO)
    }

    @PostMapping
    @Operation(summary = "Create Identity with given parameters")
    fun createIdentity(
        @RequestBody request: IdentityRequest?,
    ): ResponseEntity<ResponseDTO<IdentityDTO>> {
        val response :IdentityDTO = identityService.genIdentity(request)
        val responseDTO = ResponseDTO(
            data = response,
            error = null
        )
        return ResponseEntity.ok(responseDTO)
    }

    @PostMapping("/check-id")
    @Operation(summary = "Validate ID Number")
    fun checkIDNumber(
        @RequestBody request: ValidateIDRequest,
    ): ResponseEntity<ResponseDTO<String>> {
        if(!identityService.isValidIDNumberInput(request.idNumber)){
            val responseDTO = ResponseDTO<String>(
                data = null,
                error = ErrorDTO("id : ${request.idNumber} is not match thai national ID Number condition", 1001)
            )
            return ResponseEntity.badRequest().body(responseDTO)
        }
        val isValid: Boolean = identityService.validateIDNumber(request.idNumber)
        val message = if (isValid) "valid thai national ID Number" else "invalid thai national ID Number"
        val responseDTO = ResponseDTO(
            data = "id: ${request.idNumber} is $message",
            error = null
        )
        return ResponseEntity.ok(responseDTO)
    }
}