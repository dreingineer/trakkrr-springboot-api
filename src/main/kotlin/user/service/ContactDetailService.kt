package user.service

import user.entities.ContactDetail

interface ContactDetailService {
    fun createContactDetail(body: ContactDetail, userId: Long): ContactDetail
    fun getContactDetailById(id: Long): ContactDetail
    fun updateContactDetail(body: ContactDetail, id:Long): ContactDetail
    fun deleteContactDetail(id: Long)
    fun getAllContactDetails(): List<ContactDetail>
}