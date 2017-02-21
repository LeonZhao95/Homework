package leon.homework.JavaBean

import leon.homework.R
import org.json.JSONObject

/**
 * Created by mjhzds on 2017/2/6.
 */

class TodayWork(val subjectName: String, val deadLine: String, val worktime: String,val js:JSONObject) {
    val subjectImage: Int
    init {
        this.subjectImage = getSubjectImg(subjectName)
    }
    fun getSubjectImg(subject:String):Int{
        return when(subject){
            "语文"->{
                R.drawable.chinese
            }
            "英语"->{
                R.drawable.english
            }
            else -> {
                R.drawable.teacher //TODO set default
            }
        }
    }
}
