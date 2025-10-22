package panomete.project.spb3kotlin.service

import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO

interface IdentityService {
    fun genIdentity(request: IdentityRequest?): IdentityDTO
    fun validateIDNumber(idNumber: String): Boolean
    fun isValidIDNumberInput(idNumber: String): Boolean
}