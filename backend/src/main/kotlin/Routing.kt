package dev.matytyma

import dev.matytyma.route.teachers
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.registerRoutes() {
    routing {
        teachers()
    }
}
