package com.example.demo.word;/*
 * <p>项目名称: demo </p>
 * <p>包名称: com.example.demo.dto </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2019/11/27 </p>
 * <p>公司信息: 苏州鸿然信息科技有限公司</p>
 * @author <a href="mail to: 994662950@qq.com" rel="nofollow">ALEX</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;

public class WordData {

    @Name("tcf_function")
    private MiniTableRenderData tcfFunction;
    @Name("static_function")
    private MiniTableRenderData staticFunction;
    @Name("segment_result")
    private DocxRenderData segmentResult;
    @Name("segment_picture")
    private DocxRenderData segmentPicture;
    @Name("segment_problem")
    private DocxRenderData segmentProblem;


    public DocxRenderData getSegmentResult() {
        return segmentResult;
    }

    public MiniTableRenderData getTcfFunction() {
        return tcfFunction;
    }

    public void setTcfFunction(MiniTableRenderData tcfFunction) {
        this.tcfFunction = tcfFunction;
    }

    public MiniTableRenderData getStaticFunction() {
        return staticFunction;
    }

    public void setStaticFunction(MiniTableRenderData staticFunction) {
        this.staticFunction = staticFunction;
    }

    public void setSegmentResult(DocxRenderData segmentResult) {
        this.segmentResult = segmentResult;
    }

    public DocxRenderData getSegmentPicture() {
        return segmentPicture;
    }

    public void setSegmentPicture(DocxRenderData segmentPicture) {
        this.segmentPicture = segmentPicture;
    }

    public DocxRenderData getSegmentProblem() {
        return segmentProblem;
    }

    public void setSegmentProblem(DocxRenderData segmentProblem) {
        this.segmentProblem = segmentProblem;
    }
}

