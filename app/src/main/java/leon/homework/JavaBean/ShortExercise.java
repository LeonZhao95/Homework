package leon.homework.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mjhzds on 2017/2/21.
 */

public class ShortExercise implements Parcelable{

    private String shortId;
    private String title;
    private String answer;
    private String imgName;

    public ShortExercise(String shortId,String title,String answer,String imgName) {
        this.shortId = shortId;
        this.title =title;
        this.answer = answer;
        this.imgName = imgName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortId);
        dest.writeString(title);
        dest.writeString(answer);
        dest.writeString(imgName);
    }

    public static final Creator<ShortExercise> CREATOR = new Creator<ShortExercise>() {
        @Override
        public ShortExercise createFromParcel(Parcel source) {
            String shortId = source.readString();
            String title = source.readString();
            String answer = source.readString();
            String img = source.readString();
            return new ShortExercise(shortId,title,answer,img);
        }

        @Override
        public ShortExercise[] newArray(int size) {
            return new ShortExercise[0];
        }
    };

    public String getImgName() {
        return imgName;
    }
}
