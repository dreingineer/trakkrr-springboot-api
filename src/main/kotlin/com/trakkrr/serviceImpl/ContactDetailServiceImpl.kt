package com.trakkrr.serviceImpl

import com.trakkrr.entities.ContactDetail
import com.trakkrr.repositories.ContactDetailRepository
import com.trakkrr.repositories.UserRepository
import com.trakkrr.service.ContactDetailService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ContactDetailServiceImpl(
    private val repository: ContactDetailRepository,
    private val userRepository: UserRepository
) : ContactDetailService {
    /**
     * Create contact detail
     * */
    override fun createContactDetail(contactDetail: ContactDetail, userId: Long): ContactDetail {
        //Find the user
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: $userId does not exist!")
        }
        //contact detail lang
        //return repository.save(contactDetail);

        //eto pag 2 entity na may relation
        return repository.save(contactDetail.copy(
            user = user
        ))
    }

    /**
     * Get a specific contact detail using id
     * */
    override fun getContactDetailById(id: Long): ContactDetail {
        val contactDetail = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Detail with $id does not exist")
        };
        return contactDetail;
    }

    /**
     * update contact detail by id
     * */
    override fun updateContactDetail(body: ContactDetail, id: Long): ContactDetail {
        val contactDetail = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Detail with $id does not exist")
        }

        return repository.save(contactDetail.copy(
            contactDetails = body.contactDetails,
            contactType = body.contactType
        ))
    }

    /**
     * delete contact detail by id
     * */
    override fun deleteContactDetail(id: Long) {
        val contactDetail = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Detail with $id does not exist")
        }

        repository.delete(contactDetail);
    }

    /**
     * list all contact detail
     * */
    override fun getAllContactDetails(): List<ContactDetail> {
        val contactDetails = repository.findAll()
        return contactDetails.toList();
    }
}