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

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;

public class ProblemTablePolicy extends DynamicTableRenderPolicy{

    //问题单动态属性填充行数
    int propertyStartRow = 12;

    @Override
    public void render(XWPFTable table, Object data) {
        if(null == data){
            return;
        }
        ProblemPropertyData propertyData = (ProblemPropertyData) data;
        List<RowRenderData> properties = propertyData.getProperties();
        table.removeRow(propertyStartRow);
        if(properties != null){
            for(int i=0; i<properties.size(); i++){
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(propertyStartRow);
                for (int j = 0; j < 7; j++) {
                    insertNewTableRow.createCell();
                }
                // 合并单元格
                TableTools.mergeCellsHorizonal(table, propertyStartRow, 1, 6);
                // 渲染问题单动态属性
                MiniTableRenderPolicy.Helper.renderRow(table, propertyStartRow, properties.get(i));
            }
        }
    }
}
