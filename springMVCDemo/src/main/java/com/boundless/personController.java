package com.boundless;


import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.net.httpserver.HttpServer;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.naming.Name;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("person")
public class personController implements Serializable {

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String getPersonName(){
        return "name";
    }

    @PostMapping("insert")
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

    @RequestMapping(value = "requestBody",method = RequestMethod.POST)
    public String responseBody(@RequestBody person body){
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

    @ModelAttribute()
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


    @RequestMapping("test")
    public String test(RedirectAttributes attr){
        Address address = new Address();
        address.setArea("高新区1");
        address.setStreet("190号1");

        person p = new person();
        p.setAge(201);
        p.setName("张三1");
        p.setAddress(address);
//        attr.addAttribute("name","xialu");         //这里传入的参数会出现在重定向后的url中，相当于get方式。
        attr.addFlashAttribute("person",p);    //这里传入的参数会用flashmap保存
        return "redirect:modelAttribute";
    }
//    @ModelAttribute()
//    public void testModelAttribute(){
//
//        Address address = new Address();
//        address.setArea("高新区");
//        address.setStreet("190号");
//
//        person p = new person();
//        p.setAge(20);
//        p.setName("张三");
//        p.setAddress(address);
////        System.out.println("数据库用户：" +p);
//
////        person.setName(p.getName());
////        person.setAge(p.getAge());
////        person.setAddress(p.getAddress());
//
//    }

    @RequestMapping("success")
    public String success(){
        return "success";
    }



    @RequestMapping("testVoid")
    public void testVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        request.getRequestDispatcher("success").forward(request,response);
        response.sendRedirect("success");
    }


    @RequestMapping("forword")
    public String forword(){
//        return "success";
        return "redirect:testVoid";
    }



    @RequestMapping("info")
    public void info(int id,HttpServletResponse response) throws IOException {
        person p = findPersonByID(id);
        String json = JSONObject.toJSONString(p);
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping("jsonInfo")
    @ResponseBody
    public person jsonInfo(int id) throws IOException {
        person p = findPersonByID(id);
        return p;
    }


    @RequestMapping("jsonBody")
    @ResponseBody
    public person jsonBody(@RequestBody person p,Integer id) throws IOException {
        person u = findPersonByID(id);
        u.setName(p.getName());
        return u;
    }


    @RequestMapping("fileupload")
    public String fileupload(String imageName, MultipartFile uploadFile,HttpServletRequest request) throws IOException {

        System.out.println(imageName);
        System.out.println(uploadFile);

        String basePath = request.getServletContext().getRealPath("WEB-INF/upload/");
        String dataPath = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File file = new File(basePath,dataPath);
        if (!file.exists()){
            file.mkdirs();
        }

//        DispatcherServlet

        String fileName = uploadFile.getOriginalFilename();
        fileName = UUID.randomUUID().toString().replace("-","").toUpperCase() + fileName;

//        String serverImagePath = "http://localhost:9091/upload/";
//        Client client = Client.create();
//        WebResource resource = client.resource(serverImagePath+fileName);
//        String result = resource.put(String.class,uploadFile.getBytes());

//        System.out.println(result);
        uploadFile.transferTo(new File(file,fileName));
//        ResourceHttpRequestHandler
//        DispatcherServlet
//        HttpMessageConverter
//        MultipartResolver
//        HandlerExceptionResolver
//        View
//        HandlerAdapter
//        HandlerMapping
//        ViewResolver
//        WebApplicationContext
//        HttpRequestHandler
        return "success";
    }




    private person findPersonByID(int id){
        Address address = new Address();
        address.setArea("高新区");
        address.setStreet("190号");

        person p = new person();
        p.setAge(20);
        p.setName("张三");
        p.setAddress(address);
        return p;
    }
}
