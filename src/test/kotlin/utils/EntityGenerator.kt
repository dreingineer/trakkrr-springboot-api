package utils

import user.entities.ContactDetail
import user.entities.User

object EntityGenerator {
    fun createUser(): User = User(
        firstName = "Brandon",
        lastName = "Cruz",
        email = "brandon@brandon.com",
        userType = "Contractor"
    )

    fun createContactDetail(user: User): ContactDetail = ContactDetail(
        contactDetails = "09991234567",
        contactType = "Mobile",
        isPrimary = true,
        user = user
    )
}