package com.altimetrik.altimetrics.service;


import com.altimetrik.altimetrics.pojo.Project;
import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.pojo.Story;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.util.List;

@Service
public class PptGenerator {

    @Autowired
    RallyService rallyService;

//    public File buildObjectsForPPT(String iterationId) throws IOException {
//        return buildContent();
//    }

    public File buildContent(List<ProjectGroup> projects) throws IOException {
        InputStream inputStream = getinputTemplateStream();
        XMLSlideShow ppt = new XMLSlideShow(inputStream);
        File outputFile = buildOutputFile();
        OutputStream outputStream = buildOutputFileStream(outputFile);
        // geting story details by iterationId
        List<Story> storiesBySprintId = rallyService.getStoriesBySprintId("616929938915");
        XSLFSlideLayout slideLayout = ppt.getSlides().get(0).getSlideLayout();
        /// adding slides based on projects size
        projects.forEach(p->{
            XSLFSlide newSlide = ppt.createSlide(slideLayout);
            newSlide.importContent(ppt.getSlides().get(0));
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                for(XSLFShape shape : ppt.getSlides().get(i)){
                    setTitle(shape);
                    setOverallSatus(shape);
                    setProjectTableDetails(shape,storiesBySprintId);
                }
            }


        });
        ppt.write(outputStream);
        outputStream.close();
        inputStream.close();
        return outputFile;
    }

    public void setTitle(XSLFShape shape){
        if(shape instanceof XSLFAutoShape){

            XSLFAutoShape t = (XSLFAutoShape) shape;
            if(t.getShapeName().equals("Title 3")){
                t.clearText();
                t.setText("DNA");
            }
        }
    }

    public void setOverallSatus(XSLFShape shape){
        /// Overall Status
        if(shape instanceof XSLFTextBox){

            XSLFTextBox t = (XSLFTextBox) shape;
            if(t.getShapeName().equals("Title 1")){
                t.clearText();
                XSLFTextRun xslfTextRun = t.addNewTextParagraph().addNewTextRun();
                xslfTextRun.setText("Overall status: G");
                xslfTextRun.setFontColor(Color.BLACK);
                xslfTextRun.setFontSize(24.);
                xslfTextRun.setFontFamily("Calibri");
            }
        }
    }

    public void setProjectDetails(XSLFTable xslfTable){
        // First Table with Project details
        if(xslfTable.getShapeName().equals("Table 12")){
            for (XSLFTableRow row : xslfTable.getRows()) {
                int firstCell=0;
                for (XSLFTableCell cell : row.getCells()) {
                    if(firstCell==0){
                        firstCell++;
                        continue;
                    }
                    cell.clearText();
                    XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                    xslfTextRun.setText("project details text");
                    xslfTextRun.setFontColor(Color.BLACK);
                    xslfTextRun.setFontSize(12.);
                    xslfTextRun.setFontFamily("Calibri");

                }
            }
        }
    }

    public void setProjectManagementDetails(XSLFTable xslfTable){
        // Project management details
        if(xslfTable.getShapeName().equals("Table 19")){
            for (XSLFTableRow row : xslfTable.getRows()) {
                int firstCell=0;
                for (XSLFTableCell cell : row.getCells()) {
                    if(firstCell==0){
                        firstCell++;
                        continue;
                    }
                    cell.clearText();
                    XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                    xslfTextRun.setText("project management details");
                    xslfTextRun.setFontColor(Color.BLACK);
                    xslfTextRun.setFontSize(12.);
                    xslfTextRun.setFontFamily("Calibri");

                }
            }
        }
    }

    private String buildBulletStories(List<Story> stories){
        StringBuilder stringBuilder = new StringBuilder();
        stories.forEach(s->stringBuilder.append(s.getName()+"\r"));
        return stringBuilder.toString();
    }

    public void setStoriesDetails(XSLFTable xslfTable,List<Story> stories){
        String storiesList = buildBulletStories(stories);
        // Project content
        if(xslfTable.getShapeName().equals("Table 22")){

            for (XSLFTableRow row : xslfTable.getRows()) {
                int cellNum =0;
                //System.out.println("table 22 rows size "+ row.getCells().size());
                // row.addCell();

                //System.out.println("table 22 rows size "+ row.getCells().size());
                for (XSLFTableCell cell : row.getCells()) {
                   // int columnWidth = ppt.getPageSize().width/ row.getCells().size();// needs to fixed later
                    cell.clearText();
                    // cell.setBorderColor(TableCell.BorderEdge.bottom,Color.BLACK);
                    //cell.setBorderColor(TableCell.BorderEdge.top,Color.BLACK);
                    //cell.setBorderColor(TableCell.BorderEdge.right,Color.BLACK);
                    //cell.setBorderColor(TableCell.BorderEdge.bottom,Color.BLACK);
                   // t.setColumnWidth(cellNum,columnWidth);
                    cellNum++;
                    // Team Name
                    XSLFTextParagraph teamName = cell.addNewTextParagraph();
                    XSLFTextRun xslfTextRun1 = teamName.addNewTextRun();
                    xslfTextRun1.setText("Team Name :");
                    xslfTextRun1.setBold(true);
                    xslfTextRun1.setFontColor(Color.BLACK);
                    xslfTextRun1.setFontSize(12.);
                    xslfTextRun1.setFontFamily("Calibri");

                    // project  Name
                    XSLFTextParagraph projectname = cell.addNewTextParagraph();
                    XSLFTextRun xslfTextRun2 = projectname.addNewTextRun();
                    xslfTextRun2.setText("Project Name :");
                    xslfTextRun2.setBold(true);
                    xslfTextRun2.setFontColor(Color.BLACK);
                    xslfTextRun2.setFontSize(12.);
                    xslfTextRun2.setFontFamily("Calibri");

                    XSLFTextParagraph xslfTextParagraph = cell.addNewTextParagraph();
                    xslfTextParagraph.setBullet(true);
                    xslfTextParagraph.setIndent(0.);  //bullet offset
                    xslfTextParagraph.setLeftMargin(10.);   //text offset (should be greater than bullet offset)
                    XSLFTextRun xslfTextRun = xslfTextParagraph.addNewTextRun();
                    xslfTextRun.setText(storiesList);
                    xslfTextRun.setFontColor(Color.BLACK);
                    xslfTextRun.setFontSize(12.);
                    xslfTextRun.setFontFamily("Calibri");

                }
            }
        }
    }

    public  void setProjectMileStoneDetails(XSLFTable xslfTable){
        // Project milestones details
        if(xslfTable.getShapeName().equals("Table 8")){
            int rowNum =0;

            for (XSLFTableRow row : xslfTable.getRows()) {
                if (rowNum == 0) {
                    rowNum++;
                    continue;
                }
                for (XSLFTableCell cell : row.getCells()) {
                    cell.clearText();
                    XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                    xslfTextRun.setText("testing text");
                    xslfTextRun.setFontColor(Color.BLACK);
                    xslfTextRun.setFontSize(12.);
                    xslfTextRun.setFontFamily("Calibri");

                }
                rowNum++;
            }


        }
    }

    public void setProjectTableDetails(XSLFShape shape,List<Story> stories){

        if (shape instanceof XSLFTable){
            XSLFTable t = (XSLFTable) shape;
            setProjectDetails(t);
            setProjectManagementDetails(t);
            setStoriesDetails(t,stories);
            setProjectMileStoneDetails(t);

        }
    }

    public InputStream getinputTemplateStream() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:template.pptx");
        FileInputStream inputstream = new FileInputStream(file);
        return inputstream;
    }

    public OutputStream buildOutputFileStream (File outputFile) throws FileNotFoundException {

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        return outputStream;
    }

    public File buildOutputFile(){
        File outputFile = new File("template.pptx");
        return outputFile;
    }
    private void generatePpt() throws IOException {
        File file = ResourceUtils.getFile("classpath:template.pptx");
        FileInputStream inputstream = new FileInputStream(file);
        File outputFile = new File("template.pptx");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        XMLSlideShow ppt = new XMLSlideShow(inputstream);
        for(XSLFShape shape : ppt.getSlides().get(0)){
            //shape.getAnchor();
//            System.out.println(shape.getShapeName());
//            System.out.println(shape);
            // System.out.println(shape.getXmlObject());
//            Title replacement
            if(shape instanceof XSLFAutoShape){

                XSLFAutoShape t = (XSLFAutoShape) shape;
                if(t.getShapeName().equals("Title 3")){
                    t.clearText();
                    t.setText("DNA");
                }
            }
            /// Overall Status
            if(shape instanceof XSLFTextBox){

                XSLFTextBox t = (XSLFTextBox) shape;
                if(t.getShapeName().equals("Title 1")){
                    t.clearText();
                    XSLFTextRun xslfTextRun = t.addNewTextParagraph().addNewTextRun();
                    xslfTextRun.setText("Overall status: G");
                    xslfTextRun.setFontColor(Color.BLACK);
                    xslfTextRun.setFontSize(24.);
                    xslfTextRun.setFontFamily("Calibri");
                }
            }

            if (shape instanceof XSLFTable){
                XSLFTable t = (XSLFTable) shape;
                // First Table with Project details
                if(t.getShapeName().equals("Table 12")){
                    for (XSLFTableRow row : t.getRows()) {
                        int firstCell=0;
                        for (XSLFTableCell cell : row.getCells()) {
                            if(firstCell==0){
                                firstCell++;
                                continue;
                            }
                            cell.clearText();
                            XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                            xslfTextRun.setText("project details text");
                            xslfTextRun.setFontColor(Color.BLACK);
                            xslfTextRun.setFontSize(12.);
                            xslfTextRun.setFontFamily("Calibri");

                        }
                    }
                }
                // Project management details
                if(t.getShapeName().equals("Table 19")){
                    for (XSLFTableRow row : t.getRows()) {
                        int firstCell=0;
                        for (XSLFTableCell cell : row.getCells()) {
                            if(firstCell==0){
                                firstCell++;
                                continue;
                            }
                            cell.clearText();
                            XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                            xslfTextRun.setText("project management details");
                            xslfTextRun.setFontColor(Color.BLACK);
                            xslfTextRun.setFontSize(12.);
                            xslfTextRun.setFontFamily("Calibri");

                        }
                    }
                }

                // Project content
                if(t.getShapeName().equals("Table 22")){

                    for (XSLFTableRow row : t.getRows()) {
                        int cellNum =0;
                        //System.out.println("table 22 rows size "+ row.getCells().size());
                        // row.addCell();

                        //System.out.println("table 22 rows size "+ row.getCells().size());
                        for (XSLFTableCell cell : row.getCells()) {
                            int columnWidth = ppt.getPageSize().width/ row.getCells().size();
                            cell.clearText();
                            // cell.setBorderColor(TableCell.BorderEdge.bottom,Color.BLACK);
                            //cell.setBorderColor(TableCell.BorderEdge.top,Color.BLACK);
                            //cell.setBorderColor(TableCell.BorderEdge.right,Color.BLACK);
                            //cell.setBorderColor(TableCell.BorderEdge.bottom,Color.BLACK);
                            t.setColumnWidth(cellNum,columnWidth);
                            cellNum++;
                            // Team Name
                            XSLFTextParagraph teamName = cell.addNewTextParagraph();
                            XSLFTextRun xslfTextRun1 = teamName.addNewTextRun();
                            xslfTextRun1.setText("Team Name :");
                            xslfTextRun1.setBold(true);
                            xslfTextRun1.setFontColor(Color.BLACK);
                            xslfTextRun1.setFontSize(12.);
                            xslfTextRun1.setFontFamily("Calibri");

                            // project  Name
                            XSLFTextParagraph projectname = cell.addNewTextParagraph();
                            XSLFTextRun xslfTextRun2 = projectname.addNewTextRun();
                            xslfTextRun2.setText("Project Name :");
                            xslfTextRun2.setBold(true);
                            xslfTextRun2.setFontColor(Color.BLACK);
                            xslfTextRun2.setFontSize(12.);
                            xslfTextRun2.setFontFamily("Calibri");

                            XSLFTextParagraph xslfTextParagraph = cell.addNewTextParagraph();
                            xslfTextParagraph.setBullet(true);
                            xslfTextParagraph.setIndent(0.);  //bullet offset
                            xslfTextParagraph.setLeftMargin(10.);   //text offset (should be greater than bullet offset)
                            XSLFTextRun xslfTextRun = xslfTextParagraph.addNewTextRun();
                            xslfTextRun.setText("Spanish language development – Planning for L1 canary\r" +
                                    "Spanish language development – Planning for L1 canary\r" +
                                    "Spanish language development – Planning for L1 canary\r" +
                                    "Spanish language development – Planning for L1 canary");
                            xslfTextRun.setFontColor(Color.BLACK);
                            xslfTextRun.setFontSize(12.);
                            xslfTextRun.setFontFamily("Calibri");

                        }
                    }
                }

                // Project milestones details
                if(t.getShapeName().equals("Table 8")){
                    int rowNum =0;

                    for (XSLFTableRow row : t.getRows()) {
                        if (rowNum == 0) {
                            rowNum++;
                            continue;
                        }
                        for (XSLFTableCell cell : row.getCells()) {
                            cell.clearText();
                            XSLFTextRun xslfTextRun = cell.addNewTextParagraph().addNewTextRun();
                            xslfTextRun.setText("testing text");
                            xslfTextRun.setFontColor(Color.BLACK);
                            xslfTextRun.setFontSize(12.);
                            xslfTextRun.setFontFamily("Calibri");

                        }
                        rowNum++;
                    }


                }
            }
        }
        ppt.write(outputStream);
        outputStream.close();
        inputstream.close();
    }

}
