package dev.alimansour.routes

import dev.alimansour.model.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.unauthorizedRoute() {
    get(path = EndPoint.Unauthorized.path) {
        call.respond(
            message = "Not Authorized",
            status = HttpStatusCode.Unauthorized
        )
    }
}