package com.aopcloud.palmproject.api;

import com.aopcloud.palmproject.BuildConfig;

/**
 * @PackageName : com.aopcloud.palmproject.api
 * @File : ApiConstants.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public interface ApiConstants {

    /**
     * 本地
     */
    String BASE_URL_LOCAL = "";
    /**
     * 测试
     */
    String BASE_URL_TEST = "";
    /**
     * 正式
     */
    String BASE_URL_ONLINE = "";


    /**
     * 生产
     * <p>
     * 生产环境 只需要更换域名 正式 测试域名
     */
    String BASE_URL_PRODUCTION = BuildConfig.BASE_URL;


    /**
     * file_upload
     * {@link EventTags#file_upload}
     */
    String file_upload = BASE_URL_PRODUCTION + "/file/upload";


    /**
     * sms_code_send
     * {@link EventTags#sms_code_send}
     */
    String sms_code_send = BASE_URL_PRODUCTION + "/login/code/send";


    /**
     * sms_code_get
     * {@link EventTags#sms_code_get}
     */
    String sms_code_get = BASE_URL_PRODUCTION + "/login/code/get";


    /**
     * 登录
     * {@link EventTags#login_pwd}
     */
    String login_pwd = BASE_URL_PRODUCTION + "/login/login";

    /**
     * 登录
     * {@link EventTags#login_sms_code}
     */
    String login_sms_code = BASE_URL_PRODUCTION + "/login/login";


    /**
     * 注册
     * {@link EventTags#register}
     */
    String register = BASE_URL_PRODUCTION + "/login/reg";


    /**
     * retrieve_password
     * {@link EventTags#retrieve_password}
     */
    String retrieve_password = BASE_URL_PRODUCTION + "/login/reset";


    /**
     * reset_password
     * {@link EventTags#reset_password}
     */
    String reset_password = BASE_URL_PRODUCTION + "/user/reset";

    /**
     * user_info
     * {@link EventTags#user_info}
     */
    String user_info = BASE_URL_PRODUCTION + "/user/get";
    /**
     * user_info_update
     * {@link EventTags#user_info_update}
     */
    String user_info_update = BASE_URL_PRODUCTION + "/user/update";

    /**
     * company_add
     * {@link EventTags#company_add}
     */
    String company_add = BASE_URL_PRODUCTION + "/company/add";


    /**
     * reportjob_add
     * {@link EventTags#reportjob_add}
     */
    String reportjob_add = BASE_URL_PRODUCTION + "/reportjob/add";

    /**
     * reportjob_add
     * {@link EventTags#reportjob_add}
     */
    String reportjob_all = BASE_URL_PRODUCTION + "/reportjob/all";
    /**
     * reportjob_add
     * {@link EventTags#reportjob_add}
     */
    String reportjob_statistics = BASE_URL_PRODUCTION + "/reportjob/statistics";

    /**
     * company_get
     * {@link EventTags#company_get}
     */
    String company_get = BASE_URL_PRODUCTION + "/company/get";

    /**
     * company_update
     * {@link EventTags#company_update}
     */
    String company_update = BASE_URL_PRODUCTION + "/company/update";


    /**
     * manage_all
     * {@link EventTags#manage_all}
     */
    String manage_all = BASE_URL_PRODUCTION + "/manage/all";


    /**
     * manage_update
     * {@link EventTags#manage_update}
     */
    String manage_update = BASE_URL_PRODUCTION + "/manage/update";
    /**
     * manage_del
     * {@link EventTags#manage_del}
     */
    String manage_del = BASE_URL_PRODUCTION + "/manage/del";

    /**
     * manage_add
     * {@link EventTags#manage_add}
     */
    String manage_add = BASE_URL_PRODUCTION + "/manage/add";


    /**
     * company_my
     * {@link EventTags#company_my}
     */
    String company_my = BASE_URL_PRODUCTION + "/company/my";


    /**
     * company_apply
     * {@link EventTags#company_apply}
     */
    String company_apply = BASE_URL_PRODUCTION + "/company/apply";


    /**
     * company_usermange
     * {@link EventTags#company_usermange}
     */
    String company_usermange = BASE_URL_PRODUCTION + "/company/usermange";


    /**
     * company_status
     * {@link EventTags#company_status}
     */
    String company_status = BASE_URL_PRODUCTION + "/company/status";


    /**
     * company_changecode
     * {@link EventTags#company_changecode}
     */
    String company_changecode = BASE_URL_PRODUCTION + "/company/changecode";


    /**
     * company_invite
     * {@link EventTags#company_invite}
     */
    String company_invite = BASE_URL_PRODUCTION + "/company/invite";


    /**
     * department_add
     * {@link EventTags#department_add}
     */
    String department_add = BASE_URL_PRODUCTION + "/department/add";
    /**
     * department_update
     * {@link EventTags#department_update}
     */
    String department_update = BASE_URL_PRODUCTION + "/department/update";
    /**
     * department_del
     * {@link EventTags#department_del}
     */
    String department_del = BASE_URL_PRODUCTION + "/department/del";


    /**
     * department_department
     * {@link EventTags#department_department}
     */
    String department_department = BASE_URL_PRODUCTION + "/department/department";
    /**
     * department_alluser
     * {@link EventTags#department_alluser}
     */
    String department_alluser = BASE_URL_PRODUCTION + "/department/alluser";

    /**
     * department_adduser
     * {@link EventTags#department_adduser}
     */
    String department_adduser = BASE_URL_PRODUCTION + "/department/adduser";

    /**
     * department_deluser
     * {@link EventTags#department_deluser}
     */
    String department_deluser = BASE_URL_PRODUCTION + "/department/deluser";


    /**
     * department_taguser
     * {@link EventTags#department_taguser}
     */
    String department_taguser = BASE_URL_PRODUCTION + "/department/taguser";


    /**
     * department_nduser
     * {@link EventTags#department_nduser}
     */
    String department_nduser = BASE_URL_PRODUCTION + "/department/nduser";


    /**
     * roletag_all
     * {@link EventTags#roletag_all}
     */
    String roletag_all = BASE_URL_PRODUCTION + "/roletag/all";


    /**
     * roletag_add
     * {@link EventTags#roletag_add}
     */
    String roletag_add = BASE_URL_PRODUCTION + "/roletag/add";


    /**
     * roletag_update
     * {@link EventTags#roletag_update}
     */
    String roletag_update = BASE_URL_PRODUCTION + "/roletag/update";


    /**
     * roletag_del
     * {@link EventTags#roletag_del}
     */
    String roletag_del = BASE_URL_PRODUCTION + "/roletag/del";


    /**
     * roletag_get
     * {@link EventTags#roletag_get}
     */
    String roletag_get = BASE_URL_PRODUCTION + "/roletag/get";


    /**
     * project_add
     * {@link EventTags#project_add}
     */
    String project_add = BASE_URL_PRODUCTION + "/project/add";


    /**
     * project_get
     * {@link EventTags#project_get}
     */
    String project_get = BASE_URL_PRODUCTION + "/project/get";


    /**
     * project_all
     * {@link EventTags#project_all}
     */
    String project_all = BASE_URL_PRODUCTION + "/project/all";


    /**
     * project_member
     * {@link EventTags#project_member}
     */
    String project_member = BASE_URL_PRODUCTION + "/project/member";

    /**
     * department_all
     * {@link EventTags#department_all}
     */
    String department_all = BASE_URL_PRODUCTION + "/department/all";


    /**
     * company_del
     * {@link EventTags#company_del}
     */
    String company_del = BASE_URL_PRODUCTION + "/company/del";


    /**
     * project_adduser
     * {@link EventTags#project_adduser}
     */
    String project_adduser = BASE_URL_PRODUCTION + "/project/adduser";


    /**
     * project_deluser
     * {@link EventTags#project_deluser}
     */
    String project_deluser = BASE_URL_PRODUCTION + "/project/deluser";


    /**
     * company_allstatus
     * {@link EventTags#company_allstatus}
     */
    String company_allstatus = BASE_URL_PRODUCTION + "/company/allstatus";

    /**
     * project_feedback
     * {@link EventTags#project_feedback}
     */
    String project_feedback = BASE_URL_PRODUCTION + "/project/feedback";


    /**
     * trends_all
     * {@link EventTags#trends_all}
     */
    String trends_all = BASE_URL_PRODUCTION + "/trends/all";

    /**
     * trends_debate
     * {@link EventTags#trends_debate}
     */
    String trends_debate = BASE_URL_PRODUCTION + "/trends/debate";

    /**
     * trends_del
     * {@link EventTags#trends_del}
     */
    String trends_del = BASE_URL_PRODUCTION + "/trends/del";


    /**
     * task_add
     * {@link EventTags#task_add}
     */
    String task_add = BASE_URL_PRODUCTION + "/task/add";


    /**
     * task_all
     * {@link EventTags#task_all}
     */
    String task_all = BASE_URL_PRODUCTION + "/task/all";


    /**
     * task_del
     * {@link EventTags#task_del}
     */
    String task_del = BASE_URL_PRODUCTION + "/task/del";


    /**
     * task_update
     * {@link EventTags#task_update}
     */
    String task_update = BASE_URL_PRODUCTION + "/task/update";


    /**
     * scenes_all
     * {@link EventTags#scenes_all}
     */
    String scenes_all = BASE_URL_PRODUCTION + "/scenes/all";

    /**
     * scenes_add
     * {@link EventTags#scenes_add}
     */
    String scenes_add = BASE_URL_PRODUCTION + "/scenes/add";


    /**
     * team_add
     * {@link EventTags#team_add}
     */
    String team_add = BASE_URL_PRODUCTION + "/team/add";


    /**
     * team_edit
     * {@link EventTags#team_edit}
     */
    String team_edit = BASE_URL_PRODUCTION + "/team/update";


    /**
     * team_del
     * {@link EventTags#team_del}
     */
    String team_del = BASE_URL_PRODUCTION + "/team/del";

    /**
     * project_team
     * {@link EventTags#project_team}
     */
    String project_team = BASE_URL_PRODUCTION + "/project/team";

    /**
     * company_team
     * {@link EventTags#company_team}
     */
    String company_team = BASE_URL_PRODUCTION + "/company/team";


    /**
     * teammember_all
     * {@link EventTags#teammember_all}
     */
    String teammember_all = BASE_URL_PRODUCTION + "/teammember/all";


    /**
     * teammember_add
     * {@link EventTags#teammember_add}
     */
    String teammember_add = BASE_URL_PRODUCTION + "/teammember/add";

    /**
     * teammember_edit
     * {@link EventTags#teammember_edit}
     */
    String teammember_edit = BASE_URL_PRODUCTION + "/teammember/update";

    /**
     * teammember_del
     * {@link EventTags#teammember_del}
     */
    String teammember_del = BASE_URL_PRODUCTION + "/teammember/del";


    /**
     * task_get
     * {@link EventTags#task_get}
     */
    String task_get = BASE_URL_PRODUCTION + "/task/get";


    /**
     * task_assign
     * {@link EventTags#task_assign}
     */
    String task_assign = BASE_URL_PRODUCTION + "/task/assign";


    /**
     * project_tasks
     * {@link EventTags#project_tasks}
     */
    String project_tasks = BASE_URL_PRODUCTION + "/project/tasks";

    /**
     * task_tasks
     * {@link EventTags#task_tasks}
     */
    String task_tasks = BASE_URL_PRODUCTION + "/task/tasks";


    /**
     * project_update
     * {@link EventTags#project_update}
     */
    String project_update = BASE_URL_PRODUCTION + "/project/update";

    /**
     * project_projects
     * {@link EventTags#project_projects}
     */
    String project_projects = BASE_URL_PRODUCTION + "/project/projects";
    /**
     * task_feedback
     * {@link EventTags#task_feedback}
     */
    String task_feedback = BASE_URL_PRODUCTION + "/task/feedback";

    /**
     * attendance_signup
     * {@link EventTags#attendance_signup}
     */
    String attendance_signup = BASE_URL_PRODUCTION + "/attendance/signup";

    /**
     * attendance_project
     * {@link EventTags#attendance_project}
     */
    String attendance_project = BASE_URL_PRODUCTION + "/attendance/project";


    /**
     * trajectory_add
     * {@link EventTags#trajectory_add}
     */
    String trajectory_add = BASE_URL_PRODUCTION + "/trajectory/add";

    /**
     * trajectory_all
     * {@link EventTags#trajectory_all}
     */
    String trajectory_all = BASE_URL_PRODUCTION + "/trajectory/all";


    /**
     * attendance_status
     * {@link EventTags#attendance_status}
     */
    String attendance_status = BASE_URL_PRODUCTION + "/attendance/status";


    /**
     * salary_all
     * {@link EventTags#salary_all}
     */
    String salary_all = BASE_URL_PRODUCTION + "/salary/all";


    /**
     * roletag_sort
     * {@link EventTags#roletag_sort}
     */
    String roletag_sort = BASE_URL_PRODUCTION + "/roletag/sort";

    /**
     * task_sort
     * {@link EventTags#task_sort}
     */
    String task_sort = BASE_URL_PRODUCTION + "/task/all";
    /**
     * trends_project
     * {@link EventTags#trends_project}
     */
    String trends_project = BASE_URL_PRODUCTION + "/trends/project";
    /**
     * team_copy
     * {@link EventTags#team_copy}
     */
    String team_copy = BASE_URL_PRODUCTION + "/team/copy";


    /**
     * msg_todo
     * {@link EventTags#msg_todo}
     */
    String msg_todo = BASE_URL_PRODUCTION + "/msg/todo";


    /**
     * attendance_all
     * {@link EventTags#attendance_all}
     */
    String attendance_all = BASE_URL_PRODUCTION + "/attendance/all";


    /**
     * msg_my
     * {@link EventTags#msg_my}
     */
    String msg_my = BASE_URL_PRODUCTION + "/msg/my";


    /**
     * task_addsub
     * {@link EventTags#task_addsub}
     */
    String task_addsub = BASE_URL_PRODUCTION + "/task/addsub";

    String check_app_version = BASE_URL_PRODUCTION + "/version?device=android";


    final class EventTags {
        public final static int BEGIN_EVENT = 0;


        /**
         * sms_code_send
         * {@link ApiConstants#sms_code_send}
         */
        public final static int sms_code_send = BEGIN_EVENT + 1;


        /**
         * sms_code_get
         * {@link ApiConstants#sms_code_get}
         */
        public final static int sms_code_get = BEGIN_EVENT + 2;


        /**
         * 登录
         * {@link ApiConstants#login_pwd}
         */
        public final static int login_pwd = BEGIN_EVENT + 3;


        /**
         * login_sms_code
         * {@link ApiConstants#login_sms_code}
         */
        public final static int login_sms_code = BEGIN_EVENT + 4;


        /**
         * register
         * {@link ApiConstants#register}
         */
        public final static int register = BEGIN_EVENT + 5;


        /**
         * retrieve_password
         * {@link ApiConstants#retrieve_password}
         */
        public final static int retrieve_password = BEGIN_EVENT + 6;

        /**
         * reset_password
         * {@link ApiConstants#reset_password}
         */
        public final static int reset_password = BEGIN_EVENT + 7;


        /**
         * user_info
         * {@link ApiConstants#user_info}
         */
        public final static int user_info = BEGIN_EVENT + 8;
        /**
         * user_info_update
         * {@link ApiConstants#user_info_update}
         */
        public final static int user_info_update = BEGIN_EVENT + 9;


        /**
         * reportjob_add
         * {@link ApiConstants#reportjob_add}
         */
        public final static int reportjob_add = BEGIN_EVENT + 10;


        /**
         * reportjob_all
         * {@link ApiConstants#reportjob_all}
         */
        public final static int reportjob_all = BEGIN_EVENT + 11;

        /**
         * reportjob_statistics
         * {@link ApiConstants#reportjob_statistics}
         */
        public final static int reportjob_statistics = BEGIN_EVENT + 12;


        /**
         * company_add
         * {@link ApiConstants#company_add}
         */
        public final static int company_add = BEGIN_EVENT + 13;


        /**
         * company_get
         * {@link ApiConstants#company_get}
         */
        public final static int company_get = BEGIN_EVENT + 14;

        /**
         * company_update
         * {@link ApiConstants#company_update}
         */
        public final static int company_update = BEGIN_EVENT + 15;

        /**
         * manage_all
         * {@link ApiConstants#manage_all}
         */
        public final static int manage_all = BEGIN_EVENT + 16;


        /**
         * manage_update
         * {@link ApiConstants#manage_update}
         */
        public final static int manage_update = BEGIN_EVENT + 17;
        /**
         * manage_del
         * {@link ApiConstants#manage_del}
         */
        public final static int manage_del = BEGIN_EVENT + 18;

        /**
         * manage_add
         * {@link ApiConstants#manage_add}
         */
        public final static int manage_add = BEGIN_EVENT + 19;

        /**
         * company_my
         * {@link ApiConstants#company_my}
         */
        public final static int company_my = BEGIN_EVENT + 20;


        /**
         * company_apply
         * {@link ApiConstants#company_apply}
         */
        public final static int company_apply = BEGIN_EVENT + 21;


        /**
         * company_usermange
         * {@link ApiConstants#company_usermange}
         */
        public final static int company_usermange = BEGIN_EVENT + 22;


        /**
         * company_status
         * {@link ApiConstants#company_status}
         */
        public final static int company_status = BEGIN_EVENT + 23;


        /**
         * company_changecode
         * {@link ApiConstants#company_changecode}
         */
        public final static int company_changecode = BEGIN_EVENT + 24;


        /**
         * company_invite
         * {@link ApiConstants#company_invite}
         */
        public final static int company_invite = BEGIN_EVENT + 25;


        /**
         * department_add
         * {@link ApiConstants#department_add}
         */
        public final static int department_add = BEGIN_EVENT + 26;


        /**
         * department_update
         * {@link ApiConstants#department_update}
         */
        public final static int department_update = BEGIN_EVENT + 27;


        /**
         * department_del
         * {@link ApiConstants#department_del}
         */
        public final static int department_del = BEGIN_EVENT + 28;


        /**
         * department_alluser
         * {@link ApiConstants#department_alluser}
         */
        public final static int department_alluser = BEGIN_EVENT + 29;


        /**
         * department_department
         * {@link ApiConstants#department_department}
         */
        public final static int department_department = BEGIN_EVENT + 30;


        /**
         * department_adduser
         * {@link ApiConstants#department_adduser}
         */
        public final static int department_adduser = BEGIN_EVENT + 31;

        /**
         * department_deluser
         * {@link ApiConstants#department_deluser}
         */
        public final static int department_deluser = BEGIN_EVENT + 32;


        /**
         * department_taguser
         * {@link ApiConstants#department_taguser}
         */
        public final static int department_taguser = BEGIN_EVENT + 33;


        /**
         * department_nduser
         * {@link ApiConstants#department_nduser}
         */
        public final static int department_nduser = BEGIN_EVENT + 34;


        /**
         * roletag_all
         * {@link ApiConstants#roletag_all}
         */
        public final static int roletag_all = BEGIN_EVENT + 35;


        /**
         * roletag_add
         * {@link ApiConstants#roletag_add}
         */
        public final static int roletag_add = BEGIN_EVENT + 36;


        /**
         * roletag_update
         * {@link ApiConstants#roletag_update}
         */
        public final static int roletag_update = BEGIN_EVENT + 37;


        /**
         * roletag_del
         * {@link ApiConstants#roletag_del}
         */
        public final static int roletag_del = BEGIN_EVENT + 38;


        /**
         * roletag_get
         * {@link ApiConstants#roletag_get}
         */
        public final static int roletag_get = BEGIN_EVENT + 39;


        /**
         * project_add
         * {@link ApiConstants#project_add}
         */
        public final static int project_add = BEGIN_EVENT + 40;


        /**
         * project_get
         * {@link ApiConstants#project_get}
         */
        public final static int project_get = BEGIN_EVENT + 41;

        /**
         * project_all
         * {@link ApiConstants#project_all}
         */
        public final static int project_all = BEGIN_EVENT + 42;

        /**
         * project_member
         * {@link ApiConstants#project_member}
         */
        public final static int project_member = BEGIN_EVENT + 43;


        /**
         * department_all
         * {@link ApiConstants#department_all}
         */
        public final static int department_all = BEGIN_EVENT + 44;

        /**
         * company_del
         * {@link ApiConstants#company_del}
         */
        public final static int company_del = BEGIN_EVENT + 45;


        /**
         * project_adduser
         * {@link ApiConstants#project_adduser}
         */
        public final static int project_adduser = BEGIN_EVENT + 46;


        /**
         * project_deluser
         * {@link ApiConstants#project_deluser}
         */
        public final static int project_deluser = BEGIN_EVENT + 47;

        /**
         * company_allstatus
         * {@link ApiConstants#company_allstatus}
         */
        public final static int company_allstatus = BEGIN_EVENT + 48;


        /**
         * project_feedback
         * {@link ApiConstants#project_feedback}
         */
        public final static int project_feedback = BEGIN_EVENT + 49;


        /**
         * trends_all
         * {@link ApiConstants#trends_all}
         */
        public final static int trends_all = BEGIN_EVENT + 50;


        /**
         * trends_debate
         * {@link ApiConstants#trends_debate}
         */
        public final static int trends_debate = BEGIN_EVENT + 51;


        /**
         * trends_del
         * {@link ApiConstants#trends_del}
         */
        public final static int trends_del = BEGIN_EVENT + 52;


        /**
         * task_add
         * {@link ApiConstants#task_add}
         */
        public final static int task_add = BEGIN_EVENT + 53;


        /**
         * task_all
         * {@link ApiConstants#task_all}
         */
        public final static int task_all = BEGIN_EVENT + 54;


        /**
         * task_del
         * {@link ApiConstants#task_del}
         */
        public final static int task_del = BEGIN_EVENT + 55;


        /**
         * task_update
         * {@link ApiConstants#task_update}
         */
        public final static int task_update = BEGIN_EVENT + 56;


        /**
         * scenes_all
         * {@link ApiConstants#scenes_all}
         */
        public final static int scenes_all = BEGIN_EVENT + 57;


        /**
         * scenes_all
         * {@link ApiConstants#scenes_all}
         */
        public final static int scenes_add = BEGIN_EVENT + 58;


        /**
         * team_add
         * {@link ApiConstants#team_add}
         */
        public final static int team_add = BEGIN_EVENT + 59;


        /**
         * team_edit
         * {@link ApiConstants#team_edit}
         */
        public final static int team_edit = BEGIN_EVENT + 60;


        /**
         * team_del
         * {@link ApiConstants#team_del}
         */
        public final static int team_del = BEGIN_EVENT + 61;

        /**
         * project_team
         * {@link ApiConstants#project_team}
         */
        public final static int project_team = BEGIN_EVENT + 62;

        /**
         * company_team
         * {@link ApiConstants#company_team}
         */
        public final static int company_team = BEGIN_EVENT + 63;


        /**
         * teammember_add
         * {@link ApiConstants#teammember_add}
         */
        public final static int teammember_add = BEGIN_EVENT + 64;

        /**
         * teammember_edit
         * {@link ApiConstants#teammember_edit}
         */
        public final static int teammember_edit = BEGIN_EVENT + 65;
        /**
         * teammember_del
         * {@link ApiConstants#teammember_del}
         */
        public final static int teammember_del = BEGIN_EVENT + 66;
        /**
         * teammember_all
         * {@link ApiConstants#teammember_all}
         */
        public final static int teammember_all = BEGIN_EVENT + 67;


        /**
         * task_get
         * {@link ApiConstants#task_get}
         */
        public final static int task_get = BEGIN_EVENT + 68;


        /**
         * task_assign
         * {@link ApiConstants#task_assign}
         */
        public final static int task_assign = BEGIN_EVENT + 69;

        /**
         * project_tasks
         * {@link ApiConstants#project_tasks}
         */
        public final static int project_tasks = BEGIN_EVENT + 70;

        /**
         * task_tasks
         * {@link ApiConstants#task_tasks}
         */
        public final static int task_tasks = BEGIN_EVENT + 71;


        /**
         * project_update
         * {@link ApiConstants#project_update}
         */
        public final static int project_update = BEGIN_EVENT + 72;


        /**
         * project_projects
         * {@link ApiConstants#project_projects}
         */
        public final static int project_projects = BEGIN_EVENT + 73;


        /**
         * task_feedback
         * {@link ApiConstants#task_feedback}
         */
        public final static int task_feedback = BEGIN_EVENT + 74;


        /**
         * attendance_signup
         * {@link ApiConstants#attendance_signup}
         */
        public final static int attendance_signup = BEGIN_EVENT + 75;


        /**
         * attendance_project
         * {@link ApiConstants#attendance_project}
         */
        public final static int attendance_project = BEGIN_EVENT + 76;


        /**
         * trajectory_add
         * {@link ApiConstants#trajectory_add}
         */
        public final static int trajectory_add = BEGIN_EVENT + 77;


        /**
         * trajectory_all
         * {@link ApiConstants#trajectory_all}
         */
        public final static int trajectory_all = BEGIN_EVENT + 78;



        /**
         * attendance_status
         * {@link ApiConstants#attendance_status}
         */
        public final static int attendance_status = BEGIN_EVENT + 79;


        /**
         * salary_all
         * {@link ApiConstants#salary_all}
         */
        public final static int salary_all = BEGIN_EVENT + 80;




        /**
         * roletag_sort
         * {@link ApiConstants#roletag_sort}
         */
        public final static int roletag_sort = BEGIN_EVENT + 81;

        /**
         * task_sort
         * {@link ApiConstants#task_sort}
         */
        public final static int task_sort = BEGIN_EVENT + 82;
        /**
         * trends_project
         * {@link ApiConstants#trends_project}
         */
        public final static int trends_project = BEGIN_EVENT + 83;


        /**
         * team_copy
         * {@link ApiConstants#team_copy}
         */
        public final static int team_copy = BEGIN_EVENT + 84;



        /**
         * msg_todo
         * {@link ApiConstants#msg_todo}
         */
        public final static int msg_todo = BEGIN_EVENT + 85;

        /**
         * msg_todo
         * {@link ApiConstants#attendance_all}
         */
        public final static int attendance_all = BEGIN_EVENT + 86;


        /**
         * msg_my
         * {@link ApiConstants#msg_my}
         */
        public final static int msg_my = BEGIN_EVENT + 87;

        /**
         * task_addsub
         * {@link ApiConstants#task_addsub}
         */
        public final static int task_addsub = BEGIN_EVENT + 88;


        public final static int check_app_version = BEGIN_EVENT + 90;

    }


}
