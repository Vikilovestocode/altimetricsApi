package com.altimetrik.altimetrics.service;

import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TableRead {

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\Prakash\\alti-metrics\\src\\main\\resources\\template.pptx");
        FileInputStream inputstream = new FileInputStream(file);
        File outputFile = new File("template.pptx");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        XMLSlideShow ppt = new XMLSlideShow(inputstream);
        for(XSLFShape shape : ppt.getSlides().get(0)){
            //shape.getAnchor();
            System.out.println(shape.getShapeName());
            System.out.println(shape);
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
