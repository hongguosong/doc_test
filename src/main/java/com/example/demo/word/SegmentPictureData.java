package com.example.demo.word;/*
 * <p>项目名称: demo </p>
 * <p>包名称: com.example.demo.word </p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2019/11/28 </p>
 * <p>公司信息: 苏州鸿然信息科技有限公司</p>
 * @author <a href="mail to: 994662950@qq.com" rel="nofollow">ALEX</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

import com.deepoove.poi.data.PictureRenderData;

public class SegmentPictureData {

    private String function;
    private PictureRenderData testcase;
    private PictureRenderData statement;
    private PictureRenderData branch;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public PictureRenderData getTestcase() {
        return testcase;
    }

    public void setTestcase(PictureRenderData testcase) {
        this.testcase = testcase;
    }

    public PictureRenderData getStatement() {
        return statement;
    }

    public void setStatement(PictureRenderData statement) {
        this.statement = statement;
    }

    public PictureRenderData getBranch() {
        return branch;
    }

    public void setBranch(PictureRenderData branch) {
        this.branch = branch;
    }
}
