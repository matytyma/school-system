package dev.matytyma.dao

import org.jetbrains.exposed.sql.Table

object Students : Table("student") {
    val id = integer("id").autoIncrement()
    val classroom = reference("classroom", Classrooms.id)

    override val primaryKey = PrimaryKey(id)
}
