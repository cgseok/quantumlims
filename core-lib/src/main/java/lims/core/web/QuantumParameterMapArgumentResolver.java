package lims.core.web;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lims.core.common.dto.QuantumParameterMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class QuantumParameterMapArgumentResolver implements HandlerMethodArgumentResolver {

    protected final transient static Logger LOG = LoggerFactory.getLogger(QuantumParameterMapArgumentResolver.class);
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer navContainer
            , NativeWebRequest webRequest
            , WebDataBinderFactory binderFactory) throws Exception {
        QuantumParameterMap parameterMap = new QuantumParameterMap();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();



        parameterMap.put("X-Auth-Token", request.getHeader("X-Auth-Token"));



        parameterMap.put("loginSession", request.getAttribute("loginSession"));


        String key = null;
        String[] values = null;
        Enumeration<?> enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()){
            key = (String) enumeration.nextElement();
            values = request.getParameterValues(key);
            if(values != null){
                parameterMap.put(key, (values.length > 1) ? values:values[0] );
            }
        }

        if(StringUtils.equals(request.getContentType(), "application/json")) {
            parameterMap.putAll(readRequestBody(request));
        }

        LOG.info(parameterMap.toString());
        return parameterMap;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return QuantumParameterMap.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> readRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder json = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        Gson gson = new Gson();
        try {
            LOG.info("LLLL : " + json.toString());
            return gson.fromJson(json.toString(), Map.class);
        } catch(JsonSyntaxException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Object>();
    }
}
