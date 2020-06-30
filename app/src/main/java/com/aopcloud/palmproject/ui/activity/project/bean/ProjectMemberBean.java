package com.aopcloud.palmproject.ui.activity.project.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : ProjectMemberBean.java
 * @Date : 2020/5/4 2020/5/4
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectMemberBean {
    /**
     * users : [{"user_id":3,"user_avatar":"/upload/2123/1.jpg","user_name":"曾大大","user_status":2,"type":0},{"user_id":4,"user_avatar":"/upload/2123/1.jpg","user_name":"曾大大","user_status":2,"type":1}]
     * teams : [{"team_name":"水电一班1","team_id":1,"user_count":2}]
     * leader : {"user_id":1,"user_avatar":"/upload/2123/1.jpg","user_name":"曾大大","user_status":2}
     */

    private LeaderBean leader;
    private List<UsersBean> users;
    private List<TeamsBean> teams;

    public LeaderBean getLeader() {
        return leader;
    }

    public void setLeader(LeaderBean leader) {
        this.leader = leader;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public List<TeamsBean> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamsBean> teams) {
        this.teams = teams;
    }

    public static class LeaderBean {
        /**
         * user_id : 1
         * user_avatar : /upload/2123/1.jpg
         * user_name : 曾大大
         * user_status : 2
         */

        private int user_id;
        private String user_avatar;
        private String user_name;
        private int user_status;
        private  long tel;

        public long getTel() {
            return tel;
        }

        public void setTel(long tel) {
            this.tel = tel;
        }

        /**
         * project_user_id : 6
         */


        private int project_user_id;

        public int getProject_user_id() {
            return project_user_id;
        }

        public void setProject_user_id(int project_user_id) {
            this.project_user_id = project_user_id;
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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }
    }

    public static class UsersBean {
        /**
         * user_id : 3
         * user_avatar : /upload/2123/1.jpg
         * user_name : 曾大大
         * user_status : 2
         * type : 0
         */

        private int user_id;
        private String user_avatar;
        private String user_name;
        private int user_status;
        private int type;//0,管理员，1：施工员
        /**
         * project_user_id : 6
         */
        private  long tel;

        public long getTel() {
            return tel;
        }

        public void setTel(long tel) {
            this.tel = tel;
        }
        private int project_user_id;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getProject_user_id() {
            return project_user_id;
        }

        public void setProject_user_id(int project_user_id) {
            this.project_user_id = project_user_id;
        }
    }

    public static class TeamsBean {

        /**
         * team_name : 吃货
         * team_id : 17
         * member : [{"user_id":7,"avatar":"/upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg","user_name":"米大爷","majors":"点菜工","salary":200,"type":1}]
         * user_count : 1
         */

        private String team_name;
        private int team_id;
        private int user_count;
        private List<MemberBean> member;

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }

        public List<MemberBean> getMember() {
            return member;
        }

        public void setMember(List<MemberBean> member) {
            this.member = member;
        }

        public static class MemberBean {
            /**
             * user_id : 7
             * avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
             * user_name : 米大爷
             * majors : 点菜工
             * salary : 200
             * type : 1
             */

            private int user_id;
            private String avatar;
            private String user_name;
            private String majors;
            private int salary;
            private int type;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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
        }
    }
}
