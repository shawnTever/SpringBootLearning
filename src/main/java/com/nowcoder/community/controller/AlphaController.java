package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

//    cookie示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
//        创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
//        设置cookie生效范围
        cookie.setPath("/community/alpha");
//        设置cookie生存时间
        cookie.setMaxAge(60 * 10); // 60s * 10
//        发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
//        key = code
        System.out.println(code);
        return "get cookie";
    }

//    session示例
//    分布式应用session用的比较少，如果session不在一个服务器查不到，可用黏性session，同步session或者共享session处理
//    黏性session：同一ip分给同一服务器
//    同步session：将所有服务器同步给所有服务器
//    共享session：同一交给某一个服务器处理（如果依赖服务器出问题影响整个程序）
//    目前主流方法将敏感数据存入数据库（relational database将数据存入硬盘，读取较慢，用nosql（redis）技术）
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

}
