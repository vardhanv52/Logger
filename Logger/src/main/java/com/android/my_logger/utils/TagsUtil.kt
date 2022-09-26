package com.android.my_logger.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal object TagsUtil {
    private var tags = ArrayList<String>()

    init {
        val records = PrefUtil.getString(PrefUtil.keyTags)
        if (!records.isNullOrEmpty()) {
            tags.addAll(records.split(","))
        }
    }

    fun setTags(tags: List<String>) {
        this.tags.clear()
        this.tags.addAll(tags)
        saveTags()
    }

    fun addTags(tags: List<String>) {
        tags.forEach {
            if(!tags.contains(it))
                this.tags.add(it)
        }
        saveTags()
    }

    fun clearTags() {
        this.tags.clear()
        saveTags()
    }

    fun getTags(): List<String> {
        return this.tags
    }

    private fun saveTags() {
        PrefUtil.saveData(PrefUtil.keyTags, tags.joinToString(","))
    }
}