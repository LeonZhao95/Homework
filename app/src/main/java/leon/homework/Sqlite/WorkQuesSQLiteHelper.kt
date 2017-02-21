package leon.homework.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by BC on 2017/2/19 0019.
 */
class WorkQuesSQLiteHelper(context: Context, name:String="WorkQues.db", factory: SQLiteDatabase.CursorFactory?=null, version:Int=1): SQLiteOpenHelper(context, name, factory, version) {
    /*val ansques ="CREATE TABLE ansques (" +
            "ans_id varchar(9) ," +
            "ans_sbj varchar(200) ," +
            "ans_ans varchar(200) ," +
            "ans_img varchar(9))"
    val choicques = "CREATE TABLE choicques (" +
            "choic_id varchar(9)," +
            "choic_sbj varchar(200)," +
            "choic_A varchar(80)," +
            "choic_B varchar(80)," +
            "choic_C varchar(80)," +
            "choic_D varchar(80)," +
            "choic_ans varchar(5)," +
            "choic_img varchar(9))"
    val fillques = "CREATE TABLE fillques (" +
            "fill_id varchar(9)," +
            "fill_sbj varchar(100)," +
            "fill_ans varchar(20)," +
            "fill_img varchar(9))"
    val tfques ="CREATE TABLE tfques (" +
            "tf_id varchar(9)," +
            "tf_sbj varchar(100)," +
            "tf_ans varchar(5)," +
            "tf_img varchar(9))"*/
    override fun onCreate(db: SQLiteDatabase) {
        Log.d("StuWorkSQlite","建立数据库")
        /*db.execSQL(ansques)
        db.execSQL(choicques)
        db.execSQL(fillques)
        db.execSQL(tfques)*/
    }
    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }

}