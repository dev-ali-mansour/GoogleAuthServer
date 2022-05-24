package dev.alimansour.plugins

import dev.alimansour.domain.repository.UserRepository
import dev.alimansour.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {

    routing {
        val userRepository: UserRepository by inject(UserRepository::class.java)

        rootRoute()
        tokenVerificationRoute(application, userRepository)
        getUserInfoRoute(application, userRepository)
        updateUserInfoRoute(application, userRepository)
        deleteUserInfoRoute(application, userRepository)
        signOutRoute()
        authorizedRoute()
        unauthorizedRoute()
    }
}
