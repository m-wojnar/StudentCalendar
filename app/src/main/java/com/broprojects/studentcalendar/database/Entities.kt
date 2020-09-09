package com.broprojects.studentcalendar.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.broprojects.studentcalendar.helpers.ValueDropdownItem
import java.util.*

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var courseId: Long? = null,
    var name: String = "",
    var iconId: Int? = null,
    var colorId: Int? = null
)

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
) {
    override fun toString() = "$lastName ${firstName ?: ""}"
}

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long? = null,
    var courseId: Long = 0L,
    var type: String? = null,
    var whenTime: Date? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var personId: Long? = null,
    var location: String? = null,
    var moreInfo: String? = null
)

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
)

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long? = null,
    var courseId: Long? = null,
    var title: String = "",
    var priority: Long? = null,
    var location: String? = null,
    var whenDateTime: Date? = null,
    var reminder: Long? = null,
    var moreInfo: String? = null
)

data class CourseWithTests(
    @Embedded var course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var tests: List<Test>
)

data class CourseWithSchedules(
    @Embedded var course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var schedules: List<Schedule>
)

data class CourseWithTasks(
    @Embedded var course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var tasks: List<Task>
)

data class PersonWithSchedulesAndLocation(
    @Embedded var person: Person,
    @Relation(
        parentColumn = "personId",
        entityColumn = "personId"
    )
    var schedule: List<Schedule>,
)

interface ToValueItem {
    fun toValueDropdownItem(): ValueDropdownItem
}

data class CoursesDropdownItem(val name: String, val courseId: Long): ToValueItem {
    override fun toValueDropdownItem() = ValueDropdownItem(name, courseId)
}

data class PeopleDropdownItem(val lastName: String, val firstName: String?, val personId: Long): ToValueItem {
    override fun toValueDropdownItem() = ValueDropdownItem("$lastName ${firstName ?: ""}", personId)
}