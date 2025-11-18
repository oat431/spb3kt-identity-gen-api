package panomete.project.spb3kotlin.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import panomete.project.spb3kotlin.entity.Name
import panomete.project.spb3kotlin.entity.Province
import panomete.project.spb3kotlin.payload.request.IdentityRequest
import panomete.project.spb3kotlin.payload.response.IdentityDTO
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class IdentityServiceImpl : IdentityService {
    var provinces: List<Province> = listOf()
    var maleNames: List<Name> = listOf()
    var femaleNames: List<Name> = listOf()
    var lastNames: List<Name> = listOf()
    val random = Random(622115039)
    val missingCode : List<Int> = listOf(28,29,38,59,68,69,78,79,87,88,89)

    override fun genIdentity(request: IdentityRequest?): IdentityDTO {
        loadProvince()
        loadFirstNames()
        loadLastNames()
        if(request != null) {
            return generateIdentity(request)
        }
        return generateRandomIdentity()
    }

    private fun loadProvince() {
        val jsonPath = "/data/province.json"
        val provincesText = this::class.java.getResource(jsonPath)?.readText()
            ?: throw IllegalStateException("Cannot find provinces data file")
        val mapper= jacksonObjectMapper()
        provinces = mapper.readValue(provincesText,object : TypeReference<List<Province>>() {})
    }

    private fun loadFirstNames() {
        val jsonPathMale = "/data/male_name.json"
        val jsonPathFemale = "/data/female_name.json"
        val maleNamesText = this::class.java.getResource(jsonPathMale)?.readText()
            ?: throw IllegalStateException("Cannot find data file")
        val femaleNamesText = this::class.java.getResource(jsonPathFemale)?.readText()
            ?: throw IllegalStateException("Cannot find data file")
        val mapper= jacksonObjectMapper()
        maleNames = mapper.readValue(maleNamesText,object : TypeReference<List<Name>>() {})
        femaleNames = mapper.readValue(femaleNamesText,object : TypeReference<List<Name>>() {})
    }

    private fun loadLastNames() {
        val jsonPathLast = "/data/lastname.json"
        val lastNamesText = this::class.java.getResource(jsonPathLast)?.readText()
            ?: throw IllegalStateException("Cannot find data file")
        val mapper= jacksonObjectMapper()
        lastNames = mapper.readValue(lastNamesText,object : TypeReference<List<Name>>() {})
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

    private fun genBirthDate(): LocalDateTime {
        val currentYear = LocalDateTime.now().year
        val year = random.nextInt(currentYear - 60, currentYear - 15)
        val month = random.nextInt(1, 13)
        val day = when (month) {
            2 -> random.nextInt(1, 29)
            4, 6, 9, 11 -> random.nextInt(1, 31)
            else -> random.nextInt(1, 32)
        }
        return LocalDateTime.of(year, month, day, 0, 0)
    }

    private fun genTitle(age: Int, gender: Boolean) : String {
        return if(age < 15) {
            if(gender) {
                "เด็กชาย"
            } else {
                "เด็กหญิง"
            }
        } else {
            if(gender) {
                "นาย"
            } else {
                "นางสาว"
            }
        }
    }

    private fun genFirstName(gender: Boolean) : Name {
        return if(gender) {
            maleNames[random.nextInt(maleNames.size)]
        } else {
            femaleNames[random.nextInt(femaleNames.size)]
        }
    }

    private fun genLastName() : Name {
        return lastNames[random.nextInt(lastNames.size)]
    }

    private fun generateRandomIdentity(): IdentityDTO {
        val nationalIdNumber = genIDNumber()
        val birthDate = genBirthDate()
        val age = LocalDateTime.now().year - birthDate.year
        val gender = random.nextBoolean()
        val title = genTitle(age, gender)
        val firstName = genFirstName(gender)
        val lastName = genLastName()
        return IdentityDTO(
            nationalID = nationalIdNumber,
            title = title,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
    }

    private fun generateIdentity(request: IdentityRequest): IdentityDTO {
        return IdentityDTO(
                nationalID = "9876543210987",
                title = "Ms.",
                firstName = Name(
                    nameEN = "Jane",
                    nameTH = "เจน"
                ),
                lastName = Name(
                    nameEN = "Doe",
                    nameTH = "โด"
                ),
                birthDate = LocalDateTime.of(1985, 5, 15, 0, 0)
            )
    }


}