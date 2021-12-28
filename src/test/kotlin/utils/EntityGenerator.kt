package utils

import user.entities.Addresses
import user.entities.ContactDetail
import user.entities.User
import user.utils.UserTypeEnum

object EntityGenerator {
    fun createUser(): User = User(
        firstName = "Brandon",
        lastName = "Cruz",
        email = "brandon@brandon.com",
        userType = UserTypeEnum.CR.value
    )

    fun createContactDetail(user: User): ContactDetail = ContactDetail(
        contactDetails = "09991234567",
        contactType = "Mobile",
        isPrimary = true,
        user = user
    )

    fun createAddresses(user: User): Addresses = Addresses(
        street = "jfabella",
        barangay = "mauway",
        city = "mandaluyong",
        province = "NCR",
        zipCode = "1554",
        user = user
    )
}