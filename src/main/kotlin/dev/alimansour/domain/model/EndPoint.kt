package dev.alimansour.domain.model

sealed class EndPoint(val path: String) {
    object Root : EndPoint(path = "/")
    object TokenVerification : EndPoint(path = "/token_verification")
    object GetUserInfo : EndPoint(path = "/get_user")
    object UpdateUserInfo : EndPoint(path = "/update_user")
    object DeleteUserInfo : EndPoint(path = "/delete_user")
    object SignOut : EndPoint(path = "/sign_out")
    object Unauthorized : EndPoint(path = "/unauthorized")
    object Authorized : EndPoint(path = "/authorized")
}
