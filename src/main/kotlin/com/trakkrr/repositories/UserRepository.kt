package com.trakkrr.repositories

import com.trakkrr.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

// Extend CrudRepository
// Basic db operations

@Repository
interface UserRepository: CrudRepository<User, Long> {
//    fun add() // add user
//    fun delete() // delete a user
//    fun update() //
//    fun getById()
//    fun getAll()

    //calls or sql commands
    @Query(
        """
           SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END
           FROM User u WHERE u.email = :email
        """
    )
    fun doesEmailExist(email: String) : Boolean


    @Query(
        """
            SELECT u FROM User u
            LEFT JOIN FETCH u.contactDetails cd
            WHERE u.id = :id
        """
    )
    fun getUserWithContactDetails(id:Long) : Optional<User>
}

//gitlab.com/reddpanes/trakkrr-springboot-api
//https://docs.google.com/document/d/1tmjPovu3zUFA-uVIfx0hFRN9_sWwwX5HL2TdTjFDF18/edit