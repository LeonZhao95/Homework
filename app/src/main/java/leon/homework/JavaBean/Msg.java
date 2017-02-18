package leon.homework.JavaBean;

/**
 * Created by mjhzds on 2017/2/14.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;

    private String content;
    private int msgType;

    public Msg(String content, int msgType) {
        this.content = content;
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public int getMsgType() {
        return msgType;
    }
}
