package contactDetail

import TrakkrrApplicationTests
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository
import user.serviceImpl.ContactDetailServiceImpl
import user.serviceImpl.UserServiceImpl
import utils.EntityGenerator
import javax.swing.text.html.parser.Entity
import kotlin.test.assertEquals

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
}