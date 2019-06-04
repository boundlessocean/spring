package com.boundless.controller;

//import com.boundless.ExceptionHandle.UserNameNotMatchPasswordException;
import com.boundless.ExceptionHandle.myException;
import com.boundless.model.all;
import com.boundless.model.course;
import com.boundless.model.student;
import com.boundless.validation.studentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(value = "p")
@RequestMapping("job")
public class JobController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true);
//        DatePeopertyEditor editor = new DatePeopertyEditor(new SimpleDateFormat("yyyy-MM-dd"));
//        binder.registerCustomEditor(Date.class, editor);
//        if (binder.getObjectName().equals("student")){
//            binder.addValidators(new studentValidator());
//        }
    }


    @InitBinder("student")
    public void initBinderUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("student.");
    }

    @InitBinder("course")
    public void initBinderCourse(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("course.");
    }


    @PostMapping("object")
    @ResponseBody
    public all object(@Valid student stu,  BindingResult bindingResult, course cre, all all){
        System.out.println(all);

//        FormattingConversionServiceFactoryBean
//        ConversionServiceFactoryBean
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
        }

        return all;
    }

    @GetMapping("login")
    public void login() throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("error");
    }

//    DefaultHandlerExceptionResolver
    @GetMapping("compareName")
    public String compareName(String name) throws myException {
        if (name == null){
            myException exception = new myException();
            exception.setErrorMsg("name错误，请检查");
            throw exception;
        } else {
            throw new HttpMessageNotReadableException("dddddd");
        }
    }


    @GetMapping("cumlacuteNum")
    public String cumlacuteNum(int id) throws Exception {
        try {
            int i = 10/id;
        } catch (Exception ex){
            throw ex;
        }
        List<String> list = new ArrayList<>();
        list.add("1111");
        String a = list.get(1);
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
