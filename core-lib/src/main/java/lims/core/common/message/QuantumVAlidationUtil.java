package lims.core.common.message;

import com.google.gson.Gson;
import lims.core.common.dto.QuantumParameterMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuantumVAlidationUtil {


    private QuantumVAlidationUtil() {

    }

    public static boolean validate(String apiName, QuantumParameterMap param) {
        boolean isValid = true;

        String message = QuantumMessageUtil.getMessage(apiName);

        Gson gson = new Gson();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = gson.fromJson(message, List.class);

        Map<String, Object> errorData = new HashMap<String, Object>();

        List<String> requiredKeys = new ArrayList<String>();
        List<String[]> dataTypeKeys = new ArrayList<String[]>();

        for(Map<String, Object> map : list) {
            for(Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldKey = entry.getKey();
                @SuppressWarnings("unchecked")
                Map<String, Object> valMap = (Map<String, Object>) entry.getValue();

                boolean required = (boolean) valMap.get("required");

                if(required) {
                    if(param.isBlank(fieldKey)) {
                        isValid = false;
                        requiredKeys.add(fieldKey);
                        continue;
                    }
                }

                String dataType = (String) valMap.get("dataType");


                if(StringUtils.equals(dataType, "cellPhone")) {
                    if(!param.isCellPhoneNumber(fieldKey)) {
                        isValid = false;
                        dataTypeKeys.add(new String[]{fieldKey, "cellPhone"});
                    }
                } else if(StringUtils.equals(dataType, "number")) {
                    if(!param.isNumber(fieldKey)) {
                        isValid = false;
                        dataTypeKeys.add(new String[]{fieldKey, "number"});
                    }
                } else if(StringUtils.equals(dataType, "numberOnly")) {
                    if(!param.isNumberOnly(fieldKey)) {
                        isValid = false;
                        dataTypeKeys.add(new String[]{fieldKey, "numberOnly"});
                    }
                } else if(StringUtils.equals(dataType, "date")) {
                    if(!param.isDate(fieldKey)) {
                        isValid = false;
                        dataTypeKeys.add(new String[]{fieldKey, "date"});
                    }
                } else if(StringUtils.equals(dataType, "email")) {
                    if(!param.isEmail(fieldKey)) {
                        isValid = false;
                        dataTypeKeys.add(new String[]{fieldKey, "email"});
                    }
                }
            }
        }
        if(requiredKeys.size() > 0) {
            errorData.put("requiredKeys", requiredKeys);
        }
        if(dataTypeKeys.size() > 0) {
            errorData.put("dataTypeKeys", dataTypeKeys);
        }
        param.put("errorData", errorData);
        return isValid;

    }

}
