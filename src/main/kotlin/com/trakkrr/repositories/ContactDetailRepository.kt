package com.trakkrr.repositories

import com.trakkrr.entities.ContactDetail
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ContactDetailRepository: CrudRepository<ContactDetail, Long> {

}