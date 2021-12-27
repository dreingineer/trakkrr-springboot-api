package user

import TrakkrrApplicationTests
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository
import user.serviceImpl.ContactDetailServiceImpl
import user.serviceImpl.UserServiceImpl
import utils.EntityGenerator
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ContactDetailServiceImplTest: TrakkrrApplicationTests() {
    @Autowired
    private lateinit var contactDetailRepository: ContactDetailRepository

    @Autowired
    private lateinit var contactDetailServiceImpl: ContactDetailServiceImpl

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @BeforeEach
    //kada run ng test delete muna agad ung laman ng repository
    fun setUp() {
        contactDetailRepository.deleteAll()
    }

    @Test
    fun `create contactDetail should return contactDetail`() {
        //check is user repo is empty
        assertEquals(0, userRepository.findAll().count())

        //check if contactDetail repo is empty
        assertEquals(0, contactDetailRepository.findAll().count())

        //create user
        val user = EntityGenerator.createUser()
        //create contactDetail
        val contactDetail = EntityGenerator.createContactDetail(user)

        val createdUser = userServiceImpl.createUser(user);
        val createdContactDetail = contactDetailRepository.save(contactDetail.copy(
            user = createdUser
        ))
        assertEquals(1, contactDetailRepository.findAll().count());
        assertEquals(contactDetail.contactDetails, createdContactDetail.contactDetails);
        assertEquals(contactDetail.contactType, createdContactDetail.contactType);
        assertEquals(contactDetail.isPrimary, createdContactDetail.isPrimary);
        assertEquals(contactDetail.user, createdContactDetail.user);
    }

    @Test
    fun `get contactDetail should return contactDetail given valid id`() {
        val user = EntityGenerator.createUser()
        val contactDetail = EntityGenerator.createContactDetail(user)

        val createdUser = userServiceImpl.createUser(user)
        val createdContactDetail = contactDetailRepository.save(contactDetail.copy(
            user = createdUser
        ))

        val cdFromServiceImpl = contactDetailServiceImpl.getContactDetailById(createdContactDetail.id!!)
        assertEquals(createdContactDetail.id, cdFromServiceImpl.id);
        assertEquals(createdContactDetail.contactDetails, cdFromServiceImpl.contactDetails);
        assertEquals(createdContactDetail.contactType, cdFromServiceImpl.contactType);
        assertEquals(createdContactDetail.isPrimary, cdFromServiceImpl.isPrimary);
    }

    @Test
    fun `get contactDetail should throw error given invalid id`() {
        val invalidId: Long = 123

        val exception = assertFailsWith<ResponseStatusException> {
            contactDetailServiceImpl.getContactDetailById(invalidId)
        };

        val expectedException = "404 NOT_FOUND \"Contact Detail with $invalidId does not exist\""
        assertEquals(expectedException, exception.message);
    }

    @Test
    fun `delete contactDetail should work`() {
        val user = EntityGenerator.createUser()
        val createdUser = userServiceImpl.createUser(user);
        val contactDetail = EntityGenerator.createContactDetail(createdUser)
        val createdContactDetail = contactDetailRepository.save(contactDetail)

        assertEquals(1, contactDetailRepository.findAll().count())
        contactDetailServiceImpl.deleteContactDetail(createdContactDetail.id!!)

        assertEquals(0, contactDetailRepository.findAll().count())
    }

    @Test
    fun `update contactDetail should return updated contactDetail given valid inputs`() {

        //create a user
        val user = EntityGenerator.createUser();
        //create contactDetail
        val contactDetail = EntityGenerator.createContactDetail(user);

        val createdUser = userServiceImpl.createUser(user);
        val original = contactDetailRepository.save(contactDetail.copy(
            user = createdUser
        ))

        val body = original.copy(
            contactDetails = "09991234111",
            contactType = "Mobile Landline",
            isPrimary = true,
//            user = createdUser
        )

        val updatedContactDetail = contactDetailServiceImpl.updateContactDetail(body, original.id!!)
        assertEquals(body.contactDetails, updatedContactDetail.contactDetails);
        assertEquals(body.contactType, updatedContactDetail.contactType);
        assertEquals(body.isPrimary, updatedContactDetail.isPrimary);
    }
}