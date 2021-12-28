package user.contactDetails

import TrakkrrApplicationTests
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository

class ContactDetailServiceIntegrationTest: TrakkrrApplicationTests() {
    @Autowired
    private lateinit var mockMvc: MockMvc //para magamit ung http request

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var contactDetailRepository: ContactDetailRepository

    @BeforeEach
    fun setUp() {
        contactDetailRepository.deleteAll()
        userRepository.deleteAll()
    }
}