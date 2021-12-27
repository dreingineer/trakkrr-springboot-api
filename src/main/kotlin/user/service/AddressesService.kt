package user.service

import user.entities.Addresses

interface AddressesService {
    fun createAddress(body: Addresses, userId: Long): Addresses
    fun getAddressById(id:Long): Addresses
    fun updateAddress(body: Addresses, id: Long): Addresses
    fun deleteAddress(id: Long)
    fun getAllAddresses(): List<Addresses>
}