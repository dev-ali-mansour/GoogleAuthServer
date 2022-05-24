package dev.alimansour.di

import dev.alimansour.data.repository.UserRepositoryImpl
import dev.alimansour.domain.repository.UserRepository
import dev.alimansour.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(DATABASE_NAME)
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}