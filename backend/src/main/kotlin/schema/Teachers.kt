package dev.matytyma.schema

import org.jetbrains.exposed.sql.Table

object Teachers : Table("teacher") {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val shortName = varchar("short_name", 5)
    val prefixTitle = varchar("prefix_title", 50).nullable()
    val suffixTitle = varchar("suffix_title", 50).nullable()

    override val primaryKey = PrimaryKey(id)
}
