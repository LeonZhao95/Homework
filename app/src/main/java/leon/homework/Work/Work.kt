package leon.homework.Work

import org.json.JSONObject
import java.util.*

/**
 * Created by BC on 2017/2/15 0015.
 */
class Work(json_s :String){
    var subject :String? = null
    var deadline :String? =null
    var worksum :String? = null
    var ChoiceQuestions :ArrayList<ChoiceQuestion> = ArrayList<ChoiceQuestion>()
    var JudgmentQuestions :ArrayList<JudgmentQuestion> =ArrayList<JudgmentQuestion>()
    var ShortAnswerQuestions :ArrayList<ShortAnswerQuestion> = ArrayList<ShortAnswerQuestion>()
    init {
        val js:JSONObject = JSONObject(json_s)
        this.subject = js.getString("subject")
        this.deadline = js.getString("deadline")
        this.worksum = js.getString("worknum")
        val js_choice :JSONObject = js.getJSONObject("js_choice")
        val js_judgment :JSONObject = js.getJSONObject("js_judgment")
        val js_short_answer :JSONObject = js.getJSONObject("js_short_answer")
    }
    fun SendBroadcast(){

    }
}