package dev.matytyma.dao

import org.jetbrains.exposed.sql.Table

object Classrooms : Table("classroom") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val room = reference("room", Rooms.id)
    val teacher = reference("teacher", Teachers.id)

    override val primaryKey = PrimaryKey(id)
}
