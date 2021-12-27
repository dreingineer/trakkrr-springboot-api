package user

import TrakkrrApplicationTests
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.server.ResponseStatusException
import user.repositories.AddressesRepository
import user.serviceImpl.AddressesServiceImpl
import user.serviceImpl.UserServiceImpl
import utils.EntityGenerator
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AddressesServiceImplTest: TrakkrrApplicationTests() {
    @Autowired
    private lateinit var addressesRepository: AddressesRepository

    @Autowired
    private lateinit var addressesServiceImpl: AddressesServiceImpl

    @Autowired
    private lateinit var userRepository: AddressesRepository

    @Autowired
    private lateinit var userServiceImpl: UserServiceImpl

    @BeforeEach
    // kada run ng test delete muna agad ung laman ng repo
    fun setUp() {
        addressesRepository.deleteAll()
    }

    @Test
    fun `create address should return address`() {
        //check if user repo is empty
        assertEquals(0, userRepository.findAll().count())

        //check if address repo is empty
        assertEquals(0, addressesRepository.findAll().count())

        //create user, address needs a user
        val user = EntityGenerator.createUser()
        //create address
        val add = EntityGenerator.createAddresses(user)

        val createdUser = userServiceImpl.createUser(user)
        val createdAddress = addressesRepository.save(add.copy(
            user = createdUser
        ))

        assertEquals(1, addressesRepository.findAll().count());
        assertEquals(add.street, createdAddress.street);
        assertEquals(add.barangay, createdAddress.barangay);
        assertEquals(add.city, createdAddress.city);
        assertEquals(add.province, createdAddress.province);
        assertEquals(add.zipCode, createdAddress.zipCode);
    }

    @Test
    fun `get address should return address given valid id`() {
        val user = EntityGenerator.createUser()
        val address = EntityGenerator.createAddresses(user)

        val createdUser = userServiceImpl.createUser(user)
        val createdAddress = addressesRepository.save(address.copy(
            user = createdUser
        ))

        val addressFromServiceImpl = addressesServiceImpl.getAddressById(createdAddress.id!!)
        assertEquals(createdAddress.id, addressFromServiceImpl.id);
        assertEquals(createdAddress.street, addressFromServiceImpl.street);
        assertEquals(createdAddress.barangay, addressFromServiceImpl.barangay);
        assertEquals(createdAddress.city, addressFromServiceImpl.city);
        assertEquals(createdAddress.province, addressFromServiceImpl.province);
        assertEquals(createdAddress.zipCode, addressFromServiceImpl.zipCode);

    }

    @Test
    fun `get address should throw error given an invalid id`() {
        val invalidId: Long = 888

        val exceptionAddress = assertFailsWith<ResponseStatusException> {
            addressesServiceImpl.getAddressById(invalidId);
        }

        val expectedException = "404 NOT_FOUND \"Address with $invalidId does not exist\"";
        assertEquals(expectedException, exceptionAddress.message);
    }

    @Test
    fun `delete address should work`() {
        val user = EntityGenerator.createUser();
        val createdUser = userServiceImpl.createUser(user);
        val add = EntityGenerator.createAddresses(createdUser);
        val createdAddress = addressesRepository.save(add);

        assertEquals(1, addressesRepository.findAll().count());
        addressesServiceImpl.deleteAddress(createdAddress.id!!);

        assertEquals(0, addressesRepository.findAll().count());

    }

    @Test
    fun `update address should return updated address given valid inputs`() {
        //create a user
        val user = EntityGenerator.createUser();
        //create an address
        val createdAddress = EntityGenerator.createAddresses(user);

        val createdUser = userServiceImpl.createUser(user);
        val originalAddress = addressesRepository.save(createdAddress.copy(
            user = createdUser
        ))

        val body = originalAddress.copy(
            street = "new jfabella",
            barangay = "new mauway",
            city = "new mandaluyong",
            province = "new province",
            zipCode = "1550 v2"
        )

        val updatedAddress = addressesServiceImpl.updateAddress(body, originalAddress.id!!)
        assertEquals(body.street, updatedAddress.street);
        assertEquals(body.barangay, updatedAddress.barangay);
        assertEquals(body.city, updatedAddress.city);
        assertEquals(body.province, updatedAddress.province);
        assertEquals(body.zipCode, updatedAddress.zipCode);
    }
}