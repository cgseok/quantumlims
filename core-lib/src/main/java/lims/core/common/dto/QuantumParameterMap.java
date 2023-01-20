package lims.core.common.dto;

import lims.core.util.QuantumDateUtil;
import lims.core.util.QuantumStringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class QuantumParameterMap implements Serializable {


    private static final long serialVersionUID = 8979341319398726521L;

    private Map<String, Object> param = new HashMap<String, Object>();

    public Map<String, Object> getParam() {
        return param;
    }

    public int size() {
        return this.param.size();
    }

    public boolean isEmpty() {
        return this.param.isEmpty();
    }

    public boolean containsKey(String key) {
        return param.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return param.containsValue(value);
    }

    public Object get(String key) {
        return param.get(key);
    }

    public Object put(String key, Object value) {
        return param.put(key, value);
    }

    public Object remove(String key) {
        return param.remove(key);
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        if(m != null) {
            param.putAll(m);
        }
    }

    public void clear() {
        param.clear();
    }

    public Set<String> keySet() {
        return param.keySet();
    }

    public Collection<Object> values() {
        return param.values();
    }

    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return param.entrySet();
    }

    public String toString() {
        return param.toString();
    }

    public String getString(String key) {
        Object o = get(key);
        return o != null ? o.toString() : "";
    }

    public String getTrimString(String key) {
        return getString(key).trim();
    }

    public int getInt(String key) {
        return (int) getDouble(key);
    }

    public long getLong(String key) {
        return (long) getDouble(key);
    }

    public double getDouble(String key) {
        double rtnValue = 0.0;
        try {
            rtnValue = new Double(getTrimString(key)).doubleValue();
        } catch (NumberFormatException e) {
            rtnValue = 0.0;
        }
        return rtnValue;
    }



    public boolean isBlank(String key) {
        return StringUtils.isBlank(getTrimString(key));
    }

    public boolean isCellPhoneNumber(String key) {

        return QuantumStringUtil.isCellPhoneNumber(getTrimString(key));
    }

    public boolean isNumber(String key) {

        return QuantumStringUtil.isNumber(getTrimString(key));
    }

    public boolean isNumberOnly(String key) {

        return QuantumStringUtil.isNumberOnly(getTrimString(key));
    }

    public boolean isDate(String key) {

        return QuantumDateUtil.isDate(getTrimString(key));
    }

    public boolean isEmail(String key) {

        return QuantumStringUtil.isEmail(getTrimString(key));
    }

    public void setPasswordEncode(String key, String value) {
        put(key, value);
    }

    public String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public int getPageSize() {
        if(getInt("pageSize") < 1) {
            return 20;
        }
        return getInt("pageSize");
    }

    public int getPageNum() {
        if(getInt("pageNum") < 1) {
            return 1;
        }
        return getInt("pageNum");
    }

    public int getPageStart() {
        int pageStart = 0;
        if(getInt("pageNum") > 1) {
            pageStart =  (getInt("pageNum") - 1) * getPageSize();
        }
        return pageStart;
    }

    public String getLoginSessionValueString(String key) {
        try {
            Map<String,Object> loginSession = (Map<String, Object>) get("loginSession");
            Object o = loginSession.get(key);
            return o != null ? o.toString() : "";
        } catch (Exception e) {
            return "";
        }

    }
}
