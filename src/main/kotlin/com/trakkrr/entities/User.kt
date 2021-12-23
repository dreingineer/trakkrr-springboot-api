package com.trakkrr.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

// Entity -> representation of a data in our table
// File names -> Pascal Case

@Entity(name = "User")
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // Long -> BIGINT

    @Column(
        nullable = false,
        updatable = true,
        name = "firstName"
    )
    var firstName: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "lastName"
    )
    var lastName: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "email"
    )
    var email: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "isActive"
    )
    var isActive: Boolean = true,
    @Column(
        nullable = false,
        updatable = true,
        name = "userType"
    )
    var userType: String,

    //JsonIgnoreProperties ignore nya ung values na user
    //allowSetters
    @JsonIgnoreProperties(value = ["user"], allowSetters = true)
    //mamap kanino ang mapped by
    //ung user na column sa contact detail
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val contactDetails: List<ContactDetail> = mutableListOf()

)
