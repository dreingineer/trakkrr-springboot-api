package user.entities

import javax.persistence.*

@Entity(name = "Addresses")
@Table(name= "addresses")
data class Addresses(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(
        nullable = false,
        updatable = true,
        name = "street"
    )
    var street: String,
    @Column(
        nullable = false,
        updatable = true,
        name = "barangay"
    )
    var barangay: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "city"
    )
    var city: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "province"
    )
    var province: String,

    @Column(
        nullable = false,
        updatable = true,
        name = "zipCode"
    )
    var zipCode: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(nullable = true)
    var user: User? = null
)
