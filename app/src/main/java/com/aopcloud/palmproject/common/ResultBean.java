package com.aopcloud.palmproject.common;

/**
 * @PackageName : com.aopcloud.palmproject.common
 * @File : ResultBean.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ResultBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"token":"6c9e9aa74739a4748713d94860f15e71","is_in_company":1}
     */

    private int code;
    private String msg;
    private String data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
