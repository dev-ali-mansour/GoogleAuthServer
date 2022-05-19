package dev.alimansour.plugins

import dev.alimansour.model.EndPoint
import dev.alimansour.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureAuth() {
    install(Authentication) {
        session<UserSession>(name = "auth-session") {
            validate { session ->
                session
            }
            challenge {
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
    }
}