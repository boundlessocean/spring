package com.boundless.controller;

import com.boundless.ExceptionHandle.myException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Name;

@Controller
@SessionAttributes(value = "p")
@RequestMapping("job")
public class JobController {


    @GetMapping("compareName")
    public String compareName(String name) throws myException {
        if (name == null){
            myException exception = new myException();
            exception.setErrorMsg("name错误，请检查");
            throw exception;
        }
        return "success";
    }


    @GetMapping("cumlacuteNum")
    public String cumlacuteNum(int id) throws Exception {
        try {
            int i = 10/id;
        } catch (Exception ex){
            throw ex;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"错误，错误");
//        return "success";
    }

    @GetMapping("idTest")
    public String idTest(int id) {

        return "success";
    }

    @GetMapping("applyJob")
    public String apply(@ModelAttribute("p") person p,
                        @RequestAttribute("name") String name) {
        System.out.println("找工作的人 ="+"person = " +p.toString()+" name = "+name);

        return "success";
    }


    @GetMapping("userName")
    public  String  user(Model model){
        Address address = new Address();
        address.setArea("高新区");
        address.setStreet("1901号");

        person p = new person();
        p.setAge(10);
        p.setName("张三");
        p.setAddress(address);
        model.addAttribute("p",p);
        return "success";
    }

}
