package leon.homework.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by BC on 2017/2/19 0019.
 */

public class ZipUtil {
    public static void unzipFiles(File file, String destDir) throws FileNotFoundException,IOException {
        //压缩文件
        File srcZipFile=file;
        //基本目录
        if(!destDir.endsWith("/")){
            destDir+="/";
        }
        String prefixion=destDir;

        //压缩输入流
        ZipInputStream zipInput=new ZipInputStream(new FileInputStream(srcZipFile));
        //压缩文件入口
        ZipEntry currentZipEntry=null;
        //循环获取压缩文件及目录
        while((currentZipEntry=zipInput.getNextEntry())!=null){
            //获取文件名或文件夹名
            String fileName=currentZipEntry.getName();
            //Log.v("filename",fileName);
            //构成File对象
            File tempFile=new File(prefixion+fileName);
            //父目录是否存在
            if(!tempFile.getParentFile().exists()){
                //不存在就建立此目录
                tempFile.getParentFile().mkdir();
            }
            //如果是目录，文件名的末尾应该有“/"
            if(currentZipEntry.isDirectory()){
                //如果此目录不在，就建立目录。
                if(!tempFile.exists()){
                    tempFile.mkdir();
                }
                //是目录，就不需要进行后续操作，返回到下一次循环即可。
                continue;
            }
            //如果是文件
            if(!tempFile.exists()){
                //不存在就重新建立此文件。当文件不存在的时候，不建立文件就无法解压缩。
                tempFile.createNewFile();
            }
            //输出解压的文件
            FileOutputStream tempOutputStream=new FileOutputStream(tempFile);

            //获取压缩文件的数据
            byte[] buffer=new byte[1024];
            int hasRead=0;
            //循环读取文件数据
            while((hasRead=zipInput.read(buffer))>0){
                tempOutputStream.write(buffer,0,hasRead);
            }
            tempOutputStream.flush();
            tempOutputStream.close();
        }
        zipInput.close();
    }
}
