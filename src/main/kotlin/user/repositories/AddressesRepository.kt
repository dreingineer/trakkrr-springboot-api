package user.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import user.entities.Addresses

@Repository
interface AddressesRepository: CrudRepository<Addresses, Long> {

}