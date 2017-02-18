package leon.homework.JavaBean;

/**
 * Created by mjhzds on 2017/2/8.
 */

public class Work {

    private static final int ELECTRICAL_WORK = 0;
    private static final int PAPER_WORK = 1;
    private static final int VOICE_WORK = 2;

    private String title;

    public String getTitle() {
        return title;
    }

    private String content;
    private int type;

    public Work(String title, String content, int type) {
        this.title =title;
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
