package leon.homework.Data

import leon.homework.AppContext

/**
 * Created by BC on 2017/2/5 0005.
 */

object Const {
    /**
     *>>>>>>>>>>>>>>>>>>>>>>>>>>>>PATH
     */
    val DATA_PATH = AppContext.instance!!.filesDir.absolutePath+"/"
    /**
     * >>>>>>>>>>>>>>>>>URL
     */
    val RegisterStuUrl = "http://zz503379238.oicp.net/usr/signup/info.php"
    val RegisterStuUpdataUrl = "http://zz503379238.oicp.net/usr/signup/info2.php"
    val LoginCheckUrl = "http://zz503379238.oicp.net/usr/signup/info3.php"
    val ForgetPwdStuUrl = "http://zz503379238.oicp.net/usr/fpswd/fpswd.php"
    val DownloadQuestionsUrl = "http://zz503379238.oicp.net/usr/download/downdata/1.zip"
    /**
     * >>>>>>>>>>>>>>>>>>>广播
     */
    val BROADCAST_SYSTEM = "BROADCAST_SYSTEM"
    val BROADCAST_HOMEWORK = "BROADCAST_HOMEWORK"
    val BROADCAST_CHAT = "BROADCAST_CHAT"
    /**
     * >>>>>>>>>>>>>>>>>config
     */
    val SHOW = 1
    val HIDE = 0
    val REGISTER_SUCCESS = 1
    val REGISTER_FAIL = 0
    val REGISTER_ERROR = -1
    val LOGIN_SUCCESS = 1
    val LOGIN_WRONGPWD = -1
    val LOGIN_NOEXIST = 0
    val LOGIN_NEED_COMPLETE = 2
    val LOGIN_ERROR = -2
    val COMPLETEINFO_SUCCESS = 1
    val COMPLETEINFO_FAIL = 0
    val COMPLETEINFO_ERROR = -1
    val Retrieve_SUCCESS = 1
    val Retrieve_FAIL = 0
    val Retrieve_ERROR = -1
    /**
     * >>>>>>>>>>>>>>>>>>homework
     */
    val SUBJECT_MATH      = "1"
    val SUBJECT_CHINESE   = "2"
    val SUBJECT_ENGLISH   = "3"
    val SUBJECT_PHYSICS   = "4"
    val SUBJECT_CHENISTRY = "5"
    val GRADE_ONE   = "01"
    val GRADE_TWO   = "02"
    val GRADE_THREE = "03"
    val GRADE_FOUR  = "04"
    val GRADE_FIVE  = "05"
    val GRADE_SIX   = "06"
    val GRADE_SEVEN = "07"
    val GRADE_EIGHT = "08"
    val GRADE_NINE  = "09"
    val CHAPTER_ONE   ="01"
    val CHAPTER_TWO   ="02"
    val SECTION_ONE   ="01"
    val SECTION_TWO   ="02"
    /**
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>Save
     */
    val SAVE_LOGIN_MODEL = "IsLogined"
    val SAVE_ISFIRSTIN = "IsFirst"
    val SAVE_ISSTUDENT = "IsStudent"
    val SAVE_PHONENUM = "PHONE_NUM"
    val SAVE_STU_ALIA = "Stu_Alia"
    val SAVE_STU_NAME = "Stu_Name"
    val SAVE_STU_ID = "Stu_Id"
    val SAVE_STU_IMG = "Stu_Img"
    val SAVE_STU_BIR = "Stu_Bir"
    val SAVE_STU_SEX = "Stu_Sex"
    val SAVE_STU_CLS = "Stu_Cls"
    /**
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>download
     */
    val DOWNLOAD_ERROR = -1
    val DOWNLOAD_UPDATE_PROGRESS = 0
    val DOWNLOAD_OVER = 1
    val DOWNLOAD_FILEEXIST = 2
    val DOWNLOAD_PATH = DATA_PATH+"/Download/"
    /**
     * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>others
     */
    fun SMSCode(status: String): String {
        when (status) {
                "456" -> return "请输入手机号"
                "457" -> return "手机号格式错误"
                "466" -> return "请输入验证码"
                "467" -> return "校验验证码请求频繁"
                "468" -> return "验证码错误"
                "477" -> return "当前手机号尝试次数过多"
                "500" -> return "服务器内部错误"
                "601" -> return "短信发送受限"
                "603" -> return "请填写正确的手机号码"
                "604" -> return "当前服务暂不支持此国家"
                else -> return "未知错误，错误代码:" + status
        }
    }
}
