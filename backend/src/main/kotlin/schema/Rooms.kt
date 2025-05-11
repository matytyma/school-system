package dev.matytyma.schema

import org.jetbrains.exposed.sql.Table

object Rooms : Table("room") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50).nullable()
    val shortName = varchar("short_name", 4).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}
