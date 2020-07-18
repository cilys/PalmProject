package com.aopcloud.palmproject.bean;

import java.io.Serializable;
import java.util.List;

public class WeatherBean implements Serializable {
    /*
//    http://wthrcdn.etouch.cn/weather_mini?city=北京
//    {"data":{"yesterday":{"date":"17日星期五","high":"高温 31℃","fx":"西南风","low":"低温 22℃","fl":"","type":"小雨"},"city":"北京","forecast":[{"date":"18日星期六","high":"高温 29℃","fengli":"","low":"低温 23℃","fengxiang":"东风","type":"小雨"},{"date":"19日星期天","high":"高温 33℃","fengli":"","low":"低温 21℃","fengxiang":"东北风","type":"多云"},{"date":"20日星期一","high":"高温 34℃","fengli":"","low":"低温 23℃","fengxiang":"西南风","type":"晴"},{"date":"21日星期二","high":"高温 35℃","fengli":"","low":"低温 22℃","fengxiang":"南风","type":"晴"},{"date":"22日星期三","high":"高温 33℃","fengli":"","low":"低温 22℃","fengxiang":"南风","type":"多云"}],"ganmao":"感冒低发期，天气舒适，请注意多吃蔬菜水果，多喝水哦。","wendu":"25"},"status":1000,"desc":"OK"}

    private String status;
    private String desc;
    private Item data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Item getData() {
        return data;
    }

    public void setData(Item data) {
        this.data = data;
    }

    public static class Item implements Serializable {
        private String city;
        private String ganmao;
        private String wendu;
        private Weather yesterday;
        private List<Weather> forecast;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public Weather getYesterday() {
            return yesterday;
        }

        public void setYesterday(Weather yesterday) {
            this.yesterday = yesterday;
        }

        public List<Weather> getForecast() {
            return forecast;
        }

        public void setForecast(List<Weather> forecast) {
            this.forecast = forecast;
        }

        public static class Weather implements Serializable {
            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }*/

//    http://www.weather.com.cn/data/cityinfo/101190101.html
//{"weatherinfo":{"city":"南京","cityid":"101190101","temp1":"18℃","temp2":"21℃","weather":"大雨","img1":"n9.gif","img2":"d9.gif","ptime":"18:00"}}

    private static String currentWeatherInfo;
    private static String cityName;
    private static String address;

    public static void setAddress(String address) {
        WeatherBean.address = address;
    }

    public static String getAddress() {
        return address;
    }

    public static void setCurrentWeatherInfo(String currentWeatherInfo) {
        WeatherBean.currentWeatherInfo = currentWeatherInfo;
    }

    public static void setCityName(String cityName) {
        WeatherBean.cityName = cityName;
        setCurrentWeatherInfo(null);
    }

    public static String getCityName() {
        return cityName;
    }

    public static String getCurrentWeatherInfo() {
        return currentWeatherInfo;
    }

    private Item weatherinfo;
    public Item getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(Item weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public static class Item implements Serializable {
        private String city;
        private String cityid;
        private String temp1;
        private String temp2;
        private String weather;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemp1() {
            return temp1;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }
    }
}
