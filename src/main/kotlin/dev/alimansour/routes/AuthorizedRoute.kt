package dev.alimansour.routes

import dev.alimansour.domain.model.ApiResponse
import dev.alimansour.domain.model.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorizedRoute() {
    authenticate("auth-session") {
        get(path = EndPoint.Authorized.path) {
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }
    }
}