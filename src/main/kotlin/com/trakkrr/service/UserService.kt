package com.trakkrr.service

import com.trakkrr.entities.User

interface UserService {
    fun createUser(body: User): User
    fun getById(id: Long): User
    fun updateUser(body: User, id: Long): User
    fun deleteUser(id: Long)
    fun getAllUsers(): List<User>
}