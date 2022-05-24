package dev.alimansour.routes

import dev.alimansour.domain.model.ApiResponse
import dev.alimansour.domain.model.EndPoint
import dev.alimansour.domain.model.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.signOutRoute() {
    authenticate("auth-session") {
        get(path = EndPoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(
                    success = true,
                    message = "See You Later!"
                ),
                status = HttpStatusCode.OK
            )
        }
    }
}