package dev.alimansour.plugins

import dev.alimansour.domain.repository.UserRepository
import dev.alimansour.routes.authorizedRoute
import dev.alimansour.routes.rootRoute
import dev.alimansour.routes.tokenVerificationRoute
import dev.alimansour.routes.unauthorizedRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {

    routing {
        val userRepository: UserRepository by inject(UserRepository::class.java)

        rootRoute()
        tokenVerificationRoute(application, userRepository)
        authorizedRoute()
        unauthorizedRoute()
    }
}
