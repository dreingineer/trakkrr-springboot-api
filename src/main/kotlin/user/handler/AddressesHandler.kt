package user.handler

import org.springframework.web.bind.annotation.*
import user.entities.Addresses
import user.service.AddressesService

@RestController
class AddressesHandler(private val addressesService: AddressesService) {
    @PostMapping("/api/address/{userId}")
    fun createAddress(@RequestBody address:Addresses, @PathVariable("userId") userId: Long): Addresses {
        return  addressesService.createAddress(address, userId);
    }

    // add user muna bago mag dagdag ng address

    @GetMapping("/api/address/{id}")
    fun getAddressById(@PathVariable("id") id: Long): Addresses {
        return addressesService.getAddressById(id);
    }

    @PutMapping("/api/address/{id}")
    fun updateAddressById(@RequestBody body:Addresses, @PathVariable("id") id:Long): Addresses {
        return addressesService.updateAddress(body, id);
    }

    @DeleteMapping("/api/address/{id}")
    fun deleteAddress(@PathVariable("id") id:Long) {
        return addressesService.deleteAddress(id);
    }

    @GetMapping("/api/addresses")
    fun getAllAddresses(): List<Addresses> {
        return addressesService.getAllAddresses();
    }
}