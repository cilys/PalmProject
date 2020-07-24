package com.aopcloud.palmproject.ui.activity.project.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.aopcloud.palmproject.conf.TaskStatus;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : ProjectTaskDetailBean.java
 * @Date : 2020/5/17 2020/5/17
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectTaskDetailBean  implements Serializable, Parcelable {


    /**
     * project_id : 15
     * make_time : 1589515761
     * pid : 0
     * name : 恶魔之眼
     * work_value : 10
     * work_unit : 台
     * work_des : 闷油
     * attach : /upload/20200515/kWD2sxHqzJFN8K7V299X3Tyxnv40bCO0A0eZDNZL.jpeg
     * progress : 0
     * team_id : 6
     * assign_time : 1589784399
     * address : 广东省广州市天河区棠下西沙二巷天盈创意园
     * longitue : 113.37667271252191
     * latitude : 23.13505055749225
     * scope : 300
     * level : 普通
     * leader_id : 7
     * leader_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * leader_name : 米大爷
     * team_name : 恶魔之眼
     * project_name : 广州水电工程3期
     * task_id : 15
     * status_str : 进行中
     * team_num : 2
     * start_date : 2020-05-18
     * end_date : 2020-10-01
     * start_date_real : 1970-01-01
     * end_date_real : 1970-01-01
     * trends : [{"extra":"","make_time":1589515761,"type":2,"user_id":7,"content":"米大爷 创建工单","message":"","attach":"","user_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg"},{"extra":"{\"team_id\":\"6\"}","make_time":1589783608,"type":4,"user_id":7,"content":"米大爷 指派给团队 测试编辑班组","message":"","attach":"","user_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg"},{"extra":"{\"team_id\":\"6\"}","make_time":1589784399,"type":4,"user_id":7,"content":"米大爷 指派给团队 测试编辑班组","message":"","attach":"","user_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg"},{"extra":"","make_time":1589982130,"type":5,"user_id":7,"content":"米大爷 创建子工单","message":"","attach":"","user_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg"}]
     */

    private int project_id;
    private long make_time;
    private int pid;
    private String name;
    private int work_value;
    private String work_unit;
    private String work_des;
    private String attach;
    private int progress;
    private int team_id;
    private long assign_time;
    private String address;
    private String longitue;
    private String latitude;
    private int scope;
    private String level;
    private int leader_id;
    private String leader_avatar;
    private String leader_name;
    private String team_name;
    private String project_name;
    private int task_id;
    private String status_str;
    private int team_num;
    private String start_date;
    private String end_date;
    private String start_date_real;
    private String end_date_real;
    private List<TrendsBean> trends;
    private int work_value_finished;//已完成的工作数量

    public int getWork_value_finished() {
        return work_value_finished;
    }

    public void setWork_value_finished(int work_value_finished) {
        this.work_value_finished = work_value_finished;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWork_value() {
        return work_value;
    }

    public void setWork_value(int work_value) {
        this.work_value = work_value;
    }

    public String getWork_unit() {
        return work_unit;
    }

    public void setWork_unit(String work_unit) {
        this.work_unit = work_unit;
    }

    public String getWork_des() {
        return work_des;
    }

    public void setWork_des(String work_des) {
        this.work_des = work_des;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public long getAssign_time() {
        return assign_time;
    }

    public void setAssign_time(long assign_time) {
        this.assign_time = assign_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitue() {
        return TextUtils.isEmpty(longitue) ? "0" : longitue;
    }

    public void setLongitue(String longitue) {
        this.longitue = longitue;
    }

    public String getLatitude() {
        return TextUtils.isEmpty(latitude) ? "0" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getLeader_avatar() {
        return leader_avatar;
    }

    public void setLeader_avatar(String leader_avatar) {
        this.leader_avatar = leader_avatar;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getStatus_str() {
        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    public int getTeam_num() {
        return team_num;
    }

    public void setTeam_num(int team_num) {
        this.team_num = team_num;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date_real() {
        return start_date_real;
    }

    public void setStart_date_real(String start_date_real) {
        this.start_date_real = start_date_real;
    }

    public String getEnd_date_real() {
        return end_date_real;
    }

    public void setEnd_date_real(String end_date_real) {
        this.end_date_real = end_date_real;
    }

    public List<TrendsBean> getTrends() {
        return trends;
    }

    public void setTrends(List<TrendsBean> trends) {
        this.trends = trends;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class TrendsBean {
        /**
         * extra :
         * make_time : 1589515761
         * type : 2
         * user_id : 7
         * content : 米大爷 创建工单
         * message :
         * attach :
         * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
         */

        private String extra;
        private long make_time;
        private int type;
        private int user_id;
        private String content;
        private String message;
        private String attach;
        private String user_avatar;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public long getMake_time() {
            return make_time;
        }

        public void setMake_time(long make_time) {
            this.make_time = make_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }
    }

    /**
     * 经过计算后的任务状态
     */
    private String calculateStatus;
    /**
     * 经过计算后的超期天数，>= 0，未逾期，< 0逾期的天数
     */
    private long outOfDay;
    public void setCalculateStatus(String calculateStatus) {
        this.calculateStatus = calculateStatus;
    }

    public String getCalculateStatus() {
        if (StrUtils.isEmpty(calculateStatus)){
            calculateStatus = calculateStatus();
        }
        return calculateStatus;
    }

    public void setOutOfDay(long outOfDay) {
        this.outOfDay = outOfDay;
    }

    public long getOutOfDay() {
        return outOfDay;
    }

    private String calculateStatus(){
        if (StrUtils.isEmpty(getStatus_str())){
            return getStatus_str();
        }
        if (TaskStatus.STATE_complete.equals(getStatus_str())){
            return getStatus_str();
        }
        if (TaskStatus.STATE_pause.equals(getStatus_str()) || getStatus_str().contains("暂停")){
            return getStatus_str();
        }
        if (TaskStatus.STATE_progress.equals(getStatus_str())
                || TaskStatus.STATE_operation.equals(getStatus_str())) {
            if (getProgress() >= 100){
                return TaskStatus.STATE_complete;
            }
        }

        long cd = System.currentTimeMillis();
        long ed = TimeUtils.strToMil(getEnd_date(), TimeType.DAY_LINE, cd);
        if (ed < cd){
            outOfDay = (cd - ed) / (1000 * 60* 60 * 24);
            return TaskStatus.STATE_expect;
        }
        if (TaskStatus.STATE_progress.equals(getStatus_str())
                || TaskStatus.STATE_operation.equals(getStatus_str())) {
            if (getProgress() >= 100){
                return TaskStatus.STATE_complete;
            }
            return TaskStatus.STATE_progress;
        }
        if (TaskStatus.STATE_no_start.equals(getStatus_str())){
            return getStatus_str();
        }

        return getStatus_str();
    }
}
