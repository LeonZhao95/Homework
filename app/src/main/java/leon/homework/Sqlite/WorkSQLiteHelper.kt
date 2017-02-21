package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by BC on 2017/2/20 0020.
 */
class WorkSQLiteHelper(context: Context, name:String="Work.db", factory: SQLiteDatabase.CursorFactory?=null, version:Int=1): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("WorkSQLiteHelper","创建数据库 Work.db")
        db.execSQL("CREATE TABLE HistoryWork (" +
                "WorkId      INTEGER PRIMARY KEY AUTOINCREMENT," +
                "StuID        INT(16)," +
                "StuName      VARCHAR( 10 )," +
                "StuCls       VARCHAR( 20 )," +
                "Subject      VARCHAR( 10 )," +
                "WorkTime     VARCHAR( 10 )," +
                "WorkDeadline VARCHAR( 10 )," +
                "IsFinished   INT(1)," +
                "Questions    VARCHAR( 500 )," +
                "CorrectQues  VARCHAR( 500 ));")
        Log.d("WorkSQLiteHelper","创建表 HistoryWork")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}