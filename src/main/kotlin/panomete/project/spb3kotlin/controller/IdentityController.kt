package panomete.project.spb3kotlin.controller

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import panomete.project.spb3kotlin.common.ResponseDTO
import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO
import panomete.project.spb3kotlin.service.IdentityService

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/identity")
class IdentityController(val identityService: IdentityService) {


    @GetMapping
    fun createRandomIdentity(): ResponseEntity<ResponseDTO<IdentityDTO>> {
        val response :IdentityDTO = identityService.genIdentity(null)
        val responseDTO = ResponseDTO(
            data = response,
            error = null
        )
        return ResponseEntity.ok(responseDTO)
    }

    @PostMapping
    fun createIdentity(
        @RequestBody request: IdentityRequest,
    ): ResponseEntity<ResponseDTO<IdentityDTO>> {
        val response :IdentityDTO = identityService.genIdentity(request)
        val responseDTO = ResponseDTO(
            data = response,
            error = null
        )
        return ResponseEntity.ok(responseDTO)
    }
}