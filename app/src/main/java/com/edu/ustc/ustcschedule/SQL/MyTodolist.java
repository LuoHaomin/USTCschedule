package com.edu.ustc.ustcschedule.SQL;

import java.sql.Time;
import java.util.Date;

public class MyTodolist extends BasicSchedule{



    //默认不添加备注,重要性为1,创建时必须填写编号,时间,地点和内容
    public MyTodolist (String name,long starting_time,int importance,boolean is_repeat,int period,String place,String description){
        super(name,starting_time,importance,is_repeat,period,place,description);

    }


}
