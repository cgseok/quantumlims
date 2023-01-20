package lims.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

public class QuantumStringUtil {

    protected final transient static Logger LOG = LoggerFactory.getLogger(QuantumStringUtil.class);

    private QuantumStringUtil() {
    }

    // UUID 생성
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 6자리 임시비밀번호 생성
    public static String generateTempPassword() {
        int start = (int)(Math.random()*27);
        return generateUUID().substring(start, start+6);
    }

    // 4자리 인증번호생성
    public static String generateCertificationNumber() {
        Random random = new Random();

        int result = random.nextInt(10000)+1000;

        if(result>10000){
            result = result - 1000;
        }

        return Integer.toString(result);

    }

    public static boolean isNumber(String str) {

        if(StringUtils.isBlank(str)) {
            return true;
        }

        return Pattern.matches("^[+-]?\\d*(\\.?\\d*)$", str);
    }

    public static boolean isNumberOnly(String str) {

        if(StringUtils.isBlank(str)) {
            return true;
        }

        return Pattern.matches("(^[0-9]*$)", str);
    }

    public static boolean isEmail(final String str) {
        if(StringUtils.isBlank(str)) {
            return true;
        }
        return Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
    }

    public static boolean isCellPhoneNumber(final String str) {

        if(StringUtils.isBlank(str)) {
            return true;
        }

        return Pattern.matches("^(?:(010\\d{4})|(01[1|6|7|8|9]\\d{3,4}))(\\d{4})$", str);
    }



    public static void main(String args[]) {
        LOG.info(QuantumStringUtil.generateUUID());
    }
}
