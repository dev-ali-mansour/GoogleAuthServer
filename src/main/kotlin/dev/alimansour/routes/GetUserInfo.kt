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

fun Route.getUserInfoRoute(
    app: Application,
    userRepository: UserRepository
) {
    authenticate("auth-session") {
        get(path = EndPoint.GetUserInfo.path) {
            val userSession = call.principal<UserSession>()
            userSession?.let {
                runCatching {
                    call.respond(
                        message = ApiResponse(
                            success = true,
                            user = userRepository.getUserInfo(userSession.id)
                        ),
                        status = HttpStatusCode.OK
                    )
                }.onFailure { t ->
                    app.log.info("GETTING USER INFO ERROR: ${t.message}")
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            } ?: run {
                app.log.info("INVALID SESSION")
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
    }
}