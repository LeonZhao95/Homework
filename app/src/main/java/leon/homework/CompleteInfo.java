package leon.homework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CompleteInfo extends AppCompatActivity {
    private Spinner spinnerG;
    private Spinner spinnerC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);
        spinnerG= (Spinner) findViewById(R.id.gradSpin);
        spinnerC= (Spinner) findViewById(R.id.classSpin);

        ArrayAdapter<String> adapterG = new ArrayAdapter<String>(this, R.layout.simple_spin);
        String level[] = getResources().getStringArray(R.array.test);//资源文件
        for (int i = 0; i < level.length; i++) {
            adapterG.add(level[i]);
        }
        adapterG.setDropDownViewResource(R.layout.simple_spind);
        spinnerG.setAdapter(adapterG);
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, R.layout.simple_spin2);
        String level2[] = getResources().getStringArray(R.array.test2);//资源文件
        for (int i = 0; i < level2.length; i++) {
            adapterC.add(level2[i]);
        }
        adapterC.setDropDownViewResource(R.layout.simple_spin2d);
        spinnerC.setAdapter(adapterC);
    }
}
