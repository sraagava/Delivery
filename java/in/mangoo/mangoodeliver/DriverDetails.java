package in.mangoo.mangoodeliver;

import java.sql.Driver;

public class DriverDetails {
    public String empid;
    public String name;
    public String ordercount;
    public String phone;

    public DriverDetails(){

    }


    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(String ordercount) {
        this.ordercount = ordercount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
