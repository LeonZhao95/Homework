package leon.homework.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by BC on 2017/2/2 0002.
 */

public class ImgHelper {

    /**
     * TODO:将byte数组以Base64方式编码为字符串
     *
     * @param bytes 待编码的byte数组
     * @return 编码后的字符串
     */
    private static String encode(byte[] bytes) {
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        return new String(encode);
    }

    /**
     * TODO:将以Base64方式编码的字符串解码为byte数组
     *
     * @param encodeStr 待解码的字符串
     * @return 解码后的byte数组
     * @throws IOException
     */
    public static byte[] decode(String encodeStr) throws IOException {
        byte[] bt = null;
        bt = Base64.decode(encodeStr, Base64.DEFAULT);
        return bt;
    }

    /**
     * TODO:将两个byte数组连接起来后，返回连接后的Byte数组
     *
     * @param front 拼接后在前面的数组
     * @param after 拼接后在后面的数组
     * @return 拼接后的数组
     */
    public static byte[] connectBytes(byte[] front, byte[] after) {
        byte[] result = new byte[front.length + after.length];
        System.arraycopy(front, 0, result, 0, after.length);
        System.arraycopy(after, 0, result, front.length, after.length);
        return result;
    }

    /**
     * TODO:将图片以Base64方式编码为字符串
     *
     * @param imgUrl 图片的绝对路径（例如：D:\\jsontest\\abc.jpg）
     * @return 编码后的字符串
     * @throws IOException
     */
    public static String encodeImage(String imgUrl) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeFile(imgUrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return encode(bytes);
    }
}
