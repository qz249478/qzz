package com.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author quanzhengzheng
 * 读取Excel(支持2017、2010)生成sql语句
 */
public final class ReadExcelToSql {
    /**
     * 日志输出。
     */
    private static Logger logger = Logger.getLogger(ReadExcelToSql.class);
    /**
     * 需要转换的Excel。
     */
    private static final  String XLS_FILE = "f:\\DATA-8662.xls";
    /**
     * 生成的sql文件
     */
    private static final  String SQL_FILE = "f:\\DATA-8662-DML-04.sql";
    /**
     *
     */
    private File fileResulet = new File(SQL_FILE);
    /**
     * 要生成sql文件的类型
     *  1：生成插入短信sql（sms_main）
     *  2：生成查询险种配置sql(lp_config_new)
     *  其他：生成插入险种配置sql(lp_config_new)
     */
    private String sqlType;
    /**
     * 最终生成的sql串
     */
    private StringBuffer content = new StringBuffer();


    /**
     *
     * @param sqlType 需要生成的sql类型
     */
    private ReadExcelToSql(final String sqlType) {
        this.sqlType = sqlType;
    }

    /**
     *  主程序运行入口
     */
    public void run() {
        logger.info("ReadExcelToSql run() start ...");

        HSSFSheet sheet ;
        try {
            sheet = checkFile();
            int rowAll = 0;
            if (sheet != null) {
                //获取行数
                rowAll = sheet.getLastRowNum();
            }
            //查询险种配置
            String createSmsType = "0";
            String selectRiskConfig = "1";
            if (createSmsType.equals(sqlType)) {
                createSms(sheet, rowAll);
            } else if (selectRiskConfig.equals(sqlType)) {
                selectRiskConfig(sheet, rowAll);
            } else if ("4".equals(sqlType)) {
                createSel(sheet, rowAll);
            } else {
                createRiskConfig(sheet, rowAll);
            }

        } catch (Exception e) {
            logger.info("ReadExcelToSql run() end ..." , e);
        }
    }

    /**
     * @Description 生成核心客户号查询的二维sql
     * @param sheet
     * @param rowAll
     */
    private void createSel(HSSFSheet sheet, int rowAll) {
        logger.info("createSel 开始、、、");
        int num = 0;
        try {
            for (int i = 1; i <= rowAll; i++) {
                num = i;
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                /*row.getCell(6).setCellType(CellType.STRING);
                if ("#N/A".equals(row.getCell(6).getStringCellValue())) {
                    continue;
                }*/
                //姓名
                row.getCell(1).setCellType(CellType.STRING);
                String name = row.getCell(1).getStringCellValue();
                //性别
                row.getCell(2).setCellType(CellType.STRING);
                String sex = row.getCell(2).getStringCellValue();
                //证件类型
                row.getCell(3).setCellType(CellType.STRING);
                String idType = row.getCell(3).getStringCellValue();
                //证件号码
                row.getCell(0).setCellType(CellType.STRING);
                String idNo = row.getCell(0).getStringCellValue();
                //出生日期
                row.getCell(4).setCellType(CellType.STRING);
                String birthday = row.getCell(4).getStringCellValue();
                //核心客户号
//                String coreCustNo = row.getCell(6).getStringCellValue();
                //客户号
                /*row.getCell(5).setCellType(CellType.STRING);
                String customerNo = row.getCell(5).getStringCellValue();*/

                StringBuilder insSql = new StringBuilder();
                insSql.append("('")
                        .append(name).append("', '")
                        .append(idType).append("', '")
                        .append(sex).append("', '")
                        .append(idNo).append("', date'")
                        .append(birthday).append("' ),\n");
                content.append(insSql);
                //update eccustomer set coreCustNo = '' where customerNo = '';
                /*insSql.append("update eccustomer set coreCustNo = '")
                        .append(coreCustNo).append("' where customerNo = '")
                        .append(customerNo).append("';\n");
                content.append(insSql);*/
                /*insSql.append("or idno = '").append(idNo).append("' \n");
                content.append(insSql);*/
            }

            FileUtils.writeStringToFile(fileResulet, content.toString(), "UTF-8");

        } catch (Exception e) {
            logger.info("异常行数" + num);
            e.printStackTrace();
        }

        logger.info("createSel 结束、、、");
    }

    /**
     *
     * @param sheet 默认第一个工作簿
     * @param rowAll 行数
     */
    private void createRiskConfig(final HSSFSheet sheet, final  int rowAll) {
        logger.info("createRiskConfig start ...");

        try {
            for (int i = 1; i <= rowAll; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //销售方式
                row.getCell(4).setCellType(CellType.STRING);
                String selltype = row.getCell(4).getStringCellValue();
                //销售渠道
                row.getCell(2).setCellType(CellType.STRING);
                String salechnl = row.getCell(2).getStringCellValue();
                //险种
                row.getCell(0).setCellType(CellType.STRING);
                String riskcode = row.getCell(0).getStringCellValue();

                StringBuilder insSql = new StringBuilder();
                insSql.append("insert into `lp_config_new` (`salechnl`, `selltype`, `riskCode`, `item`, `agentCom`,`raisePlan`, `extends1`, `extends2`, `makeDate`, `lastModifyDate`) \n values('")
                        .append(salechnl).append("','")
                        .append(selltype).append("','")
                        .append(riskcode).append("','PC',NULL,NULL,NULL,NULL,now(),now());\n");
                content.append(insSql);
            }

            FileUtils.writeStringToFile(fileResulet, content.toString(), "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }

        logger.info("createRiskConfig end ...");
    }

    /**
     *
     * @return 返回第一个sheet
     * @throws Exception if anything can't be written.
     */
    private HSSFSheet checkFile() throws Exception {
        logger.info("checkFile() start ...");

        File file = new File(XLS_FILE);
        if (!file.exists()) {
            throw new Exception("导入的文件不存在");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取工作薄
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        //获取第一个sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            throw new Exception("您上传的文件中没有工作表。");
        }

        logger.info("checkFile() end ...");
        return sheet;
    }

    /**
     * 尊敬的${HOLDERNAME}${HOLDERSEX}，您${SOURCENAME}购买的${PRODUCTNAME}已于${PAYMENTDATE}成功缴纳首期保费并承保，
     * 保单号：${CONTNO}，保险期间${INSUYEAR}，交费期间${PAYDATE}，${PAYMENTFREQUENCYTYPE}保费${PREMIUM}元，
     * 犹豫期自${WTDATYSTARTDATE}至${WTDATYENDDATE}，请您关注“弘康人寿”官方微信或登录www.hongkang-life.com查询保单，
     * 并仔细阅读保险合同条款，详询4008500365。
     * @param sheet 默认第一个工作簿
     * @param rowAll 行数
     */
    private void createSms(final HSSFSheet sheet, final  int rowAll) {
        logger.info("createSms() start ...");

        try {
            String insert = "insert into sms_main (`sms_no`, `mobile`, `sms_text`, `status`, `should_send_date`, `should_send_time`,`source`, `create_date`, `create_time`, `generate_datetime`, `classify`, `template`)\n";

            for (int i = 1; i <= rowAll; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                //短信设置发送时间
                String shouldSendDate = "2019-05-13";
                String shouldSendTime = "18:15:00";
                //序号 自动生成
                String smsNo = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                //手机号
                row.getCell(4).setCellType(CellType.STRING);
                String mobile = row.getCell(4).getStringCellValue();
                //投保人姓名
                row.getCell(8).setCellType(CellType.STRING);
                String appntName = row.getCell(8).getStringCellValue();
                //先生/女士
                row.getCell(9).setCellType(CellType.STRING);
                String appntSex = row.getCell(9).getStringCellValue();
                //if ("女".equals(appntSex)) {
                //    appntSex = "女士";
                //} else if ("男".equals(appntSex)) {
                //    appntSex = "先生";
                //} else {
                //    System.out.println("性别怪异");
                //}
                //险种名称（如有主险和附加险，只展示主险名称即可）
                row.getCell(10).setCellType(CellType.STRING);
                String riskName = row.getCell(10).getStringCellValue();

                row.getCell(11).setCellType(CellType.STRING);
                String path = row.getCell(11).getStringCellValue();

                row.getCell(12).setCellType(CellType.STRING);
                String contno = row.getCell(12).getStringCellValue();

                //SM010202
                String smsText =  "尊敬的" + appntName + appntSex +  "，您购买的" + riskName + "电子保单"
                        +  path + "已生成，请及时下载阅读合同条款，并点击智能回访" + contno + "参与权益确认。更多服务请关注我公司官网、官微或拨打全国统一客服热线4008500365，感谢支持！";

                String values = "values('" + smsNo + "','" + mobile + "','" + smsText + "','1', '" + shouldSendDate + "', '" + shouldSendTime + "', '99',curdate(),curtime(), now(), 'C00002', 'SM010198');\n";
                content.append(insert).append(values);
            }

            FileUtils.writeStringToFile(fileResulet, content.toString(), "UTF-8");
            } catch (Exception e) {
            logger.info("createSms() end ...",e );
        }


    }

    /**
     *
     * @param sheet 默认第一个工作簿
     * @param rowAll 行数
     */
    private void selectRiskConfig(final HSSFSheet sheet, final int rowAll) {
        logger.info("selectRiskConfig() start ...");

        for (int i = 1; i <= rowAll; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            //销售方式
            row.getCell(4).setCellType(CellType.STRING);
            String selltype = row.getCell(4).getStringCellValue();
            //销售渠道
            row.getCell(2).setCellType(CellType.STRING);
            String salechnl = row.getCell(2).getStringCellValue();
            //险种
            row.getCell(0).setCellType(CellType.STRING);
            String riskcode = row.getCell(0).getStringCellValue();

            //查询险种是否配置
            StringBuffer selSql = new StringBuffer();
            selSql.append("SELECT * from lp_config_new where selltype = '")
                    .append(selltype).append("' and salechnl = '")
                    .append(salechnl).append("' and riskcode = '")
                    .append(riskcode).append("' and item = 'AC';");
            System.out.println(selSql);
            content.append(selSql).append("\n");
        }
        try {
            FileUtils.writeStringToFile(fileResulet, content.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    logger.info("selectRiskConfig() end ...");
    }

    /**
     *
     * @param args 主函数
     *
     */
    public static void main(final String[] args) {
        ReadExcelToSql readXls2SqlThread = new ReadExcelToSql("4");
        try {
            readXls2SqlThread.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
