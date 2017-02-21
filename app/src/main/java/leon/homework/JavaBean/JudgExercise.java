package leon.homework.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mjhzds on 2017/2/21.
 */

public class JudgExercise implements Parcelable{

    private String judgeId;
    private String title;
    private int option;
    private String imgName;
    public JudgExercise(String judgeId,String title,int option,String imgName) {
        this.judgeId = judgeId;
        this.title = title;
        this.option = option;
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
        dest.writeString(judgeId);
        dest.writeString(title);
        dest.writeInt(option);
        dest.writeString(imgName);
    }

    public static final Creator<JudgExercise> CREATOR = new Creator<JudgExercise>() {
        @Override
        public JudgExercise createFromParcel(Parcel source) {
            String judgeId = source.readString();
            String title = source.readString();
            int option = source.readInt();
            String img = source.readString();
            return new JudgExercise(judgeId,title,option,img);
        }

        @Override
        public JudgExercise[] newArray(int size) {
            return new JudgExercise[0];
        }
    };

    public String getImgName() {
        return imgName;
    }
}
