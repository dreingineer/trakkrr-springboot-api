package com.trakkrr.entities

import javax.persistence.*

@Entity(name = "ContactDetail")
@Table(name = "contactDetail")
data class ContactDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(
        nullable = false,
        updatable = true,
        name = "contactDetails"
    )
    var contactDetails: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "contactType"
    )
    var contactType: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "isPrimary"
    )
    var isPrimary: Boolean = true,

    //relationship ; pounter to another realtionship
    //gusto natin maconnect ung connection agad with user
    //fetch type lazy hindi niya ifefetch un hanggat hindi mo siya ginagamit
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    //pwede maging null
    @JoinColumn(nullable = true)
    var user: User? = null
)

//1. create entity done
//2. create SQL done 14:47
//3. create Repository done 14:49
//4. create Service done 14:58
//5. Implement service
//6. Create Handler
// moving forward, repeat steps 4-6, revise step 1-2 only if
// there are changes in the db structure