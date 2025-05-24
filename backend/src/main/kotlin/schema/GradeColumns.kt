package dev.matytyma.schema

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.or

object GradeColumns : Table("grade_column") {
    val id = integer("id").autoIncrement().uniqueIndex()
    val classroom = reference("classroom", Classrooms.id).nullable()
    val group = reference("group", Groups.id).nullable()
    val index = integer("index")
    val name = varchar("name")
    val weight = integer("weight")
    val assignmentDate = date("assignment_date")
    val description = varchar("description").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(classroom, group, index)
        check { (classroom neq null) or (group neq null) }
    }
}
