package dev.matytyma.dao

import dev.matytyma.model.Day
import dev.matytyma.model.SchedulePattern
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.or

object LessonSchedules : Table("lesson_schedule") {
    val classroom = reference("classroom", Classrooms.id).nullable()
    val group = reference("group", Groups.id).nullable()
    val day = enumeration<Day>("day")
    val index = short("index")
    val subject = reference("subject", Subjects.id)
    val room = reference("room", Rooms.id)
    val teacher = reference("teacher", Teachers.id)
    val pattern = enumeration<SchedulePattern>("pattern")

    init {
        uniqueIndex(classroom, group, day, index)
        check { (classroom neq null) or (group neq null) }
    }
}
