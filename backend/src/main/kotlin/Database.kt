package dev.matytyma

import dev.matytyma.schema.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.setupDatabase() {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Absence,
            Classrooms,
            GradeColumns,
            Grades,
            Groups,
            Rooms,
            Students,
            Teachers,
        )
    }
}
