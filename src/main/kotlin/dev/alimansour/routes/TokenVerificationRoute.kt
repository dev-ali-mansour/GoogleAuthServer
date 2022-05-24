package dev.alimansour.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import dev.alimansour.model.ApiRequest
import dev.alimansour.model.EndPoint
import dev.alimansour.model.UserSession
import dev.alimansour.util.Constants.AUDIENCE
import dev.alimansour.util.Constants.ISSUER
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.tokenVerificationRoute(app: Application) {
    post(EndPoint.TokenVerification.path) {
        val request = call.receive<ApiRequest>()
        if (request.tokenId.isNotEmpty()) {
            val result = verifyGoogleTokenId(request.tokenId)
            result?.let {
                val name = result.payload["name"].toString()
                val emailAddress = result.payload["email"].toString()
                app.log.info("TOKEN SUCCESSFULLY VERIFIED: $name, $emailAddress")

                call.sessions.set(UserSession(id = "123", name = "Ali Mansour"))
                call.respondRedirect(EndPoint.Authorized.path)
            } ?: run {
                app.log.info("TOKEN VERIFICATION FAILED")
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        } else {
            app.log.info("EMPTY TOKEN ID")
            call.respondRedirect(EndPoint.Unauthorized.path)
        }
    }
}

fun verifyGoogleTokenId(tokenId: String): GoogleIdToken? =
    runCatching {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(AUDIENCE))
            .setIssuer(ISSUER)
            .build()
        verifier.verify(tokenId)
    }.getOrNull()