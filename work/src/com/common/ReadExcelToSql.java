package com.common;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.log4j.Logger;

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
    private static final  String XLS_FILE = "f:\\短信参数.xls";
    /**
     * 生成的sql文件
     */
    private static final  String SQL_FILE = "f:\\20190326-quanzhengzheng-（DATA-8021）-DML-02.sql";
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
     * @throws Exception if anything can't be written.
     */
    public void run() throws Exception {
        logger.info("ReadExcelToSql run() start ...");

        HSSFSheet sheet = checkFile();
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
        } else {
            createRiskConfig(sheet, rowAll);
        }

        logger.info("ReadExcelToSql run() end ...");
    }

    /**
     *
     * @param sheet 默认第一个工作簿
     * @param rowAll 行数
     */
    private void createRiskConfig(final HSSFSheet sheet, final  int rowAll) {
        logger.info("createRiskConfig start ...");

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
        try {
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

        String insert = "insert into sms_main (`sms_no`, `mobile`, `sms_text`, `status`, `should_send_date`, `should_send_time`,`source`, `create_date`, `create_time`, `generate_datetime`, `classify`, `template`)\n";

        for (int i = 1; i <= rowAll; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            System.out.println("开始转换第" + i + "行");
            //短信设置发送时间
            String shouldSendDate = "2019-03-26";
            String shouldSendTime = "15:15:00";
            //序号 自动生成
            String smsNo = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            //手机号
            row.getCell(2).setCellType(CellType.STRING);
            String mobile = row.getCell(2).getStringCellValue();
            //投保人姓名
            row.getCell(4).setCellType(CellType.STRING);
            String appntName = row.getCell(4).getStringCellValue();
            //先生/女士
            row.getCell(5).setCellType(CellType.STRING);
            String appntSex = row.getCell(5).getStringCellValue();
            //if ("女".equals(appntSex)) {
            //    appntSex = "女士";
            //} else if ("男".equals(appntSex)) {
            //    appntSex = "先生";
            //} else {
            //    System.out.println("性别怪异");
            //}
            //险种名称（如有主险和附加险，只展示主险名称即可）
            String riskName = row.getCell(7).getStringCellValue();
            //String insuredName = row.getCell('3').getStringCellValue();
            // 购买渠道
            /*String salechnl = row.getCell(6).getStringCellValue();
            //购买日期
            String paymentdate = row.getCell(8).getStringCellValue();
            //保单号
            String contNo = row.getCell(9).getStringCellValue();
            //保险期间
            String insuyear = row.getCell(10).getStringCellValue();
            //缴费期间
            String paydate = row.getCell(11).getStringCellValue();
            //缴费方式
            String paymentfrequencytype = "";
            //保费
            String premium = row.getCell(12).getStringCellValue();
            // 犹豫期起始日期
            String wtdatystartdate = row.getCell(13).getStringCellValue();
            // 犹豫期止期
            String wtdatyenddate = row.getCell(14).getStringCellValue();*/

            String AGENTNAME = row.getCell(3).getStringCellValue();
            String HOLDERNAME = row.getCell(4).getStringCellValue();
            String AGENCYNAME = row.getCell(5).getStringCellValue();
            String PRODUCTNAME = row.getCell(6).getStringCellValue();
            String CONTNO = row.getCell(7).getStringCellValue();
            String INSUYEAR = row.getCell(8).getStringCellValue();
            String PAYDATE = row.getCell(9).getStringCellValue();
            String PRESERVATIONAMOUNT = row.getCell(10).getStringCellValue();
            //SM010202
            String smsText = AGENTNAME + "伙伴，您好！客户" + HOLDERNAME + "在" + AGENCYNAME + "投保的" +
                    PRODUCTNAME + "已退保，保单尾号" + CONTNO + "，保险期间" + INSUYEAR + "，交费期间" +
                    PAYDATE + "，共计" + PRESERVATIONAMOUNT + "元，请关注！";
            //SM010148
            //String smsText = "尊敬的" + appntName + appntSex + "，您" + salechnl + "购买的" + riskName + "已于" + paymentdate +
            //        "成功缴纳首期保费并承保，保单号：" + contNo + "，保险期间" + insuyear + "，交费期间" + paydate + "，" +
            //        paymentfrequencytype + "保费" + premium + "元，犹豫期自" + wtdatystartdate + "至" + wtdatyenddate +
            //        "，请您关注“弘康人寿”官方微信或登录www.hongkang-life.com查询保单，并仔细阅读保险合同条款，详询4008500365。 \n";

            //String smsText = "尊敬的" + appntName + appntSex + "：您好！您在我司为" + insuredName + "购买的"
            //        + riskName + "，保障截止日期为" + "2020/2/24" + "，根据公司规划，该产品已于2019年2月28日18时停售，停售后到期的保单将无法再次续保。为不影响您的保障规划，您可关注弘康人寿微信公众号，提前选择其它保障产品。感谢您的支持，详询4008500365！";
            String values = "values('" + smsNo + "','" + mobile + "','" + smsText + "','1', '" + shouldSendDate + "', '" + shouldSendTime + "', '99',curdate(),curtime(), now(), 'C00002', 'SM010202');\n";
            content.append(insert).append(values);
        }
            try {
                FileUtils.writeStringToFile(fileResulet, content.toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        logger.info("createSms() end ...");
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
        ReadExcelToSql readXls2SqlThread = new ReadExcelToSql("0");
        try {
            readXls2SqlThread.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
