package com.andrey.restapp.controller;

import com.andrey.restapp.model.File;
import com.andrey.restapp.service.FileServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/file")
@MultipartConfig(
        location = "C:/Users/Home/Desktop/letscode/RestApiApp/src/main/webapp/WEB-INF",
        fileSizeThreshold = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class FileServlet extends HttpServlet {

    private final FileServiceImpl fileService;

    public FileServlet() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String crudOperation = req.getParameter("crud");

        switch (crudOperation) {
            case "fileUpload":
                uploadFile(req, resp);
                break;
            case "edit":
                editFileName(req, resp);
                break;
            case "delete":
                deleteFile(req, resp);
                break;
            case "all":
                listFiles(req, resp);
                break;
            case "download":
                downloadFile(req, resp);
                break;
        }
    }

    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.parseLong(req.getParameter("idDelete"));
        File file = fileService.getById(id);
        String fileName = "";
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    private void editFileName(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName = "";

        try {
            Part part = request.getPart("fileUpload");
            fileName = getFileName(part);
            part.write(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File();
        file.setFileName(fileName);
        fileService.create(file);
        response.sendRedirect("/fileview.jsp");
    }


    private void listFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<File> listFile = fileService.getAll();
        request.setAttribute("listFile", listFile);


        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        for (int i = 0; i < listFile.size(); i++) {
            out.println("<h3> User: " + listFile.get(i) + "</h3>");
        }
        out.println("<a href='http://localhost:8080/fileview.jsp'> File page</a>");

    }

    private String getFileName(Part part) {
        String cd = part.getHeader("content-disposition");

        if (!cd.contains("filename=")) {
            return null;
        }
        int beginIndex = cd.indexOf("filename=") + 10;
        int endIndex = cd.length() - 1;
        return cd.substring(beginIndex, endIndex);
    }


}
