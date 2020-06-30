package com.aopcloud.palmproject.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : AddressDataBean.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class AddressDataBean implements IPickerViewData {


    /**
     * id : 35
     * parent_id : 0
     * region_name : 台湾
     * region_type : 0
     * agency_id : 0
     * py : taiwan
     * update_time : 0
     * create_time : 0
     * cityList : [{"id":397,"parent_id":35,"region_name":"台湾","region_type":1,"agency_id":0,"py":"","update_time":0,"create_time":0,"areaList":[{"id":3400,"parent_id":397,"region_name":"澎湖县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3399,"parent_id":397,"region_name":"花莲县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3398,"parent_id":397,"region_name":"台东县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3397,"parent_id":397,"region_name":"屏东县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3396,"parent_id":397,"region_name":"云林县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3395,"parent_id":397,"region_name":"南投县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3394,"parent_id":397,"region_name":"彰化县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3393,"parent_id":397,"region_name":"苗栗县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3392,"parent_id":397,"region_name":"桃园县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3391,"parent_id":397,"region_name":"宜兰县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3390,"parent_id":397,"region_name":"嘉义","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3389,"parent_id":397,"region_name":"新竹","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3388,"parent_id":397,"region_name":"台南","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3387,"parent_id":397,"region_name":"台中","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3386,"parent_id":397,"region_name":"基隆","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3385,"parent_id":397,"region_name":"高雄","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3384,"parent_id":397,"region_name":"台北","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0}]}]
     */

    private int id;
    private String region_name;
    private int region_type;
    private String py;
    private List<CityListBean> cityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getRegion_type() {
        return region_type;
    }

    public void setRegion_type(int region_type) {
        this.region_type = region_type;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }


    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    @Override
    public String getPickerViewText() {
        return region_name;
    }

    public static class CityListBean implements IPickerViewData{
        /**
         * id : 397
         * parent_id : 35
         * region_name : 台湾
         * region_type : 1
         * agency_id : 0
         * py :
         * update_time : 0
         * create_time : 0
         * areaList : [{"id":3400,"parent_id":397,"region_name":"澎湖县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3399,"parent_id":397,"region_name":"花莲县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3398,"parent_id":397,"region_name":"台东县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3397,"parent_id":397,"region_name":"屏东县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3396,"parent_id":397,"region_name":"云林县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3395,"parent_id":397,"region_name":"南投县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3394,"parent_id":397,"region_name":"彰化县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3393,"parent_id":397,"region_name":"苗栗县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3392,"parent_id":397,"region_name":"桃园县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3391,"parent_id":397,"region_name":"宜兰县","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3390,"parent_id":397,"region_name":"嘉义","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3389,"parent_id":397,"region_name":"新竹","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3388,"parent_id":397,"region_name":"台南","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3387,"parent_id":397,"region_name":"台中","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3386,"parent_id":397,"region_name":"基隆","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3385,"parent_id":397,"region_name":"高雄","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0},{"id":3384,"parent_id":397,"region_name":"台北","region_type":2,"agency_id":0,"py":"","update_time":0,"create_time":0}]
         */

        private int id;
        private int parent_id;
        private String region_name;
        private String py;
        private List<AreaListBean> areaList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }


        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }


        public List<AreaListBean> getAreaList() {
            return areaList;
        }

        public void setAreaList(List<AreaListBean> areaList) {
            this.areaList = areaList;
        }

        @Override
        public String getPickerViewText() {
            return region_name;
        }

        public static class AreaListBean  implements IPickerViewData{
            /**
             * id : 3400
             * parent_id : 397
             * region_name : 澎湖县
             * region_type : 2
             * agency_id : 0
             * py :
             * update_time : 0
             * create_time : 0
             */

            private int id;
            private int parent_id;
            private String region_name;
            private String py;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public String getRegion_name() {
                return region_name;
            }

            public void setRegion_name(String region_name) {
                this.region_name = region_name;
            }


            public String getPy() {
                return py;
            }

            public void setPy(String py) {
                this.py = py;
            }

            @Override
            public String getPickerViewText() {
                return region_name;
            }
        }
    }
}
