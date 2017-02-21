package leon.homework.Data

import leon.homework.AppContext
import leon.homework.Utils.Preference

/**
 * Created by BC on 2017/2/10 0010.
 */
object SaveData{
    var isLogined:Boolean by Preference(AppContext.instance!!, Const.SAVE_LOGIN_MODEL,false)
    var isFirstIn:Boolean by Preference(AppContext.instance!!, Const.SAVE_ISFIRSTIN,true)
    var phonenum:String by Preference(AppContext.instance!!, Const.SAVE_PHONENUM,"")
    var alia:String by Preference(AppContext.instance!!, Const.SAVE_STU_ALIA,"")
    var Stu_Name: String by Preference(AppContext.instance!!, Const.SAVE_STU_NAME,"")
    var Stu_Id: String by Preference(AppContext.instance!!, Const.SAVE_STU_ID,"")
    var avatar_path: String by Preference(AppContext.instance!!, Const.SAVE_STU_IMG,"")
    var birthday: String by Preference(AppContext.instance!!, Const.SAVE_STU_BIR,"")
    var sex: String by Preference(AppContext.instance!!, Const.SAVE_STU_SEX,"")
    var cls: String by Preference(AppContext.instance!!, Const.SAVE_STU_CLS,"")
    var isStudent:String by Preference(AppContext.instance!!, Const.SAVE_ISSTUDENT,"-1") //1 student 2 teacher -1 default
}