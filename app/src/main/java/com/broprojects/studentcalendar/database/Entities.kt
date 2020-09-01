package com.broprojects.studentcalendar.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var courseId: Long?,
    var name: String,
    var iconId: Int?,
    var colorId: Int?
)

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var personId: Long?,
    var firstName: String?,
    var lastName: String,
    var title: String?,
    var phone: String?,
    var email: String?,
    var location: String?,
    var moreInfo: String?
)

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long?,
    var courseId: Long,
    var type: String?,
    var whenTime: Date,
    var startDate: Date,
    var endDate: Date,
    var personId: Long?,
    var location: String?,
    var moreInfo: String?
)

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true)
    var testId: Long?,
    var courseId: Long,
    var type: String?,
    var subject: String?,
    var location: String?,
    var whenDateTime: Date,
    var moreInfo: String?
)

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long?,
    var courseId: Long?,
    var title: String,
    var priority: Long?,
    var location: String?,
    var whenDateTime: Date?,
    var reminder: Long?,
    var moreInfo: String?
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
