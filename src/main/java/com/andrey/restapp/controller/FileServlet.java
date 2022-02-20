package com.andrey.restapp.controller;

import com.andrey.restapp.model.Event;
import com.andrey.restapp.model.File;
import com.andrey.restapp.service.EventService;
import com.andrey.restapp.service.EventServiceImpl;
import com.andrey.restapp.service.FileServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@WebServlet("/file")
@MultipartConfig(
        location = "D:/",
        fileSizeThreshold = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class FileServlet extends HttpServlet {

    private final FileServiceImpl fileService;
    private final EventService eventService = new EventServiceImpl();

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

    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("idDelete"));
        File file = fileService.getById(id);
        String fileName = file.getFileName();

        Path path = Paths.get("D:/" + fileName);
        fileService.delete(id);
        Files.delete(path);
        resp.sendRedirect("/fileview.jsp");
    }

    private void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // add user id when downloading

        Long id = Long.parseLong(request.getParameter("idDownload"));
        File file = fileService.getById(id);
        String fileName = file.getFileName();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + fileName);

        java.io.File downloadFile = new java.io.File("D:/" + fileName);
        FileInputStream fileIn = new FileInputStream(downloadFile);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[4096];
        while (fileIn.read(outputByte, 0, 4096) != -1) {
            out.write(outputByte, 0, 4096);
        }

        fileIn.close();
        out.flush();
        out.close();
    }

    private void editFileName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("idEdit"));
        String updName = req.getParameter("fileNameEdit");
        File origFile = fileService.getById(id);
        String origName = origFile.getFileName();
        File file = new File();
        file.setId(id);
        file.setFileName(updName);
        fileService.update(file);
        Path path = Paths.get("D:/" + origName);
        Files.move(path, path.resolveSibling(updName), StandardCopyOption.REPLACE_EXISTING);
        resp.sendRedirect("/fileview.jsp");
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
        Long userId = Long.parseLong(request.getParameter("userIdUpload"));
        Event event = new Event();
        event.setDate(new Date());
        eventService.create(event);
        ///
        ///
        ///

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
