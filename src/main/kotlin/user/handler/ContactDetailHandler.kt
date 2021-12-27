package user.handler

import user.entities.ContactDetail
import user.service.ContactDetailService
import org.springframework.web.bind.annotation.*

@RestController
class ContactDetailHandler(private val contactDetailService: ContactDetailService) {
    @PostMapping("/api/contact-detail/{userId}")
    fun createContactDetail(@RequestBody contactDetail: ContactDetail, @PathVariable("userId") userId: Long): ContactDetail {
        return contactDetailService.createContactDetail(contactDetail, userId);
    }

    // mag aadd ka muna ng user tapos contact-detail

    @GetMapping("/api/contact-detail/{id}")
    fun getContactDetailById(@PathVariable("id") id: Long): ContactDetail {
        return contactDetailService.getContactDetailById(id);
    }

    @PutMapping("/api/contact-detail/{id}")
    fun updateContactDetailById(@RequestBody body: ContactDetail, @PathVariable("id") id:Long): ContactDetail {
       return contactDetailService.updateContactDetail(body, id);
    }

    @DeleteMapping("/api/contact-detail/{id}")
    fun deleteContactDetail(@PathVariable("id") id:Long) {
        return contactDetailService.deleteContactDetail(id);
    }

    @GetMapping("/api/contact-details")
    fun getAllContactDetails(): List<ContactDetail> {
        return contactDetailService.getAllContactDetails()
    }
}