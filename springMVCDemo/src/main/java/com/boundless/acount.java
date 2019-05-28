package com.boundless;

import com.boundless.controller.person;

import java.util.List;
import java.util.Map;

public class acount {

    private String psw;
    private String money;
    private List<person> ps;
    private Map<String,String> dict;

    public Map<String, String> getDict() {
        return dict;
    }

    public void setDict(Map<String, String> dict) {
        this.dict = dict;
    }

    public List<person> getPs() {
        return ps;
    }

    public void setPs(List<person> ps) {
        this.ps = ps;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "acount{" +
                "psw='" + psw + '\'' +
                ", money='" + money + '\'' +
                ", ps=" + ps +
                ", dict=" + dict +
                '}';
    }
}
