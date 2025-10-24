package panomete.project.spb3kotlin.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import panomete.project.spb3kotlin.entity.Province
import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class IdentityServiceImpl : IdentityService {
    var provinces: List<Province> = listOf()
    val random = Random(622115039)
    val missingCode : List<Int> = listOf(28,29,38,59,68,69,78,79,87,88,89)

    override fun genIdentity(request: IdentityRequest?): IdentityDTO {
        loadJson()
        if(request != null) {
            return generateIdentity(request)
        }
        return generateRandomIdentity()
    }

    private fun loadJson() {
        val jsonPath = "/data/province.json"
        val provincesText = this::class.java.getResource(jsonPath)?.readText()
            ?: throw IllegalStateException("Cannot find provinces data file")
        val mapper= jacksonObjectMapper()
        provinces = mapper.readValue(provincesText,object : TypeReference<List<Province>>() {})
    }

    private fun genIDNumber(): String {
        val cType = if(random.nextBoolean()) "1" else "3"

        var provinceCode: Int
        do{
            provinceCode = random.nextInt(10, 97)
        } while (missingCode.contains(provinceCode))
        val cProvinceCode = "$provinceCode"

        val cityCode: Int = (provinces.find { it.code == provinceCode })?.districtAmt ?: 1
        val cCityCode = String.format("%02d", cityCode)

        val cSeq1 = String.format("%05d", random.nextInt(100000))
        val cSeq2 = String.format("%02d", random.nextInt(100))

        val idWithoutChecksum = "$cType$cProvinceCode$cCityCode$cSeq1$cSeq2"

        val cCheckSum = genCheckSum(idWithoutChecksum)
        return "${cType}-${cProvinceCode}${cCityCode}-${cSeq1}-${cSeq2}-${cCheckSum}"
    }

    private fun genCheckSum(idNumber: String): Int {
        val prepareString = idNumber.replace("-", "")
        val checkSumBaseMultiplier = 13
        val idLengthWithoutCheckSUm = 12
        val checkSumModulus = 11
        val getOnlyUnitModulus = 10
        var sum = 0;
        for (i in 0..<idLengthWithoutCheckSUm) {
            val digit = Character.getNumericValue(prepareString[i])
            sum += digit * (checkSumBaseMultiplier - i)
        }
        return (checkSumModulus - (sum % checkSumModulus)) % getOnlyUnitModulus
    }

    override fun validateIDNumber(idNumber: String): Boolean {
        val checkSum = genCheckSum(idNumber)
        val lastDigit = Character.getNumericValue(idNumber[idNumber.length - 1])
        return checkSum == lastDigit
    }

    override fun isValidIDNumberInput(idNumber: String): Boolean {
        val idNumberLength = 13
        val checkString = idNumber.replace("-", "")
        if (checkString.length != idNumberLength) {
            return false
        }
        if (!checkString.all { it.isDigit() }) {
            return false
        }
        return true
    }

    private fun generateRandomIdentity(): IdentityDTO {
        return IdentityDTO(
            nationalID = genIDNumber(),
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