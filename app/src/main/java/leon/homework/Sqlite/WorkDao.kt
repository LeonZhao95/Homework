package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import leon.homework.AppContext
import leon.homework.JavaBean.TodayWork
import org.json.JSONObject
import java.util.*


/**
 * Created by BC on 2017/2/20 0020.
 */
class WorkDao(context: Context) {
    private val TAG = "WorkDao"
    private var context: Context? = null
    private val TABLENAME = "HistoryWork"
    private var workhelper: WorkSQLiteHelper? = null
    init {
        this.context = context
        workhelper = WorkSQLiteHelper(context)
    }

    fun execSQL(sql: String) {
        val db: SQLiteDatabase = workhelper!!.writableDatabase      //获得数据库
        try {
            db.beginTransaction()
                db.execSQL(sql)
            db.setTransactionSuccessful()
            Log.d(TAG,"成功执行")
        } catch (e: Exception) {
            Log.d(TAG,"SQL错误")
            Log.e(TAG, "", e.cause)
        } finally {
            db.endTransaction()
            db.close()
        }
    }
    fun insert1(subject: TodayWork){
        val db = workhelper!!.writableDatabase
            val StuID = AppContext.stuUser!!.alia
            val StuName = AppContext.stuUser!!.Stu_Name
            val StuCls = AppContext.stuUser!!.cls
            val Subject = subject.subjectName
            val WorkTime = subject.worktime
            val WorkDeadline = subject.deadLine
            val QuesJS = subject.js.toString()
        try {
            val sql = "Insert into $TABLENAME (StuID,StuName,StuCls,Subject,WorkTime,WorkDeadline,IsFinished,Questions) " +
                    "values ($StuID,'$StuName',$StuCls,'$Subject',$WorkTime,'$WorkDeadline',0,'$QuesJS');"
            db.execSQL(sql)
        }catch (e: Exception){
            Log.e(TAG, "", e.cause)
        }finally {
            db.close()
        }

    }
    fun insert2(js: JSONObject){
        val db = workhelper!!.writableDatabase

    }
    fun select1():ArrayList<TodayWork>{
        val db = workhelper!!.readableDatabase
        val worklist = ArrayList<TodayWork>()
        val cursor = db.query(TABLENAME, arrayOf("Subject", "WorkTime", "WorkDeadline","Questions"), "IsFinished=?", arrayOf("0"), null, null, null)
        while (cursor.moveToNext()) {
            val Subject = cursor.getString(cursor.getColumnIndex("Subject"))
            val WorkTime = cursor.getString(cursor.getColumnIndex("WorkTime"))
            val WorkDeadline = cursor.getString(cursor.getColumnIndex("WorkDeadline"))
            val js = cursor.getString(cursor.getColumnIndex("Questions"))
            println("query------->学科：$Subject 时间：$WorkTime 截止日期：$WorkDeadline")
            worklist.add(TodayWork(Subject,WorkDeadline,WorkTime,JSONObject(js)))
        }
        return worklist
    }
}