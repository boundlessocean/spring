package com.boundless;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller("personController")
@Controller
@RequestMapping("person")
public class personController {

    @RequestMapping("name")
    public String getPersonName(){
        return "name";
    }

    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public String insertPerson(person p){

        System.out.println(p);
        return "name";
    }
}
