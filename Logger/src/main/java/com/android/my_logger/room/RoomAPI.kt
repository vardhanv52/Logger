package com.android.my_logger.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface RoomAPI {
    @Insert
    fun insertAPI(api: APICalls): Long

    @Query("select * from api_calls")
    fun getAPICalls(): List<APICalls>

    @Query("delete from api_calls where id > 0")
    fun deleteAPICalls()

    @Insert
    fun insertAction(action: UserActions): Long

    @Query("select * from user_actions")
    fun getActions(): List<UserActions>

    @Query("delete from user_actions where id > 0")
    fun deleteActions()
}