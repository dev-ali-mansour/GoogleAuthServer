package dev.alimansour.routes

import dev.alimansour.domain.model.ApiResponse
import dev.alimansour.domain.model.EndPoint
import dev.alimansour.domain.model.UserSession
import dev.alimansour.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*

fun Route.deleteUserInfoRoute(
    app: Application,
    userRepository: UserRepository
) {
    authenticate("auth-session") {
        delete(path = EndPoint.DeleteUserInfo.path) {
            val userSession = call.principal<UserSession>()
            userSession?.let {
                runCatching {
                    call.sessions.clear<UserSession>()
                    deleteUserFromDatabase(
                        app = app,
                        userId = userSession.id,
                        userRepository = userRepository
                    )
                }.onFailure { t ->
                    app.log.info("ERROR DELETING USER ${t.message}")
                    call.respond(
                        message = ApiResponse(
                            success = false,
                            message = "Failed to delete the user!"
                        ),
                        status = HttpStatusCode.BadRequest
                    )
                }
            } ?: run {
                app.log.info("INVALID SESSION")
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.deleteUserFromDatabase(
    app: Application,
    userId: String,
    userRepository: UserRepository
) {
    val response = userRepository.deleteUser(userId = userId)
    if (response) {
        app.log.info("USER SUCCESSFULLY DELETED")
        call.respond(
            message = ApiResponse(
                success = true,
                message = "Successfully Deleted!"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        app.log.info("ERROR DELETING THE USER")
        call.respond(
            message = ApiResponse(
                success = false,
                message = "failed to delete user!"
            ),
            status = HttpStatusCode.BadRequest
        )
    }
}