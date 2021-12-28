package user.serviceImpl

import user.entities.ContactDetail
import user.repositories.ContactDetailRepository
import user.repositories.UserRepository
import user.service.ContactDetailService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import user.utils.Constants.MAX_CONTACT_DETAILS
import javax.transaction.Transactional

@Service
class ContactDetailServiceImpl(
    private val repository: ContactDetailRepository,
    private val userRepository: UserRepository
) : ContactDetailService {
    /**
     * Create contact detail
     * */

    @Transactional
    override fun createContactDetail(bodyCd: ContactDetail, userId: Long): ContactDetail {
        //Find the user
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: $userId does not exist!")
        }

        //check if it's the 4th contact detail

        //get all contact details by userId
//        val contactDetails = repository.getContactDetailsByUserId(userId)
//        if(contactDetails.size > MAX_CONTACT_DETAILS) {
//          throw ResponseStatusException (HttpStatus.BAD_REQUEST, "You cannot create more than four contact details")
//        }

        val contactDetailsCount = repository.countContactDetailsByUserId(userId)
//        if(contactDetailsCount >= 3) { //instead of this dapat naka constants
        if(contactDetailsCount >= MAX_CONTACT_DETAILS) {
            throw ResponseStatusException (HttpStatus.BAD_REQUEST, "You cannot create more than four contact details")
        }

        if(bodyCd.isPrimary) {
            //create query that will update the value of all contactDetails isPrimary = false
            //go to repo of contactDEtails
            repository.updateIsPrimaryToFalse(userId);
        }

        //contact detail lang
        //return repository.save(contactDetail);

        //december 28 implem
        //check the value of isPrimary
//        val isPrimary = contactDetail.isPrimary //not needed

        //eto pag 2 entity na may relation
        return repository.save(bodyCd.copy(
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
    override fun updateContactDetail(bodyCd: ContactDetail, id: Long): ContactDetail {
        val contactDetail = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Detail with $id does not exist")
        }

        return repository.save(contactDetail.copy(
            contactDetails = bodyCd.contactDetails,
            contactType = bodyCd.contactType
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