package user.contactDetails

import TrakkrrApplicationTests
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository
import user.serviceImpl.ContactDetailServiceImpl
import utils.EntityGenerator
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ContactDetailImplTest: TrakkrrApplicationTests() {
//implementation ni sir
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var contactDetailRepository: ContactDetailRepository

    @Autowired
    private lateinit var contactDetailServiceImpl: ContactDetailServiceImpl

    @Test
    fun `create contactDetail should return isPrimary value`() {
        //1. Create a contact detail (isPrimary = true)
        val user = EntityGenerator.createUser()
        val savedUser = userRepository.save(user)

        val bodyContactDetail = EntityGenerator.createContactDetail(user)
        val firstContactDetail = contactDetailRepository.save(bodyContactDetail.copy(
            user = savedUser
        ))
        //2. Create another contact detail (isPrimary = missing)
            // Test if the second contact detail (isPrimary = false)
        val secondContactDetail = contactDetailServiceImpl.createContactDetail(bodyContactDetail.copy(
            contactDetails = "09171234567",
            isPrimary = false
        ), savedUser.id!!)
        assertEquals(false, secondContactDetail.isPrimary)

        //3. Crate another contact detail (isPrimary = true)
            // Check the 1st and 2nd contact details (isPrimary = false)
            // Check if the 3rd
        val thirdContactDetail = contactDetailServiceImpl.createContactDetail(bodyContactDetail.copy(
            contactDetails = "09171234567",
            isPrimary = true
        ), savedUser.id!!)

        assertEquals(true, thirdContactDetail.isPrimary)
        assertEquals(false, contactDetailRepository.findById(firstContactDetail.id!!).get().isPrimary)
    }

    @Test
    fun `create contactDetails should throw an error given 4th contactDetail`() {
        // save three contactDetails
        val user = userRepository.save( EntityGenerator.createUser() )

        val body = EntityGenerator.createContactDetail(user).copy( user = user)

        //create 3 contact details
        contactDetailRepository.saveAll(
            //magsesesave ng sabay sabay
            //set up ng test; basta may valid data
            listOf(
                body,
                body.copy(contactDetails = "09771234567", isPrimary = false),
                body.copy(contactDetails = "09181234567", isPrimary = false)
            )
        )

        val exception = assertFailsWith<ResponseStatusException> {
            contactDetailServiceImpl.createContactDetail(body.copy(
                contactDetails = "09171234567",
                isPrimary = false
            ), user.id!!)
        }

        val expectedException = "400 BAD_REQUEST \"You cannot create more than four contact details.\"";

        assertEquals(expectedException, exception.message);
    }
}