package dev.matytyma.schema

import dev.matytyma.model.GradeType
import org.jetbrains.exposed.sql.Table

object Grades : Table("grade") {
    val column = reference("column", GradeColumns.id)
    val student = reference("student", Students.id)
    val grade = enumeration<GradeType>("grade")

    override val primaryKey = PrimaryKey(column, student)
}
