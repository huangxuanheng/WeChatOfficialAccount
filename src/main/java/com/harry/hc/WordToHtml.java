package com.harry.hc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordToHtml {
    public static void main(String[] args) {
        String wordPath = "C:\\Users\\dragonpass\\Documents\\WeChat Files\\huangxuanheng\\FileStorage\\File\\2025-03\\mls.docx";
/*        try (FileInputStream fis = new FileInputStream(wordPath);
            XWPFDocument document = new XWPFDocument(fis)) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String content = extractor.getText();
            System.out.println(content);

            List<XWPFPictureData> pictures = document.getAllPictures();
            for (int i = 0; i < pictures.size(); i++) {
                XWPFPictureData pic = pictures.get(i);
                String fileName = "image_" + i + "." + picExtension(pic.suggestFileExtension());
//                FileOutputStream out = new FileOutputStream(fileName);
//                out.write(pic.getData());
//                out.close();
                System.out.println("图片已保存为：" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String htmlContent = convertWordToHtml(wordPath);
        String html="<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
            "    <title>Word to HTML</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            line-height: 1.6;\n" +
            "            margin: 20px;\n" +
            "            padding: 0;\n" +
            "            background-color: #f9f9f9;\n" +
            "        }\n" +
            "        p {\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        img {\n" +
            "            max-width: 100%;\n" +
            "            height: auto;\n" +
            "            object-fit: contain;\n" +
            "        }\n" +
            "        table {\n" +
            "            width: 100%;\n" +
            "            border-collapse: collapse;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        th, td {\n" +
            "            border: 1px solid #ddd;\n" +
            "            padding: 8px;\n" +
            "            text-align: left;\n" +
            "            word-wrap: break-word;\n" +
            "            overflow-wrap: break-word;\n" +
            "        }\n" +
            "        div.table-container {\n" +
            "            overflow-x: auto;\n" +
            "        }\n" +
            "        a {\n" +
            "            color: #007BFF;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "        a:hover {\n" +
            "            text-decoration: underline;\n" +
            "        }\n" +
            "        @media (max-width: 768px) {\n" +
            "            body {\n" +
            "                font-size: 14px;\n" +
            "            }\n" +
            "            table {\n" +
            "                font-size: 12px;\n" +
            "            }\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n"
            + "    ${content}\n"
            + "</body>\n"
            + "</html>";
        String htmlTemplate = html.replace("${content}",htmlContent);
        try (PrintWriter writer = new PrintWriter("output.html", "UTF-8")) {
            writer.print(htmlTemplate);
            System.out.println("HTML 文件已生成，路径为 output.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertWordToHtml(String wordPath) {
        try (FileInputStream fis = new FileInputStream(wordPath)) {
            XWPFDocument document = new XWPFDocument(fis);
            StringBuilder html = new StringBuilder();

            // 遍历文档的主体元素
            for (IBodyElement element : document.getBodyElements()) {
                // 根据元素类型进行处理
                switch (element.getElementType()) {
                    case PARAGRAPH:
                        html.append(processParagraph((XWPFParagraph) element,document,false));
                        break;
                    case TABLE:
                        html.append(processTable((XWPFTable) element,document));
                        break;
                    default:
                        // 忽略或处理其他类型的元素
                }
            }

            return html.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String processTable(XWPFTable table, XWPFDocument document) {
        // 处理表格内容
        StringBuilder tableContent = new StringBuilder();
        tableContent.append("<table style=\"border-collapse: collapse; width: 100%;\">\n");
        for (XWPFTableRow row : table.getRows()) {
            tableContent.append("  <tr>\n");
            for (XWPFTableCell cell : row.getTableCells()) {
                tableContent.append("    <td style=\"border: 1px solid #000; padding: 8px;\">");
                tableContent.append(cell.getText());
                tableContent.append("</td>\n");
            }
            tableContent.append("  </tr>\n");
        }
        tableContent.append("</table>\n");
        return tableContent.toString();
    }

    private static String processParagraph(XWPFParagraph paragraph, XWPFDocument document,boolean isTable) {
        StringBuilder html = new StringBuilder();
        html.append("<p ");
        if(!isTable){
            String alignment = paragraph.getAlignment().name();
            html.append("style=\"text-align: "+alignment+";margin-bottom: 20px\"");
        }
        html.append(">\n");
        for (XWPFRun run : paragraph.getRuns()) {
            if (run instanceof XWPFHyperlinkRun) {
                XWPFHyperlinkRun hyperlinkRun = (XWPFHyperlinkRun) run;
                String url = hyperlinkRun.getHyperlink(document).getURL();
                String text = hyperlinkRun.getText(0);
                String color = hyperlinkRun.getColor();
                int fontSize = hyperlinkRun.getFontSize();
                // 生成 HTML 超链接
                html.append(String.format(
                    "<a href=\"%s\" target=\"_blank\" style=\"color: %s; font-size: %dpx;\">%s</a>\n",
                    url, color, fontSize, text
                ));
            }else if (run.isBold()) {
                html.append("<strong>");
                html.append(getText(run));
                html.append("</strong>\n");
            } else if (run.isItalic()) {
                html.append("<em>");
                html.append(getText(run));
                html.append("</em>\n");
            } else {
                html.append(getText(run));
            }

            // 检查是否有嵌入图片
            for (XWPFPicture picture : run.getEmbeddedPictures()) {
                String imgPath = savePictureToDisk(picture.getPictureData());
                html.append("<img src='" + imgPath + "' alt='Word 文档图片'/>\n");
            }

        }
        html.append("</p>\n");
        return html.toString();
    }

    private static String getText(XWPFRun run) {
        String text = run.getText(0);
        if(text == null){
            return "";
        }
        if (text != null && !text.isEmpty()) {
            // 提取字体颜色
            String color = run.getColor();
            // 提取字体大小
            int fontSize = run.getFontSize();
            // 生成包含样式的HTML
            return String.format("<span style=\"color:#%s;font-size:%dpx;\">%s</span>\n",color,fontSize, text);
        }
        return text;
    }

    private static String savePictureToDisk(XWPFPictureData pictureData) {
        //TODO 替换未本地路径
        String imgPath = "C:\\Users\\dragonpass\\Documents\\WeChat Files\\huangxuanheng\\FileStorage\\File\\2025-03\\images\\" + pictureData.getFileName();
        File file=new File(imgPath);
        if(file.exists()){
            return imgPath;
        }
        try (FileOutputStream fos = new FileOutputStream(imgPath)) {
            fos.write(pictureData.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgPath;
    }

    private static String picExtension(String mimeType) {
        if ("image/jpeg".contains(mimeType)||"image/JPEG".contains(mimeType)) {
            return "jpg";
        } else if ("image/png".contains(mimeType)||"image/PNG".contains(mimeType)) {
            return "png";
        }else if ("image/GIF".contains(mimeType)||"image/gif".contains(mimeType)) {
            return "gif";
        }
        return "unknown";
    }
}