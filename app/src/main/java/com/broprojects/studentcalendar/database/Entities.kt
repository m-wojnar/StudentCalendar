package com.broprojects.studentcalendar.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

data class YourDayEntity(
    val whenDateTime: Date?
)

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val courseId: Long?,
    val name: String,
    val iconId: Int?,
    val colorId: Int?
)

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val locationId: Long?,
    val name: String,
    val longitude: Double?,
    val latitude: Double?
)

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val personId: Long?,
    val firstName: String?,
    val secondName: String,
    val title: String?,
    val phone: String?,
    val email: String?,
    val locationId: Long?,
    val moreInfo: String?
)

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val scheduleId: Long?,
    val courseId: Long,
    val type: String?,
    val whenTime: Date,
    val startDate: Date,
    val endDate: Date,
    val personId: Long?,
    val locationId: Long?,
    val moreInfo: String?
)

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val testId: Long?,
    val courseId: Long,
    val type: String?,
    val subject: String?,
    val locationId: Long?,
    val whenDateTime: Date,
    val moreInfo: String?
)

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long?,
    val courseId: Long?,
    val title: String,
    val priority: Long?,
    val locationId: Long?,
    val whenDateTime: Date?,
    val reminder: Long?,
    val moreInfo: String?
)

data class CourseWithTests(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    val tests: List<Test>
)

data class CourseWithSchedules(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    val schedules: List<Schedule>
)

data class CourseWithTasks(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    val tasks: List<Task>
)

data class PersonWithSchedulesAndLocation(
    @Embedded val person: Person,

    @Relation(
        parentColumn = "personId",
        entityColumn = "personId"
    )
    val schedule: List<Schedule>,

    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val location: Location
)

data class TestAndLocation(
    @Embedded val test: Test,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val location: Location
)

data class ScheduleAndLocation(
    @Embedded val schedule: Schedule,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val location: Location
)

data class TaskAndLocation(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val location: Location
)