package dev.matytyma.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.or

object Lessons : Table("lesson") {
    val id = integer("id").autoIncrement()
    val classroom = reference("classroom", Classrooms.id).nullable()
    val group = reference("group", Classrooms.id).nullable()
    val date = date("date")
    val index = short("index")
    val subject = reference("subject", Subjects.id)
    val teacher = reference("teacher", Teachers.id)
    val topic = varchar("topic", 100).nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(classroom, group, date, index)
        check { (classroom neq null) or (group neq null) }
    }
}
