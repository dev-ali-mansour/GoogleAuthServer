package dev.alimansour.routes

import dev.alimansour.model.EndPoint
import dev.alimansour.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.tokenVerificationRoute() {
    post(EndPoint.TokenVerification.path) {
        call.sessions.set(UserSession(id = "123", name = "Ali Mansour"))
        call.respondRedirect(EndPoint.Authorized.path)
    }
}