package com.example.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;

import com.example.demo.word.*;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.collections.ParagraphCollection;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TableOfContent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Value("${doc}")
    private String pathRoot;

    @Test
    void contextLoads() throws IOException, InvalidFormatException {
        XWPFDocument doc = new XWPFDocument(new FileInputStream(pathRoot+"/story.docx"));
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

    @Test
    void test3() throws IOException{
        //RowRenderData header = RowRenderData.build(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历"));

        RowRenderData header = new RowRenderData(
                Arrays.asList(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历")
                ),"ff9800");

        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "博士后");

        XWPFTemplate template = XWPFTemplate.compile(pathRoot+"/template.docx").render(new HashMap<String, Object>(){{
            put("table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
        }});
        FileOutputStream out = new FileOutputStream(pathRoot+"/out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

    @Test
    void test2() throws IOException, InvalidFormatException {

        //--------------所有文档正文都不能有首行缩进，不然表格就有缩进------------------------
        //-----------------------------------------------------------------------------
        //word复选框，有钩☑，无钩口, 方框字号18，打钩的12一样大
        //整个模板数据
        WordData data = new WordData();
        //动态分析结果
        List<SegmentResultData> segmentsResultDataList = new ArrayList<SegmentResultData>();
        SegmentResultData s1 = new SegmentResultData();
        s1.setFunction("ASP.READ");
        //s1.setFunction("☑口□□");
        s1.setTestcase("SDFTK_U1");
        s1.setInput("输入测试说明1");
        s1.setOutput("输出测试说明1");
        s1.setBranch("25%");
        s1.setStatement("56%");
        SegmentResultData s2 = new SegmentResultData();
        s2.setFunction("ASP.WRITE");
        s2.setTestcase("SDFTK_U2");
        s2.setInput("输入测试说明2");
        s2.setOutput("输出测试说明2");
        s2.setBranch("78%");
        s2.setStatement("90%");
        SegmentResultData s3 = new SegmentResultData();
        s3.setFunction("ASP.INIT");
        s3.setTestcase("SDFTK_U3");
        s3.setInput("输入测试说明3");
        s3.setOutput("输出测试说明3");
        s3.setBranch("98%");
        s3.setStatement("100%");
        segmentsResultDataList.add(s1);
        segmentsResultDataList.add(s2);
        segmentsResultDataList.add(s3);

        DocxRenderData segmentResult = new DocxRenderData(new File(pathRoot+"/segmentResult11.docx"), segmentsResultDataList );
        data.setSegmentResult(segmentResult);

        Style tableHeaderStyle = new Style();
        tableHeaderStyle.setBold(true);
        tableHeaderStyle.setFontSize(10);
        tableHeaderStyle.setColor("000000");

        Style tableRowStyle = new Style();
        tableRowStyle.setFontSize(10);
        tableRowStyle.setColor("000000");

        //tcf_function table
        TableData tcfFunctionData = new TableData();
        tcfFunctionData.setHeader(new RowRenderData(
                Arrays.asList(new TextRenderData("序号", tableHeaderStyle),
                        new TextRenderData("被测模块", tableHeaderStyle),
                        new TextRenderData( "测试用例编号", tableHeaderStyle),
                        new TextRenderData("语句覆盖率", tableHeaderStyle),
                        new TextRenderData( "分支覆盖率", tableHeaderStyle),
                        new TextRenderData( "测试结论", tableHeaderStyle),
                        new TextRenderData("缺陷分析", tableHeaderStyle)),
                "FFFFFF"));
        List<RowRenderData> tcfFunctionTableData = new ArrayList<>();
        for(int i=0; i<4;i++){
            Integer num = i+1;
            //RowRenderData row = RowRenderData.build(num.toString(), "ASP.READ", "XXXX", "100%", "100%", "正确", "无缺陷");
            RowRenderData row = new RowRenderData(
                    Arrays.asList(new TextRenderData(num.toString(), tableRowStyle),
                            new TextRenderData("ASP.READ", tableRowStyle),
                            new TextRenderData( "XXXX", tableRowStyle),
                            new TextRenderData("100%", tableRowStyle),
                            new TextRenderData( "100%", tableRowStyle),
                            new TextRenderData( "正确", tableRowStyle),
                            new TextRenderData("无缺陷", tableRowStyle)),
                    "FFFFFF");
            tcfFunctionTableData.add(row);
        }
        tcfFunctionData.setTableDatas(tcfFunctionTableData);
        MiniTableRenderData tcfTable = new MiniTableRenderData(tcfFunctionData.getHeader(), tcfFunctionData.getTableDatas(), 15.19F);
        TableStyle tcfTableStyle = new TableStyle();
        tcfTableStyle.setAlign(STJc.LEFT);
        tcfTable.setStyle(tcfTableStyle);
        data.setTcfFunction(tcfTable);

        //static_function table
        TableData staticFunctionData = new TableData();
        staticFunctionData.setHeader(new RowRenderData(
                Arrays.asList(new TextRenderData("序号", tableHeaderStyle),
                        new TextRenderData("被测模块", tableHeaderStyle),
                        new TextRenderData( "语句覆盖率", tableHeaderStyle),
                        new TextRenderData("分支覆盖率", tableHeaderStyle),
                        new TextRenderData("测试结论", tableHeaderStyle),
                        new TextRenderData("缺陷分析", tableHeaderStyle)),
                "FFFFFF"));
        List<RowRenderData> staticFunctionTableData = new ArrayList<>();
        for(int i=0; i<4;i++){
            Integer num = i+1;
            //RowRenderData row = RowRenderData.build(num.toString(), "ASP.READ", "XXXX", "100%", "100%", "正确", "无缺陷");
            RowRenderData row = new RowRenderData(
                    Arrays.asList(new TextRenderData(num.toString(), tableRowStyle),
                            new TextRenderData("ASP.READ", tableRowStyle),
                            new TextRenderData("100%", tableRowStyle),
                            new TextRenderData( "100%", tableRowStyle),
                            new TextRenderData( "正确", tableRowStyle),
                            new TextRenderData("无缺陷", tableRowStyle)),
                    "FFFFFF");
            staticFunctionTableData.add(row);
        }
        staticFunctionData.setTableDatas(staticFunctionTableData);
        data.setStaticFunction(new MiniTableRenderData(staticFunctionData.getHeader(), staticFunctionData.getTableDatas(), 15.19F));

        //截图文件
        List<SegmentPictureData> segmentPictureDataList = new ArrayList<>();
        for(int i=0; i<3; i++){
            SegmentPictureData s10 = new SegmentPictureData();
            if(i==0){
                s10.setFunction("ASP.READ");
            }else if(i==1){
                s10.setFunction("ASP.WRITE");
            }else if(i==2){
                s10.setFunction("ASP.INIT");
            }
            s10.setTestcase(new PictureRenderData(600,60,pathRoot+"/Case_ReadCom.png"));
            s10.setStatement(new PictureRenderData(600,75,pathRoot+"/Statement_ReadCom.png"));
            s10.setBranch(new PictureRenderData(600,75,pathRoot+"/Branch_ReadCom.png"));
            segmentPictureDataList.add(s10);
        }

        DocxRenderData segmentPicture = new DocxRenderData(new File(pathRoot+"/segmentPicture3.docx"), segmentPictureDataList );
        data.setSegmentPicture(segmentPicture);

        //问题单
        List<SegmentProblemData> segmentProblemDataList = new ArrayList<>();
        for(int i=0; i<3; i++){
            SegmentProblemData s20 = new SegmentProblemData();
            Style wugouStyle = new Style();
            wugouStyle.setFontSize(10);
            TextRenderData textRenderDataWuGou = new TextRenderData("□");
            textRenderDataWuGou.setStyle(wugouStyle);
            Style gouStyle = new Style();
            gouStyle.setFontSize(8);
            TextRenderData textRenderDataGou = new TextRenderData("☑");
            textRenderDataGou.setStyle(gouStyle);
            s20.setError1(textRenderDataGou);
            s20.setError2(textRenderDataWuGou);
            s20.setError3(textRenderDataWuGou);
            s20.setError4(textRenderDataWuGou);
            s20.setLevel1(textRenderDataWuGou);
            s20.setLevel2(textRenderDataWuGou);
            s20.setLevel3(textRenderDataWuGou);

            s20.setFunction("ASP.READ");
            s20.setRemark("问题单测试");

            ProblemPropertyData pp = new ProblemPropertyData();
            RowRenderData good = RowRenderData.build("4", "墙纸");
            pp.setProperties(Arrays.asList(good, good, good));
            s20.setProblemProperty(pp);
            segmentProblemDataList.add(s20);
        }
        DocxRenderData segmentProblem = new DocxRenderData(new File(pathRoot+"/segmentProblem.docx"), segmentProblemDataList);
        data.setSegmentProblem(segmentProblem);

        //渲染输出
        Configure config = Configure.newBuilder().customPolicy("problem_property", new ProblemTablePolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(pathRoot+"/muban4.docx", config).render(data);
        //XWPFTemplate template = XWPFTemplate.compile(pathRoot+"/segmentProblem.docx", config).render(segmentProblemDataList.get(0));
        XWPFDocument document = template.getXWPFDocument();
        generateTOC(document);

        FileOutputStream out = new FileOutputStream(pathRoot+"/out_story2.docx");
        document.write(out);
        out.flush();
        out.close();
        //template.close();
        document.close();

//        FileOutputStream out = new FileOutputStream(pathRoot+"/out_story2.docx");
//        template.write(out);
//        out.flush();
//        out.close();
//        template.close();
//        Document spireDocument = new Document(pathRoot+"/out_story2.docx");
//        //通过域代码添加目录表
//        TableOfContent toc = new TableOfContent(spireDocument, "{\\o \"1-3\" \\h \\z \\u}");
//        for(int i=0; i<spireDocument.getSections().getCount(); i++){
//            for(int j=0; j<spireDocument.getSections().get(i).getParagraphs().getCount(); j++){
//                Paragraph p = spireDocument.getSections().get(i).getParagraphs().get(j);
//               if(p.getText().contains("目录")){
//                   p.appendTOC(1,3);
//                    break;
//                }
//            }
//        }
//        //spireDocument.getSections().get(0).getParagraphs().get(0).appendTOC(1,3);
//        spireDocument.updateTableOfContents();
//
//        //保存文档
//        spireDocument.saveToFile(pathRoot+"/out_story2.docx", FileFormat.Docx_2010);
    }

    public void generateTOC(XWPFDocument document) throws InvalidFormatException, FileNotFoundException, IOException {
        String findText = "toc";
        String replaceText = "";
        for (XWPFParagraph p : document.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
                int pos = r.getTextPosition();
                String text = r.getText(pos);
                System.out.println(text);
                if (text != null && text.contains(findText)) {
                    text = text.replace(findText, replaceText);
                    r.setText(text, 0);
                    addField(p, "TOC \\o \"1-3\" \\h \\z \\u");
//                    addField(p, "TOC \\h");
                    break;
                }
            }
        }
    }

    private void addField(XWPFParagraph paragraph, String fieldName) {
        CTSimpleField ctSimpleField = paragraph.getCTP().addNewFldSimple();
        ctSimpleField.setInstr(fieldName);
        ctSimpleField.setDirty(STOnOff.TRUE);
        ctSimpleField.addNewR().addNewT().setStringValue("<<fieldName>>");
    }
}
