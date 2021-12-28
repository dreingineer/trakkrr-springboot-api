package user.service

import user.entities.ContactDetail

interface ContactDetailService {
    fun createContactDetail(bodyCd: ContactDetail, userId: Long): ContactDetail
    fun getContactDetailById(id: Long): ContactDetail
    fun updateContactDetail(bodyCd: ContactDetail, id:Long): ContactDetail
    fun deleteContactDetail(id: Long)
    fun getAllContactDetails(): List<ContactDetail>
}