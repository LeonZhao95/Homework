package leon.homework.Activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_complete_info.*
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.Data.SaveData
import leon.homework.Extensions.loading
import leon.homework.Person.Student
import leon.homework.R
import leon.homework.Utils.Utils
import org.jetbrains.anko.async
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.File




class CompleteInfoActivity : BaseActivity(), View.OnClickListener {
    var photo : Bitmap? = null
    var stu_user: Student? = null
    override val layoutResourceId: Int = R.layout.activity_complete_info
    var imagePath :String? = null
    var year=2000
    var month=1
    var day=1
    val CHOOSE_PICTURE = 0
    val TAKE_PICTURE = 1
    val CROP_SMALL_PICTURE = 2
    var tempUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imgbtn_pic.setOnClickListener(this)
        imgbtn_back.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        txt_birth.setOnClickListener(this)
        if(AppContext.stuUser!=null){
            stu_user = AppContext.stuUser!!
        }else{
            AppContext.stuUser = Student()
            stu_user = AppContext.stuUser!!
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgbtn_back -> finish()
            R.id.imgbtn_pic -> showChoosePicDialog()
            R.id.btn_submit -> {
                if(imagePath.isNullOrEmpty()||
                        name_text.text.toString().isNullOrEmpty()||
                        id_text.text.toString().isNullOrEmpty()||
                        txt_birth.text.toString().isNullOrEmpty()||
                        txt_sex.text.toString().isNullOrEmpty()||
                        txt_cls.text.toString().isNullOrEmpty()
                        ){
                    info(imagePath)
                    toast("请完善所有信息")
                }else{
                    loading(Const.SHOW)
                    CompleteInfo()
                }
            }
            R.id.txt_birth->{
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    this.year = year
                    this.month = month
                    this.day = day
                    txt_birth.setText(StringBuilder().append(year).append("-")
                            .append(if (month + 1 < 10) "0" + (month + 1) else month + 1)
                            .append("-")
                            .append(if (day < 10) "0" + day else day))
                }, year, month, day).show()
            }
        }
    }

    private fun CompleteInfo() {
        async() {
            val result = stu_user!!.CompleteInfo(name_text.text.toString(),id_text.text.toString(),imagePath!!,txt_birth.text.toString(),txt_sex.text.toString(),txt_cls.text.toString())
            uiThread {
                loading(Const.HIDE)
                when(result){
                    Const.COMPLETEINFO_SUCCESS->{
                        SaveData.isLogined = true
                        startActivity(Intent(this@CompleteInfoActivity, MainActivity::class.java))
                        finish()
                    }
                    Const.COMPLETEINFO_FAIL->{
                        toast("信息完善失败")
                    }
                    Const.COMPLETEINFO_ERROR->{
                        toast(result.toString())
                    }
                }
            }
        }
    }


    /**
     * 显示修改头像的对话框
     */
    fun showChoosePicDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("设置头像")
        val items = arrayOf("选择本地照片", "拍照")
        builder.setNegativeButton("取消", null)
        builder.setItems(items) { dialog, which ->
            when (which) {
                CHOOSE_PICTURE // 选择本地照片
                -> {
                    val openAlbumIntent = Intent(
                            Intent.ACTION_GET_CONTENT)
                    openAlbumIntent.type = "image/*"
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE)
                }
                TAKE_PICTURE // 拍照
                -> {
                    val openCameraIntent = Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE)
                    tempUri = Uri.fromFile(File(Environment
                            .getExternalStorageDirectory(), "image.jpg"))
                    // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri)
                    startActivityForResult(openCameraIntent, TAKE_PICTURE)
                }
            }
        }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) { // 如果返回码是可以用的
            when (requestCode) {
                TAKE_PICTURE -> {
                    startPhotoZoom(tempUri) // 开始对图片进行裁剪处理
                    Log.d(null, "onActivityResult: >>>>>>>>>>>>>开始对图片进行裁剪处理1")
                }
                CHOOSE_PICTURE -> {
                    Log.d(null, "onActivityResult: >>>>>>>>>>>>>开始对图片进行裁剪处理2")
                    startPhotoZoom(data!!.data) // 开始对图片进行裁剪处理
                }
                CROP_SMALL_PICTURE -> if (data != null) {
                    Log.d(null, "onActivityResult: >>>>>>>>>>>>>让刚才选择裁剪得到的图片显示在界面上")
                    setImageToView(data) // 让刚才选择裁剪得到的图片显示在界面上
                }
            }
        }
    }

    /**
     * 裁剪图片方法实现

     * @param uri
     */
    protected fun startPhotoZoom(uri: Uri?) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.")
        }
        tempUri = uri
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 设置裁剪
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", imgview.width)
        intent.putExtra("outputY", imgview.height)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, CROP_SMALL_PICTURE)
    }

    /**
     * 保存裁剪之后的图片数据

     * @param
     */
    protected fun setImageToView(data: Intent) {
        val extras = data.extras
        if (extras != null) {
            photo = extras.getParcelable<Bitmap>("data")
            photo = Utils.toRoundBitmap(photo!!, tempUri!!) // 这个时候的图片已经被处理成圆形的了
            imgview.setImageBitmap(photo)
            imgview.background.setVisible(false,true)
            imagePath = Utils.savePhoto(photo,AppContext.instance!!.filesDir.absolutePath, stu_user!!.alia)
        }/*Environment.getExternalStorageDirectory().absolutePath*/
    }
}
