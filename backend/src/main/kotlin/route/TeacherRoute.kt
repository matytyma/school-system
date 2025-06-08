package dev.matytyma.route

import dev.matytyma.dao.Teachers
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class Teacher(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val prefixTitle: String?,
    val suffixTitle: String?,
)

fun Route.teachers() {
    route("/teacher") {
        get("/list") {
            call.respond(transaction {
                Teachers.selectAll().map {
                    Teacher(
                        it[Teachers.id],
                        it[Teachers.firstName],
                        it[Teachers.lastName],
                        it[Teachers.prefixTitle],
                        it[Teachers.suffixTitle],
                    )
                }
            })
        }
    }
}
