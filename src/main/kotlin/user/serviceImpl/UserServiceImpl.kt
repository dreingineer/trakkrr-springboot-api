package user.serviceImpl

import user.entities.User
import user.repositories.UserRepository
import user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserServiceImpl(
    private val repository: UserRepository
) : UserService {
    /**
     * Create a user
     * */
    override fun createUser(user: User): User {
        //check if email already exists in the db
        //create a custom query in UserRepository
        if(repository.doesEmailExist(user.email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email ${user.email} already exists.")
        }

        // save the user using the UserRepository
        return repository.save(user);

    }
    /**
     * Get a specific user using an id
     * */
    override fun getById(id: Long): User {
        // find a user in the db and if DNE, throw 404
        // pag walang user; throw error
//        val user = repository.findById(id).orElseThrow {
//            ResponseStatusException(HttpStatus.NOT_FOUND, "User with $id does not exist")
//        };
//        return user;

        //to implement the query in UserRepository
        //get users with contact details
        val user = repository.getUserWithContactDetails(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with $id does not exist")
        };
        return user;

        //to check add user and add contact detail
    }



    override fun updateUser(body: User, id: Long): User {
        //when updating an object
        //1. find the entity first
        //!! required si id
        val user = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with $id does not exist")
        }
        //2. Use save(). If the object does not exist yet in the db,
        //it will insert the object
        //else, it will update the object
        return repository.save(user.copy(
            firstName = body.firstName,
            lastName = body.lastName,
            email = body.email
        ))
    }
//
    override fun deleteUser(id: Long) {
        //1. find the entity
        val user = repository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User with $id does not exist")
        }
        //makes sure service ang nag thothrow ng error hindi si repository/database
        repository.delete(user);
    }

    override fun getAllUsers(): List<User> {
        //findAll returns a Collection

        val users = repository.findAll()
        //from collection to list
        return users.toList();
    }
}

//add users then contact detail
//add user with same email; check if it will throw an error