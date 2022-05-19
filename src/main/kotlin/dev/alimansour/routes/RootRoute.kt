package dev.alimansour.routes

import dev.alimansour.model.EndPoint
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.rootRoute(){
    get(EndPoint.Root.path) {
        call.respondText("Welcome to Ktor Server!")
    }
}