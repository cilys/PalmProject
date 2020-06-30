package com.aopcloud.palmproject.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : MsgTodoBean.java
 * @Date : 2020/6/8 2020/6/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class MsgTodoBean {

    private List<PersonnelApprovalBean> personnel_approval;
    private List<TasksDoingBean> tasks_doing;
    private List<TaskForFeedbackBean> task_for_feedback;

    public List<PersonnelApprovalBean> getPersonnel_approval() {
        return personnel_approval;
    }

    public void setPersonnel_approval(List<PersonnelApprovalBean> personnel_approval) {
        this.personnel_approval = personnel_approval;
    }

    public List<TasksDoingBean> getTasks_doing() {
        return tasks_doing;
    }

    public void setTasks_doing(List<TasksDoingBean> tasks_doing) {
        this.tasks_doing = tasks_doing;
    }

    public List<TaskForFeedbackBean> getTask_for_feedback() {
        return task_for_feedback;
    }

    public void setTask_for_feedback(List<TaskForFeedbackBean> task_for_feedback) {
        this.task_for_feedback = task_for_feedback;
    }

    public static class PersonnelApprovalBean {
        /**
         * company_user_id : 26
         * make_time : 1591305497
         * reason : 我很想加入
         * come_from : 主动申请
         * tel : 18676632715
         * user_name : 毛毛虫
         * user_id : 5
         * user_avatar : /upload/20200430/BvhK8FO4dqqkrRbs2HfNpH2hOxMa051gkbYZayHO.jpeg
         * user_status : 2
         */

        private int company_user_id;
        private long make_time;
        private String reason;
        private String come_from;
        private long tel;
        private String user_name;
        private int user_id;
        private String user_avatar;
        private int user_status;

        public int getCompany_user_id() {
            return company_user_id;
        }

        public void setCompany_user_id(int company_user_id) {
            this.company_user_id = company_user_id;
        }

        public long getMake_time() {
            return make_time;
        }

        public void setMake_time(long make_time) {
            this.make_time = make_time;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCome_from() {
            return come_from;
        }

        public void setCome_from(String come_from) {
            this.come_from = come_from;
        }

        public long getTel() {
            return tel;
        }

        public void setTel(long tel) {
            this.tel = tel;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }
    }

    public static class TasksDoingBean {
        /**
         * project_id : 1
         * make_time : 1591289688
         * pid : 13
         * name : 工单任务1.89
         * work_value : 10
         * work_unit : 台
         * work_des : 工单描述
         * attach : /1.jpg,/2.jpg
         * progress : 0
         * team_id : 31
         * assign_time : 1591289688
         * address : 梅州
         * longitue : 123.2223
         * latitude : 24.4321
         * scope : 100
         * level : 重要
         * sort_id : 100
         * leader_id : 1
         * leader_avatar : /upload/2123/1.jpg
         * leader_name : 曾大大
         * team_name : 水电一班1的子团队
         * project_name : 广州水电工程1期
         * task_id : 49
         * work_value_finished : 0
         * status_str : 进行中
         * team_num : 2
         * start_date : 2020-02-02
         * end_date : 2022-12-12
         * start_date_real : 1970-01-01
         * end_date_real : 1970-01-01
         * attendance_status : 未签到
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
        private int assign_time;
        private String address;
        private String longitue;
        private String latitude;
        private int scope;
        private String level;
        private int sort_id;
        private int leader_id;
        private String leader_avatar;
        private String leader_name;
        private String team_name;
        private String project_name;
        private int task_id;
        private int work_value_finished;
        private String status_str;
        private int team_num;
        private String start_date;
        private String end_date;
        private String start_date_real;
        private String end_date_real;
        private String attendance_status;

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

        public int getAssign_time() {
            return assign_time;
        }

        public void setAssign_time(int assign_time) {
            this.assign_time = assign_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitue() {
            return longitue;
        }

        public void setLongitue(String longitue) {
            this.longitue = longitue;
        }

        public String getLatitude() {
            return latitude;
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

        public int getSort_id() {
            return sort_id;
        }

        public void setSort_id(int sort_id) {
            this.sort_id = sort_id;
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

        public int getWork_value_finished() {
            return work_value_finished;
        }

        public void setWork_value_finished(int work_value_finished) {
            this.work_value_finished = work_value_finished;
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

        public String getAttendance_status() {
            return attendance_status;
        }

        public void setAttendance_status(String attendance_status) {
            this.attendance_status = attendance_status;
        }
    }

    public static class TaskForFeedbackBean {
        /**
         * project_id : 1
         * make_time : 1587203585
         * pid : 0
         * name : 工单任务1.0
         * work_value : 10
         * work_unit : 台
         * work_des : 工单描述
         * attach : /1.jpg,/2.jpg
         * progress : 10
         * team_id : 1
         * assign_time : 0
         * address : 梅州
         * longitue : 123.2223
         * latitude : 24.4321
         * scope : 100
         * level : 重要
         * sort_id : 110
         * leader_id : 1
         * leader_avatar : /upload/2123/1.jpg
         * leader_name : 曾大大
         * team_name : null
         * project_name : 广州水电工程1期
         * task_id : 7
         * work_value_finished : 1
         * status_str : 进行中
         * team_num : 0
         * start_date : 2020-02-02
         * end_date : 2022-12-12
         * start_date_real : 2023-02-02
         * end_date_real : 2020-02-02
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
        private int assign_time;
        private String address;
        private String longitue;
        private String latitude;
        private int scope;
        private String level;
        private int sort_id;
        private int leader_id;
        private String leader_avatar;
        private String leader_name;
        private Object team_name;
        private String project_name;
        private int task_id;
        private int work_value_finished;
        private String status_str;
        private int team_num;
        private String start_date;
        private String end_date;
        private String start_date_real;
        private String end_date_real;

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

        public int getAssign_time() {
            return assign_time;
        }

        public void setAssign_time(int assign_time) {
            this.assign_time = assign_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitue() {
            return longitue;
        }

        public void setLongitue(String longitue) {
            this.longitue = longitue;
        }

        public String getLatitude() {
            return latitude;
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

        public int getSort_id() {
            return sort_id;
        }

        public void setSort_id(int sort_id) {
            this.sort_id = sort_id;
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

        public Object getTeam_name() {
            return team_name;
        }

        public void setTeam_name(Object team_name) {
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

        public int getWork_value_finished() {
            return work_value_finished;
        }

        public void setWork_value_finished(int work_value_finished) {
            this.work_value_finished = work_value_finished;
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
    }
}
