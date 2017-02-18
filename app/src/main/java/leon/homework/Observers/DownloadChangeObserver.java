package leon.homework.Observers;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by BC on 2017/2/17 0017.
 */

public class DownloadChangeObserver extends ContentObserver {

    public DownloadChangeObserver(Handler handler) {
        super(handler);
    }
}
