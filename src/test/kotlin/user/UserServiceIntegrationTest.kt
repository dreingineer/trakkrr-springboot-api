package user

import TrakkrrApplicationTests
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository
import utils.EntityGenerator

@AutoConfigureMockMvc
class UserServiceIntegrationTest: TrakkrrApplicationTests() {

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

    @Test
    fun `create user should return 200`() {
        val body = EntityGenerator.createUser()
        //ginagawa ni spring boot automatically tinatranslate or cinoconvert to entity ung json
        mockMvc.post("/api/user") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `get user should return user with contactDetails`() {
        val user = userRepository.save(EntityGenerator.createUser())

        contactDetailRepository.save(EntityGenerator.createContactDetail(user).copy(
            user = user
        ))

        mockMvc.get("/api/user/${user.id!!}") {
            accept(MediaType.APPLICATION_JSON)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.contactDetails") { isNotEmpty() }
//            jsonPath("$.contactDetails[0].user" ) { isNotEmpty() } //error dapat
        }
    }


}

//200 -> isOK()
//409 -> isConflict()
//401 -> isUnauthorized()
//403 -> isForbidden()
//404 -> isNotFound