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

    override fun validateIDNumber(idNumber: String): Boolean {
        val CHECK_SUM_BASE_MULTIPLIER = 13
        val ID_LENGTH_WITHOUT_CHECK_SUM = 12
        val CHECK_SUM_MODULUS = 11
        val GET_ONLY_UNIT_MODULUS = 10
        var sum = 0;
        for (i in 0..<ID_LENGTH_WITHOUT_CHECK_SUM) {
            val digit = Character.getNumericValue(idNumber[i])
            sum += digit * (CHECK_SUM_BASE_MULTIPLIER - i)
        }
        val checkSum = (CHECK_SUM_MODULUS - (sum % CHECK_SUM_MODULUS)) % GET_ONLY_UNIT_MODULUS
        val lastDigit = Character.getNumericValue(idNumber[ID_LENGTH_WITHOUT_CHECK_SUM])
        return checkSum == lastDigit
    }

    override fun isValidIDNumberInput(idNumber: String): Boolean {
        val ID_NUMBER_LENGTH = 13
        if (idNumber.length != ID_NUMBER_LENGTH) {
            return false
        }
        if (!idNumber.all { it.isDigit() }) {
            return false
        }
        return true
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