package dev.matytyma.schema

import dev.matytyma.model.AbsenceType
import org.jetbrains.exposed.sql.Table

object Absence : Table("absence") {
    val student = reference("student", Students.id)
    val lesson = reference("lesson", Lessons.id)
    val type = enumeration<AbsenceType>("type")

    override val primaryKey = PrimaryKey(student, lesson)
}
