package com.broprojects.studentcalendar.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.broprojects.studentcalendar.R

data class IconDropdownItem(val name: String, val id: Int) {
    override fun toString() = name
}

class IconAdapter(context: Context, private val objects: Array<out IconDropdownItem>) :
    ArrayAdapter<IconDropdownItem>(context, R.layout.icon_dropdown_menu_item, objects) {

    override fun getCount() = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:
        LayoutInflater.from(context).inflate(R.layout.icon_dropdown_menu_item, parent, false)

        view.findViewById<ImageView>(R.id.dropdown_item_color).setBackgroundResource(objects[position].id)
        view.findViewById<TextView>(R.id.dropdown_item_text).text = objects[position].name

        return view
    }
}

data class ValueDropdownItem(val text: String, val value: Long) {
    override fun toString() = text
}

class ValueAdapter(context: Context, private val objects: Array<out ValueDropdownItem>) :
    ArrayAdapter<ValueDropdownItem>(context, R.layout.icon_dropdown_menu_item, objects) {

    override fun getCount() = objects.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:
        LayoutInflater.from(context).inflate(R.layout.icon_dropdown_menu_item, parent, false)

        view.findViewById<TextView>(R.id.dropdown_item_text).text = objects[position].text
        return view
    }
}