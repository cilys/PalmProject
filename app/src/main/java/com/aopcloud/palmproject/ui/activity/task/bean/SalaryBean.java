package com.aopcloud.palmproject.ui.activity.task.bean;

import android.widget.ExpandableListView;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task.bean
 * @File : SalaryBean.java
 * @Date : 2020/5/27 16:42
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class SalaryBean {

    /**
     * project_id : 15
     * make_time : 1589705708
     * pid : 0
     * name : 测试创建工单
     * work_value : 10
     * work_unit : 台
     * work_des : 我哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦
     * attach : /upload/20200517/tJjCGUfmnGbSHthUmlgFc8WTkh1cZO1BFHabcCbS.mp4,/upload/20200517/YhVMD6kmNqQlbSoPAZrPuFsS74lUvaTkhqjt11Ey.jpeg
     * progress : 60
     * team_id : 6
     * assign_time : 1590230006
     * address : 广东省广州市天河区棠东丰乐路汇长大厦
     * longitue : 113.38255747899991
     * latitude : 23.125978429658094
     * scope : 300
     * level : 普通
     * sort_id : 100
     * address_extra :
     * leader_id : 7
     * leader_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * leader_name : 米大爷
     * team_name : 测试编辑班组
     * project_name : 广州水电工程3期
     * task_id : 16
     * work_value_finished : 6
     * status_str : 进行中
     * team_num : 2
     * start_date : 2020-05-23
     * end_date : 2020-12-23
     * start_date_real : -
     * end_date_real : -
     * total_days : 27.1
     * total_hours : 217.03
     * total_salary : 21703.42
     * userList : [{"user_id":7,"tel":13500000001,"user_status":2,"user_name":"米大爷","user_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg","majors":"安卓","salary":800,"type":1,"total_days":27.1,"total_hours":217.03,"total_salary":21703.42,"statistic":[{"day":"2020-06-05","salary":21703.42,"worktimes":"1591336602-1592117925","maker_id":7,"maker_name":"米大爷","maker_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg","maker_tel":13500000001,"hours":217.03}]},{"user_id":11,"tel":13700000001,"user_status":2,"user_name":"用户13700000001","user_avatar":"/img/avatar.jpg","majors":"苹果","salary":600,"type":0,"total_days":0,"total_hours":0,"total_salary":0,"statistic":[]}]
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
    private String address_extra;
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
    private double total_days;
    private double total_hours;
    private double total_salary;
    private List<UserListBean> userList;

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

    public String getAddress_extra() {
        return address_extra;
    }

    public void setAddress_extra(String address_extra) {
        this.address_extra = address_extra;
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

    public double getTotal_days() {
        return total_days;
    }

    public void setTotal_days(double total_days) {
        this.total_days = total_days;
    }

    public double getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(double total_hours) {
        this.total_hours = total_hours;
    }

    public double getTotal_salary() {
        return total_salary;
    }

    public void setTotal_salary(double total_salary) {
        this.total_salary = total_salary;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
        /**
         * user_id : 7
         * tel : 13500000001
         * user_status : 2
         * user_name : 米大爷
         * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
         * majors : 安卓
         * salary : 800
         * type : 1
         * total_days : 27.1
         * total_hours : 217.03
         * total_salary : 21703.42
         * statistic : [{"day":"2020-06-05","salary":21703.42,"worktimes":"1591336602-1592117925","maker_id":7,"maker_name":"米大爷","maker_avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg","maker_tel":13500000001,"hours":217.03}]
         */

        private int user_id;
        private long tel;
        private int user_status;
        private String user_name;
        private String user_avatar;
        private String majors;
        private int salary;
        private int type;
        private double total_days;
        private double total_hours;
        private double total_salary;
        private List<StatisticBean> statistic;
        private boolean  expand;

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public long getTel() {
            return tel;
        }

        public void setTel(long tel) {
            this.tel = tel;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getMajors() {
            return majors;
        }

        public void setMajors(String majors) {
            this.majors = majors;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getTotal_days() {
            return total_days;
        }

        public void setTotal_days(double total_days) {
            this.total_days = total_days;
        }

        public double getTotal_hours() {
            return total_hours;
        }

        public void setTotal_hours(double total_hours) {
            this.total_hours = total_hours;
        }

        public double getTotal_salary() {
            return total_salary;
        }

        public void setTotal_salary(double total_salary) {
            this.total_salary = total_salary;
        }

        public List<StatisticBean> getStatistic() {
            return statistic;
        }

        public void setStatistic(List<StatisticBean> statistic) {
            this.statistic = statistic;
        }

        public static class StatisticBean {
            /**
             * day : 2020-06-05
             * salary : 21703.42
             * worktimes : 1591336602-1592117925
             * maker_id : 7
             * maker_name : 米大爷
             * maker_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
             * maker_tel : 13500000001
             * hours : 217.03
             */

            private String day;
            private double salary;
            private String worktimes;
            private int maker_id;
            private String maker_name;
            private String maker_avatar;
            private long maker_tel;
            private double hours;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public double getSalary() {
                return salary;
            }

            public void setSalary(double salary) {
                this.salary = salary;
            }

            public String getWorktimes() {
                return worktimes;
            }

            public void setWorktimes(String worktimes) {
                this.worktimes = worktimes;
            }

            public int getMaker_id() {
                return maker_id;
            }

            public void setMaker_id(int maker_id) {
                this.maker_id = maker_id;
            }

            public String getMaker_name() {
                return maker_name;
            }

            public void setMaker_name(String maker_name) {
                this.maker_name = maker_name;
            }

            public String getMaker_avatar() {
                return maker_avatar;
            }

            public void setMaker_avatar(String maker_avatar) {
                this.maker_avatar = maker_avatar;
            }

            public long getMaker_tel() {
                return maker_tel;
            }

            public void setMaker_tel(long maker_tel) {
                this.maker_tel = maker_tel;
            }

            public double getHours() {
                return hours;
            }

            public void setHours(double hours) {
                this.hours = hours;
            }
        }
    }
}