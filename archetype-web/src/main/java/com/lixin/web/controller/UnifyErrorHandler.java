package com.lixin.web.controller;

import com.lixin.dal.model.Result;
import com.lixin.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * springboot 2.3之前版本
 *
 * @author Croky.Zheng
 * 2020年6月18日 下午11:51:30
 */
@RestController
public class UnifyErrorHandler implements ErrorController {
    private static final Logger log = LoggerFactory.getLogger(UnifyErrorHandler.class);

    @RequestMapping("/error")
    public Result<String> handleError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=utf-8");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        log.error("referer:" + request.getHeader("referer") + " url:" + RequestUtils.getDomain(request) + " statusCode:" + statusCode + " failed!");
        //获取statusCode:401,404,500
        switch (statusCode) {

            case 401:
            case 402:
            case 403:
                return Result.error(statusCode * -1, "The request is missing access");
            case 404:
                return Result.error(-404, "The requested interface is not exist");
            case 405:
                return Result.error("no authorization");
            case 500:
                return Result.error(-500, "no Authorization");
            default:
                return Result.error(statusCode * -1, "An unknown error occurred on the requested interface");
        }

    }
}
