package dev.alimansour.plugins

import dev.alimansour.routes.authorizedRoute
import dev.alimansour.routes.rootRoute
import dev.alimansour.routes.tokenVerificationRoute
import dev.alimansour.routes.unauthorizedRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        rootRoute()
        tokenVerificationRoute()
        authorizedRoute()
        unauthorizedRoute()
    }
}
