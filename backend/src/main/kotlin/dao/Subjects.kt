package dev.matytyma.dao

import org.jetbrains.exposed.sql.Table

object Subjects : Table("subject") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val shortName = varchar("short_name", 4)

    override val primaryKey = PrimaryKey(id)
}
