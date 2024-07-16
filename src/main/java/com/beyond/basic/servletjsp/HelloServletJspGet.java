package com.beyond.basic.servletjsp;


import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello/servlet/jsp/get")
public class HelloServletJspGet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        src/main/webapp폴더를 찾아가는것으로 약속
        req.setAttribute("myData", "hello world java");
        req.getRequestDispatcher("/WEB-INF/views/hello.jsp").forward(req, resp);

    }
}
