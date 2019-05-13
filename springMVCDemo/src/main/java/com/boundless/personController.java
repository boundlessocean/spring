package com.boundless;


import com.sun.net.httpserver.HttpServer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("person")
public class personController {

    @RequestMapping("name")
    public String getPersonName(){
        return "name";
    }

    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public String insertPerson(acount p){
        System.out.println(p);
        return "name";
    }

    @RequestMapping("name/{id}")
    public String getNameById(@PathVariable("id") int id){
        System.out.println(id);
        return "name";
    }

    @RequestMapping("requestParam")
    public String requestParam(@RequestParam(value="id",required = false)int ID){
        System.out.println(ID);
        return "name";
    }

    @RequestMapping(value = "responseBody",method = RequestMethod.POST)
    public String responseBody(@RequestBody String body){
        System.out.println(body);
        return "name";
    }

    @RequestMapping("requestHeader")
    public String requestHeader(@RequestHeader(value = "Accept-Language",required = false) String header,int id){
        System.out.println(header+" id = "+id);
        return "name";
    }


    @RequestMapping("cookieValue")
    public String cookieValue(@CookieValue(value = "JSESSIONID",required = false) String cookie){
        System.out.println(cookie);
        return "name";
    }


    @RequestMapping("modelAttribute")
    public String modelAttribute(person p){
        System.out.println("传入用户："+p);
        return "name";
    }

    @ModelAttribute
    public person testModelAttribute(){

        Address address = new Address();
        address.setArea("高新区");
        address.setStreet("190号");

        person p = new person();
        p.setAge(20);
        p.setName("张三");
        p.setAddress(address);
        System.out.println("数据库用户：" +p);
        return p;
    }


    @RequestMapping("success")
    public String success(){
        return "success";
    }



    @RequestMapping("testVoid")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.getRequestDispatcher("success").forward(request,response);
        response.sendRedirect("success");

    }

}
