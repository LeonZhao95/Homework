package leon.homework.JavaBean;

import android.graphics.Bitmap;

/**
 * Created by mjhzds on 2017/2/14.
 */

public class MsgObject {
    private String title;
    private Bitmap bm;
    private String lastcontent;
    private String time;

    public MsgObject(String title,Bitmap bm,String lastcontent,String time) {
        this.title = title;
        this.bm = bm;
        this.lastcontent = lastcontent;
        this.time = time;
    }
    public String getTitle(){return title;}
    public Bitmap getBm(){return bm;}
    public String getLastcontent(){return lastcontent;}
    public String getTime(){return time;}
}
