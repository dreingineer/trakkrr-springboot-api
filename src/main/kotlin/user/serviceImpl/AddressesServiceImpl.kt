package user.serviceImpl


import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import user.entities.Addresses
import user.entities.User
import user.repositories.AddressesRepository
import user.repositories.UserRepository
import user.service.AddressesService

@Service
class AddressesServiceImpl(
    private val repository: AddressesRepository,
    private val userRepository: UserRepository
): AddressesService {
    /**
     * Create address detail
     * */
    override fun createAddress(body: Addresses, userId: Long): Addresses {
        //Find the user
        val user: User = userRepository.findById(userId).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: $userId does not exist!")
        }

        return repository.save(body.copy(
            user = user
        ))
    }

    /**
     * Get a specific contact detail using id
     * */
    override fun getAddressById(id: Long): Addresses {
        val address: Addresses = repository.findById(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Detail with $id does not exist")
        };
        return address;
    }

    /**
     * update address detail by id
     * */
    override fun updateAddress(body: Addresses, id: Long): Addresses {
        val address: Addresses = repository.findById(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Address with $id does not exist")
        }

        return repository.save(address.copy(
            street = body.street,
            barangay = body.barangay,
            city = body.city,
            province = body.province,
            zipCode = body.zipCode
        ))
    }

    /**
     * delete address by id
     * */
    override fun deleteAddress(id: Long) {
        val addressToBeDeleted = repository.findById(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Address with $id does not exist")
        }

        repository.delete(addressToBeDeleted);
    }

    /**
     * List all address
     * */
    override fun getAllAddresses(): List<Addresses> {
        val addresses = repository.findAll()
        return addresses.toList()
    }

}