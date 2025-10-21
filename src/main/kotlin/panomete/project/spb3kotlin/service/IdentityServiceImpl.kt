package panomete.project.spb3kotlin.service

import org.springframework.stereotype.Service
import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO
import java.time.LocalDateTime

@Service
class IdentityServiceImpl : IdentityService {
    override fun genIdentity(request: IdentityRequest?): IdentityDTO {
        // Dummy implementation for demonstration purposes
        if(request != null) {
            return generateIdentity(request)
        }
        return generateRandomIdentity()
    }

    private fun generateRandomIdentity(): IdentityDTO {
        return IdentityDTO(
            nationalID = "1234567890123",
            title = "Mr.",
            firstName = "John",
            lastName = "Doe",
            birthDate = LocalDateTime.of(1990, 1, 1, 0, 0)
        )
    }

    private fun generateIdentity(request: IdentityRequest): IdentityDTO {
        return IdentityDTO(
                nationalID = "9876543210987",
                title = "Ms.",
                firstName = "Jane",
                lastName = "Smith",
                birthDate = LocalDateTime.of(1985, 5, 15, 0, 0)
            )
    }


}