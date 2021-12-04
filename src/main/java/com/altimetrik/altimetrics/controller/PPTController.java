package com.altimetrik.altimetrics.controller;

import com.altimetrik.altimetrics.pojo.Project;
import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.service.ContentService;
import com.altimetrik.altimetrics.service.PptGenerator;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/ppt")
public class PPTController {

    @Autowired
    PptGenerator pptGenerator;

    @Autowired
    ContentService contentService;

    @PostMapping("/download")
    public void downloadPpt(HttpServletResponse response, List<ProjectGroup> projectGroups) throws IOException {
        File outputFile = pptGenerator.buildContent(projectGroups);
        InputStream outputStream = new FileInputStream(outputFile);
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        );
        response.setHeader(
                "Content-Disposition",
                String.format("inline; filename=\"%s\"", outputFile.getName())
        );
        response.setContentLength((int) outputFile.length());
        IOUtils.copy(outputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @PostMapping
    public List<ProjectGroup> generatePPtcontent(@RequestBody List<ProjectGroup> projectGroups) {
      return contentService.generatePPTContent(projectGroups);

    }
}
