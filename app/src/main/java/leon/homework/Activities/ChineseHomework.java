package leon.homework.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import leon.homework.Adapter.WorkAdapter;
import leon.homework.JavaBean.Work;
import leon.homework.R;

/**
 * Created by mjhzds on 2017/2/8.
 */

public class ChineseHomework extends AppCompatActivity {


    private List<Work> workList = new ArrayList<Work>();
    private List<Work> workListP = new ArrayList<Work>();
    private List<Work> workListE = new ArrayList<Work>();
    private List<Work> workListV = new ArrayList<Work>();
    private static final int ELECTRICAL_WORK = 0;
    private static final int PAPER_WORK = 1;
    private static final int VOICE_WORK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_choose);
        initWork();
        ListView listView;

            for (int i=0; i<workList.size(); i++){
                if (workList.get(i).getType() == ELECTRICAL_WORK){
                    workListE.add(workList.get(i));
                }
                else if(workList.get(i).getType() == PAPER_WORK){
                    workListP.add(workList.get(i));
                }
                else {
                    workListV.add(workList.get(i));
                }
            }
        WorkAdapter adapter = new WorkAdapter(ChineseHomework.this, R.layout.work_choose_list_item, workListE);
        ListView listViewE = (ListView) findViewById(R.id.elecWork);
        listViewE.setAdapter(adapter);

        ListView listViewP = (ListView) findViewById(R.id.paperWork);
        WorkAdapter adapter2 = new WorkAdapter(ChineseHomework.this, R.layout.work_choose_list_item, workListP);
        listViewP.setAdapter(adapter2);

        ListView listViewV = (ListView) findViewById(R.id.voiceWork);
        WorkAdapter adapter3 = new WorkAdapter(ChineseHomework.this, R.layout.work_choose_list_item, workListV);
        listViewV.setAdapter(adapter3);
//        for (int i=0; i<workList.size(); i++) {
//            if (workList.get(i).getType() == ELECTRICAL_WORK) {
//                WorkAdapter adapter = new WorkAdapter(ChineseHomework.this, R.layout.work_choose_list_item, workList);
//                listView = (ListView) findViewById(R.id.elecWork);
//                listView.setAdapter(adapter);
//
//            } else if (workList.get(i).getType() == PAPER_WORK) {

//            } else {

//            }
//        }
    }

    private void initWork() {
        Work work1 = new Work("不明白东风无力百花残什么完冷凑下字数呜哈哈哈哈", "bububu", 0);
        workList.add(work1);
        Work work2 = new Work("不知道", "bububu", 1);
        workList.add(work2);
        Work work3 = new Work("彻底做不到", "bububu", 2);
        workList.add(work3);
        Work work4 = new Work("试试", "bububu", 0);
        workList.add(work4);
        Work work5 = new Work("绘画", "bububu", 1);
        workList.add(work5);
        Work work6 = new Work("信息", "bububu", 2);
        workList.add(work6);
        Work work7 = new Work("啥", "bububu", 1);
        workList.add(work7);
    }


}
