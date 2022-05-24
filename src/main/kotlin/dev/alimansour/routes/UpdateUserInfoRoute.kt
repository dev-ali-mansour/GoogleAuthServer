package dev.alimansour.routes

import dev.alimansour.domain.model.UserUpdate
import dev.alimansour.domain.model.ApiResponse
import dev.alimansour.domain.model.EndPoint
import dev.alimansour.domain.model.UserSession
import dev.alimansour.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Route.updateUserInfoRoute(
    app: Application,
    userRepository: UserRepository
) {
    authenticate("auth-session") {
        put(path = EndPoint.UpdateUserInfo.path) {
            val userSession = call.principal<UserSession>()
            val userUpdate = call.receive<UserUpdate>()
            userSession?.let {
                runCatching {
                    updateUserInfo(
                        app = app,
                        userId = userSession.id,
                        userUpdate = userUpdate,
                        userRepository = userRepository
                    )
                }.onFailure { t ->
                    app.log.info("UPDATING USER INFO ERROR: ${t.message}")
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            } ?: run {
                app.log.info("INVALID SESSION")
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.updateUserInfo(
    app: Application,
    userId: String,
    userUpdate: UserUpdate,
    userRepository: UserRepository,
) {
    val response = userRepository.updateUserInfo(
        userId = userId,
        firstName = userUpdate.firstName,
        lastName = userUpdate.lastName
    )
    if (response) {
        app.log.info("USER SUCCESSFULLY UPDATED")
        call.respond(
            message = ApiResponse(
                success = true,
                message = "Successfully Updated!"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR UPDATING THE USER")
        call.respond(
            message = ApiResponse(
                success = false,
                message = "Failed to update the user!"
            ),
            status = HttpStatusCode.BadRequest
        )
    }
}