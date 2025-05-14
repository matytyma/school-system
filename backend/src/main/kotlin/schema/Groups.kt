package dev.matytyma.schema

import org.jetbrains.exposed.sql.Table

object Groups : Table("student_group") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(id)
}
