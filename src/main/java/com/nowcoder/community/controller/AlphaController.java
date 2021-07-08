package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
//        获取请求头数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
//        获取请求体数据
        System.out.println(request.getParameter("code"));

//        返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try(PrintWriter writer = response.getWriter()//输出流
        ) {
            writer.write("<h1>output</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    GET请求

//    /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "No." + current + " students and " + limit + " students in one page";
    }

//        /students/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(
            @PathVariable("id") int id
    ) {
        return "a Student whose id is " + id;
    }

//    Post请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        return "name: " + name + " age: " + age;
    }
//    响应HTML数据

    @RequestMapping(path = "teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "Archer");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path = "school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "School");
        model.addAttribute("age", 100);
        return "/demo/view"; //return view root
    }

//    响应Json数据（异步请求：把请求交给代理对象，实现页面数据的局部刷新）
//    Java对象-> Json-> JS对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody //返回字符串，否则返回HTML
    public Map<String, Object> getemp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Archer");
        emp.put("salary", 8000);
        emp.put("age", 23);
        return emp;
    }
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody //返回字符串，否则返回HTML
    public List<Map<String, Object>> getemps(){
        List<Map<String, Object>> emps = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "Archer");
        emp.put("salary", 8000);
        emp.put("age", 23);
        emps.add(emp);
        emps.add(emp);
        emps.add(emp);
        return emps;
    }

}
