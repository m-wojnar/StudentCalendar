package com.mwojnar.studentcalendar.database

import android.content.Context
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.mwojnar.studentcalendar.helpers.ValueDropdownItem
import com.mwojnar.studentcalendar.helpers.toDateString
import com.mwojnar.studentcalendar.helpers.toDateTimeString
import com.mwojnar.studentcalendar.helpers.toTimeString
import java.util.*

interface EntityClass {
    fun getId(): Long?
}

interface ToValueItemConvertible {
    fun toValueDropdownItem(): ValueDropdownItem
}

enum class ItemType {
    SCHEDULE, TEST, TASK
}

data class YourDayItem(
    val itemType: ItemType,
    val itemId: Long?,
    val course: Course?,
    val title: String,
    val whenDateTime: Date?,
    val whenText: String?,
    val priority: Int?,
    val location: String?,
    val moreInfo: String?
)

interface ToYourDayItemConvertible {
    fun toYourDayItem(context: Context? = null): YourDayItem
}

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var courseId: Long? = null,
    var name: String = "",
    var iconName: String? = null,
    var colorName: String? = null
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

@Entity(
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["courseId"],
            childColumns = ["courseId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Person::class,
            parentColumns = ["personId"],
            childColumns = ["personId"],
            onDelete = CASCADE
        )
    ]
)
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

@Entity(
    tableName = "tests",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"],
        onDelete = CASCADE
    )]
)
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

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"],
        onDelete = CASCADE
    )]
)
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
) : EntityClass, ToYourDayItemConvertible {
    override fun getId() = test.testId

    override fun toYourDayItem(context: Context?) = YourDayItem(
        ItemType.TEST,
        test.testId,
        course,
        if (test.type != null) {
            "${course.name}: ${test.type}"
        } else {
            course.name
        },
        test.whenDateTime,
        test.whenDateTime?.toDateTimeString(context!!),
        null,
        test.location,
        test.subject
    )
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
) : EntityClass, ToYourDayItemConvertible {
    override fun getId() = schedule.scheduleId

    override fun toYourDayItem(context: Context?) = YourDayItem(
        ItemType.SCHEDULE,
        schedule.scheduleId,
        course,
        if (schedule.type != null) {
            "${course.name}: ${schedule.type}"
        } else {
            course.name
        },
        schedule.whenTime,
        "${Date().toDateString(context!!)} ${schedule.whenTime?.toTimeString(context)}",
        null,
        schedule.location,
        person?.toString()
    )
}

data class TaskAndCourse(
    @Embedded var task: Task,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var course: Course?
) : EntityClass, ToYourDayItemConvertible {
    override fun getId() = task.taskId

    override fun toYourDayItem(context: Context?) = YourDayItem(
        ItemType.TASK,
        task.taskId,
        course,
        task.title,
        task.whenDateTime,
        task.whenDateTime?.toDateTimeString(context!!),
        task.priority,
        task.location,
        course?.name
    )
}