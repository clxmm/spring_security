package org.clxmm.springsecurity02.modules.common.entity.util;

import java.io.*;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * p> 多次读写BODY用HTTP REQUEST - 解决流只能读一次问题 </p>
 *
 * @author clx
 * @date 2020/7/10 9:10
 */
@Slf4j
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {


    private final byte[] body;


    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
        body = getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {


            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };

    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    private String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
//        InputStream inputStream = null;
//        BufferedReader reader = null;

        try (
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))
        ) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 将前端请求的表单数据转换成json字符串 - 前后端一体的情况下使用
     *
     * @param request:
     * @return: java.lang.String
     */
    public String getBodyJsonStrByForm(ServletRequest request) {
        Map<String, Object> bodyMap = new HashMap<>();
        try {
            //参数定义
            String paraName = null;
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                paraName = parameterNames.nextElement();
                bodyMap.put(paraName, request.getParameter(paraName));
            }
        } catch (Exception e) {
            log.error("请求参数转换错误：", e);

        }
        // json对象转json字符串 转javabean
//        SecurityUser user = JSONObject.parseObject(JSONObject.toJSONString(bodyMap), SecurityUser.class);
        // json对象转json字符串
        return JSONObject.toJSONString(bodyMap);
    }


    /**
     * 将前端传递的json数据转换成json字符串 - 前后端分离的情况下使用
     *
     * @param request:
     * @return: java.lang.String
     */
    public String getBodyJsonStrByJson(ServletRequest request) {

        StringBuffer json = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            log.error("请求参数装换错误：", e);
        }
        return json.toString();
    }


}
