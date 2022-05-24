package dev.alimansour.data.repository

import dev.alimansour.domain.model.User
import dev.alimansour.domain.repository.UserRepository
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class UserRepositoryImpl(
    database: CoroutineDatabase
) : UserRepository {
    private val users = database.getCollection<User>()

    override suspend fun getUserInfo(userId: String): User? =
        users.findOne(filter = User::id eq userId)

    override suspend fun saveUserInfo(user: User): Boolean {
        val existingUser = users.findOne(filter = User::id eq user.id)
        return if (existingUser == null)
            users.insertOne(document = user).wasAcknowledged()
        else true
    }

    override suspend fun deleteUser(userId: String): Boolean =
        users.deleteOne(filter = User::id eq userId).wasAcknowledged()

    override suspend fun updateUserInfo(
        userId: String,
        firstName: String,
        lastName: String
    ): Boolean =
        users.updateOne(
            filter = User::id eq userId,
            update = setValue(
                property = User::name,
                value = "$firstName $lastName"
            )
        ).wasAcknowledged()
}