package com.mwojnar.studentcalendar.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.mwojnar.studentcalendar.helpers.ValueDropdownItem
import java.util.*

interface EntityClass {
    fun getId(): Long?
}

interface ToValueItemConvertible {
    fun toValueDropdownItem(): ValueDropdownItem
}

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var courseId: Long? = null,
    var name: String = "",
    var iconId: Int? = null,
    var colorId: Int? = null
) : EntityClass, ToValueItemConvertible {
    override fun getId() = courseId

    override fun toValueDropdownItem() =
        ValueDropdownItem(this.name, this.courseId!!)
}

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var personId: Long? = null,
    var firstName: String? = null,
    var lastName: String = "",
    var title: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var location: String? = null,
    var moreInfo: String? = null
) : EntityClass, ToValueItemConvertible {
    override fun getId() = personId

    override fun toString(): String {
        var titleNameText = ""
        title?.let { titleNameText += "$it " }
        firstName?.let { titleNameText += "$it " }
        titleNameText += lastName
        return titleNameText
    }

    override fun toValueDropdownItem() =
        ValueDropdownItem(this.toString(), personId!!)
}

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long? = null,
    var courseId: Long = 0L,
    var type: String? = null,
    var whenTime: Date? = null,
    var weekday: Int? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var personId: Long? = null,
    var location: String? = null,
    var moreInfo: String? = null
) : EntityClass {
    override fun getId() = scheduleId
}

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true)
    var testId: Long? = null,
    var courseId: Long = 0L,
    var type: String? = null,
    var subject: String = "",
    var location: String? = null,
    var whenDateTime: Date? = null,
    var moreInfo: String? = null
) : EntityClass {
    override fun getId() = testId
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long? = null,
    var courseId: Long? = null,
    var title: String = "",
    var priority: Int? = null,
    var location: String? = null,
    var whenDateTime: Date? = null,
    var reminder: Long? = null,
    var moreInfo: String? = null
) : EntityClass {
    override fun getId() = taskId
}

data class TestAndCourse(
    @Embedded var test: Test,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var course: Course
) : EntityClass {
    override fun getId() = test.testId
}

data class ScheduleAndCourseAndPerson(
    @Embedded var schedule: Schedule,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var course: Course,

    @Relation(
        parentColumn = "personId",
        entityColumn = "personId"
    )
    var person: Person?
) : EntityClass {
    override fun getId() = schedule.scheduleId
}

data class TaskAndCourse(
    @Embedded var task: Task,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var course: Course?
) : EntityClass {
    override fun getId() = task.taskId
}