package com.tj.spring.cloud.zuul.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Component
public class WebAdminFeginFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "tj-spring-cloud-web-admin-feign";
    }

    /**
     * 请求失败返回信息给调用者
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 网关向api服务请求失败了，但是消费者客户端向网关发起的请求是成功的
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是集群
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 响应内容
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String,Object> map = new HashMap<>();
                map.put("status",200);
                map.put("message","无法连接，请检查网络");
                return new ByteArrayInputStream(objectMapper.writeValueAsString(map).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和getBody中的内容读码一致
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}