package com.trakkrr.handler

import com.trakkrr.entities.User
import com.trakkrr.service.UserService
import org.springframework.web.bind.annotation.*

// responsible for setting up the routes
// defining the body or parameters needed by the service layer

@RestController
class UserHandler(
    private val userService: UserService
) {
    @PostMapping("/api/user")
    fun createUser(@RequestBody user: User): User {
        return userService.createUser(user);
    }

    @GetMapping("/api/user/{id}")
    fun getUserById(@PathVariable("id") id: Long): User {
        return userService.getById(id);
    }

    @PutMapping("/api/user/{id}")
    fun updateUser(@RequestBody body:User, @PathVariable("id") id: Long): User {
        return userService.updateUser( body, id);
    }

    @DeleteMapping("/api/user/{id}")
    fun deleteUser(@PathVariable("id") id: Long) {
        return userService.deleteUser(id)
    }

    @GetMapping("/api/users")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers();
    }
}

//1. create entity
//2. create SQL
//3. create Repository
//4. create Service
//5. Implement service
//6. Create Handler
// moving forward, repeat steps 4-6, revise step 1-2 only if
// there are changes in the db structure

//trakkrr-springboot-api
//https://docs.google.com/document/d/1tmjPovu3zUFA-uVIfx0hFRN9_sWwwX5HL2TdTjFDF18/edit


//push sa git
//pag create ng new repo from intellij ?????
//git add -A
//git commit -m "sample commit"
//git push origin master