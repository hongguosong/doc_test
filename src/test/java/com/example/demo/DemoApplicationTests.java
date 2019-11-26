package com.example.demo;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Value("${doc}")
    private String pathRoot;
    @Test
    void contextLoads() throws IOException, InvalidFormatException {
        XWPFDocument doc = new XWPFDocument(new FileInputStream(pathRoot+"/test.docx"));
        // 段落
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
// 表格
        List<XWPFTable> tables = doc.getTables();
// 图片
        List<XWPFPictureData> allPictures = doc.getAllPictures();
// 页眉
        List<XWPFHeader> headerList = doc.getHeaderList();
// 页脚
        List<XWPFFooter> footerList = doc.getFooterList();

        // 获取文字
        String text = paragraphs.get(0).getText();

// 获取段落内所有XWPFRun
        List<XWPFRun> runs = paragraphs.get(0).getRuns();

        XWPFParagraph p1 = doc.createParagraph();

        // 对齐方式
        p1.setAlignment(ParagraphAlignment.CENTER);
        // 边框
        p1.setBorderBottom(Borders.DOUBLE);
        p1.setBorderTop(Borders.DOUBLE);
        p1.setBorderRight(Borders.DOUBLE);
        p1.setBorderLeft(Borders.DOUBLE);
        p1.setBorderBetween(Borders.SINGLE);

        // 段落末尾创建XWPFRun
        XWPFRun run = p1.createRun();
        // 颜色
        run.setColor("00ff00");
        // 斜体
        run.setItalic(true);
        // 粗体
        run.setBold(true);
        // 字体
        run.setFontFamily("Courier");
        // 下划线
        run.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

        // setText默认为追加文本，参数0表示设置第0个位置的文本，覆盖上一次设置
        run.setText("为这个段落追加文本");
        run.setText("为这个段落修改文本", 0);
        //文本换行
        run.addCarriageReturn();


        // 段落起始插入XWPFRun
        XWPFRun insertNewRun = p1.insertNewRun(0);
        insertNewRun.setText("在段落起始位置插入这段文本");


        InputStream stream = new FileInputStream(pathRoot+"/sayi.png");
        XWPFParagraph p2 = doc.createParagraph();
        XWPFRun run2 = p2.createRun();
        run2.addPicture(stream, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(256), Units.toEMU(256));

        XWPFTable table = doc.createTable(3, 3);
        table.getRow(0).getCell(0).setText("EXAMPLE OF TABLE");

        XWPFParagraph tablep1 = table.getRow(0).getCell(1).addParagraph();
//        XWPFTableCell cell = table.getRow(0).getCell(1);
//        // 背景色
//        cell.setColor(cellStyle.getBackgroundColor());
//
//        // 获取单元格段落后设置对齐方式
//        XWPFParagraph addParagraph = cell.addParagraph();
//        addParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun tabler1 = tablep1.createRun();
        tabler1.setText("EXAMPLE OF TABLE HAHA");

        XWPFParagraph tablep2 = table.getRow(0).getCell(2).addParagraph();
        XWPFRun tabler2 = tablep2.createRun();
        InputStream stream2 = new FileInputStream(pathRoot+"/sayi.png");
        tabler2.addPicture(stream2, XWPFDocument.PICTURE_TYPE_PNG, "Generated", Units.toEMU(256), Units.toEMU(256));


        try (FileOutputStream out = new FileOutputStream(pathRoot+"/simple.docx")) {
            doc.write(out);
        }

    }
    void test() throws IOException{
        //核心API采用了极简设计，只需要一行代码
        XWPFTemplate.compile("template.docx").render(new HashMap<String, Object>(){{
            put("title", "Poi-tl 模板引擎");
        }}).writeToFile("out_template.docx");
    }
}
