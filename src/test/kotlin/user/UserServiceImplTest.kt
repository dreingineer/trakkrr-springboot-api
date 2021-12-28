package user

import TrakkrrApplicationTests
import utils.EntityGenerator
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import user.repositories.UserRepository
import user.serviceImpl.UserServiceImpl
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith



class UserServiceImplTest: TrakkrrApplicationTests() {
    @Autowired
    private lateinit var userRepository: UserRepository

    //dahil kailangan, need i autowire
    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @BeforeEach
    //kada run ng test delete muna agad ung laman ng repository
    fun setUp() {
        userRepository.deleteAll()
    }



    //we do not test function that does not exist
    //naming of unit test
    //method + result + "given" + condition
    @Test
    fun `create should return user`() {
        //  check if the repository is empty
        assertEquals(0, userRepository.findAll().count());

//        val user = User(
//            firstName = "Brandon",
//            lastName = "Cruz",
//            email = "brandon@brandon.com",
//            userType = "Contractor"
//        );
        val user = EntityGenerator.createUser()

        val createdUser = userServiceImpl.createUser(user);
        assertEquals(1, userRepository.findAll().count());
        assertEquals(user.firstName, createdUser.firstName);
        assertEquals(user.lastName, createdUser.lastName);
        assertEquals(user.email, createdUser.email);
        assertEquals(user.userType, createdUser.userType);
    }

    @Test
    fun `create should fail given duplicate email`() {
        val user = EntityGenerator.createUser()
        userRepository.save(user)

        val duplicateUser = user.copy(
            firstName = "Brenda",
            lastName = "Mage"
        )

        //test if theres an error
        val exception = assertFailsWith<ResponseStatusException> {
            userServiceImpl.createUser(duplicateUser);
        }

        val expectedException = "409 CONFLICT \"Email ${duplicateUser.email} already exists.\"";
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `get user should return user given valid id`() {
        val user = EntityGenerator.createUser()
        val createdUser = userRepository.save(user);

        val userFromServiceImpl = userServiceImpl.getById(createdUser.id!!)
        assertEquals(createdUser.id, userFromServiceImpl.id)
        assertEquals(createdUser.firstName, userFromServiceImpl.firstName)
        assertEquals(createdUser.lastName, userFromServiceImpl.lastName)
        assertEquals(createdUser.userType, userFromServiceImpl.userType)
    }

    @Test
    fun `get user should throw error given invalid id`() {
        val invalidId: Long = 123

        val exception = assertFailsWith<ResponseStatusException> {
            userServiceImpl.getById(invalidId)
        };

        val expectedException = "404 NOT_FOUND \"User with $invalidId does not exist\""
        assertEquals(expectedException, exception.message)
    }

    @Test
    fun `delete users should work`() {
        val user = EntityGenerator.createUser()

        val createdUser = userRepository.save(user)

        assertEquals(1, userRepository.findAll().count())

        userServiceImpl.deleteUser(createdUser.id!!)

        assertEquals(0, userRepository.findAll().count())
    }

    @Test
    fun `update user should return updated user given valid inputs`() {
        val user = EntityGenerator.createUser()
        val original = userRepository.save(user)

        val body = original.copy(
            firstName = "Brenda",
            lastName = "Mage",
            email = "brenda@brenda.com"
        )
        val updatedUser = userServiceImpl.updateUser(body, original.id!!)
        assertEquals(body.firstName, updatedUser.firstName)
        assertEquals(body.lastName, updatedUser.lastName)
        assertEquals(body.email, updatedUser.email)
    }


    @Test
    fun `create user should throw error given invalid user type`() {
        val user = EntityGenerator.createUser().copy(
            userType = "Invalid User Type"
        )

        val exception = assertFailsWith<IllegalArgumentException> {
            userServiceImpl.createUser(user)
        }

        val expectedException = "No enum constant user.utils.UserTypeEnum.INVALID USER TYPE"

        assertEquals(expectedException, exception.message);
    }






}