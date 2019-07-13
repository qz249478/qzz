package com.common.test;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * @author quanzhengzheng
 * @date 2019-04-16
 */
public class MainTest {

    /**
     * 增加获取字符串长度的方法
     * 中文字符长度为2,其他为1
     * @param value 字符串
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static void main(String[] args) {
        /*String reg1 = "^[a-z|A-Z][a-z|A-Z|.|·| |\\s]*[a-z|A-Z]$";
        String reg2 = "^[\u4E00-\u9FA5][\u4E00-\u9FA5|.|·| |\\s]*[\u4E00-\u9FA5]$";
        String appntName = "JI-EYING CHEN";
        int tNameLength = chineseLength(appntName);
        String tempName = appntName.replaceAll("\\.","").replaceAll("·","").replaceAll(" ","");
        if(!((appntName.matches(reg1) && tempName.length()>=4) ||appntName.matches(reg2))|| tNameLength<4 ||tNameLength>40)
        {
            System.out.print("true");
        }else {
            System.out.print("false");
        }*/

        float ptPrem = 0.057f;
        String mPrem = "50150000";
        double mainMnt = Double.parseDouble(mPrem);
        double hmxBf = (mainMnt / 1000D) * (double)ptPrem;
//        BigDecimal convertVal = new BigDecimal(hmxBf+"");
//        hmPrem = convertVal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//        System.out.print(hmxBf);
        String xml = "<PackageList><Package><Header><UUID>4c7903a1-d609-4969-b59e-3a5facbe116f</UUID><Serial>Hz20190705017313</Serial><Asyn>false</Asyn><RequestType>0101</RequestType><ComId>10001</ComId><SourceId>huize.com</SourceId><SendTime>2019-07-11 14:45:03</SendTime></Header><Request><ApplyDate>2019-07-05 15:50:37</ApplyDate><AddSerial>huize5494037</AddSerial><InsuredName>张弘毅</InsuredName><InsuredCardType>1</InsuredCardType><InsuredCardNo>150102198902280118</InsuredCardNo><InsuredBirthday>1989-02-28</InsuredBirthday><InsuredSex>1</InsuredSex><InsuredNationality>CHN</InsuredNationality><InsuredProvince>1</InsuredProvince><InsuredCity>2801</InsuredCity><InsuredHeight>168</InsuredHeight><InsuredWeight>75</InsuredWeight><InsuredHealth>&lt;br&gt;问题1：不符合健康告知内容（疾病名称/异常情况）&lt;br&gt;答：1、慢性咽炎\n" +
                "2.y－谷氨酰转移酶增高\n" +
                "3、尿酸增高\n" +
                "4、肾钙化灶（复查无异常）\n" +
                "5、肾囊肿（复查无异常）\n" +
                "6、颈椎曲度后弓\n" +
                "7、16年曾经咳嗽被医生怀疑哮喘，检查之后确诊咳嗽&lt;br&gt;问题2：具体情况（请您告知发病/检查时间，症状，治疗方式及情况&lt;br&gt;答：16年曾经咳嗽被医生怀疑哮喘，检查之后确诊咳嗽，18年检查有慢性咽炎.y－谷氨酰转移酶增高、尿酸增高、肾钙化灶、肾囊肿（复查无异常）、颈椎曲度后弓，19年复查双肾无异常，前列腺增大&lt;br&gt;问题3：治疗结果及目前健康情况&lt;br&gt;答：无需治疗&lt;br&gt;问题4：其他补充说明&lt;br&gt;答：无&lt;br&gt;</InsuredHealth><Risks><RiskCount>2</RiskCount><Risk><RiskCode>huiz_dbb001</RiskCode><MainRiskCode>huiz_dbb001</MainRiskCode><Amnt>50000000</Amnt><Prem>860000</Prem><RnewFlag>-2</RnewFlag><PayIntv>12</PayIntv><InsuYearFlag>A</InsuYearFlag><InsuYear>106</InsuYear><PayEndYearFlag>Y</PayEndYearFlag><PayEndYear>30</PayEndYear></Risk><Risk><RiskCode>huiz_zjyl001</RiskCode><MainRiskCode>huiz_dbb001</MainRiskCode><Amnt>50000000</Amnt><Prem>1000</Prem><RnewFlag>-1</RnewFlag><PayIntv>0</PayIntv><InsuYearFlag>Y</InsuYearFlag><InsuYear>1</InsuYear><PayEndYearFlag>Y</PayEndYearFlag><PayEndYear>1</PayEndYear></Risk></Risks><FileName>AF190705017313503475.zip</FileName><FileNewName>AF190705017313503475.zip</FileNewName><FilePath>/2019/07/11/</FilePath><ApplySerial>54942561562827503472</ApplySerial></Request></Package></PackageList>";
        try {
            Document doc = DocumentHelper.parseText(xml);
            System.out.print("111111");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
