package leon.homework.JavaBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mjhzds on 2017/2/21.
 */

public class ChoiceExercise implements Parcelable {

    private String choId;
    private String title;
    private String[] choices;
    private String imgname;
    public String getTitle() {
        return title;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getImgname() {
        return imgname;
    }
    public ChoiceExercise(String choId,String title,String[] choices,String imgname) {
        this.choId = choId;
        this.title = title;
        this.choices = choices;
        this.imgname =imgname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(choId);
        dest.writeString(title);
        dest.writeString(imgname);
        if (choices == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(choices.length);
        }
        if (choices != null) {
            dest.writeStringArray(choices);
        }
    }

    public static final Creator<ChoiceExercise> CREATOR = new Creator<ChoiceExercise>() {
        @Override
        public ChoiceExercise createFromParcel(Parcel source) {
            String choId = source.readString();
            String title = source.readString();
            String imgname = source.readString();
            int length = source.readInt();
            String[] choices = null;
            if (length > 0) {
                choices = new String[length];
                source.readStringArray(choices);
            }
            return new ChoiceExercise(choId,title,choices,imgname);
        }

        @Override
        public ChoiceExercise[] newArray(int size) {
            return new ChoiceExercise[0];
        }
    };
}
