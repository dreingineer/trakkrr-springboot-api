package user.repositories

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import user.entities.ContactDetail
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface ContactDetailRepository: CrudRepository<ContactDetail, Long> {

    // Use if updating, or deleting
    @Modifying
    // para maroll back ung data / operations pag nagka problem
    // transactions
    @Transactional
    @Query(
        """
              UPDATE ContactDetail c SET c.isPrimary = FALSE
              WHERE c.user.id = :userId
        """
    )
    //unit ay void
    fun updateIsPrimaryToFalse(userId: Long): Unit

    @Query(
        """
            SELECT cd.contactDetails FROM ContactDetail cd
            INNER JOIN cd.user u
            WHERE cd.user.id = :userId
        """
    )
    fun getContactDetailsByUserId(userId: Long): List<ContactDetail>

    @Query(
        """
            SELECT COUNT(contactDetails) FROM ContactDetail cd WHERE cd.user.id = :userId
        """
    )
    fun countContactDetailsByUserId(userId: Long): Int
}