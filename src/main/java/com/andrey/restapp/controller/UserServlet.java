package com.andrey.restapp.controller;

import com.andrey.restapp.model.User;
import com.andrey.restapp.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.lang.System.out;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserServiceImpl userService;

    public UserServlet() {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String crudOperation = req.getParameter("crud");

        switch (crudOperation) {
            case "create":
                insertUser(req, resp);
                break;
            case "edit":
                editUser(req, resp);
                break;
            case "delete":
                deleteUser(req, resp);
                break;
            case "all":
                listUsers(req, resp);
                break;
            case "byid":
                userById(req, resp);
                break;
        }
    }

    private void userById(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long id = Long.parseLong(req.getParameter("iduser"));
        User user = userService.getById(id);

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<h3> User: " + user + "</h3>");
        out.println("<a href='http://localhost:8080/userview.jsp'> User page</a>");
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.create(user);
        response.sendRedirect("/userview.jsp");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Long id = Long.parseLong(request.getParameter("idDelete"));
        userService.delete(id);
        response.sendRedirect("/userview.jsp");
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Long id = Long.parseLong(request.getParameter("idEdit"));
        String firstName = request.getParameter("firstNameEdit");
        String lastName = request.getParameter("lastNameEdit");
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.update(user);
        response.sendRedirect("/userview.jsp");
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> listUser = userService.getAll();
        request.setAttribute("listUser", listUser);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        for (int i = 0; i < listUser.size(); i++) {
            out.println("<h3> User: " + listUser.get(i) + "</h3>");
        }
        out.println("<a href='http://localhost:8080/userview.jsp'> User page</a>");
    }
}
