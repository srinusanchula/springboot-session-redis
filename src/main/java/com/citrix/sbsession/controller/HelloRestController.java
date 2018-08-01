package com.citrix.sbsession.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

    private static String ACCESS_COUNT = "accessCount";

    @GetMapping("/hello")
    String hello(HttpSession session, HttpServletRequest request) {
        Integer accessCount = 1;
        if (session.getAttribute(ACCESS_COUNT) != null) {
            accessCount += (Integer) session.getAttribute(ACCESS_COUNT);
        }
        session.setAttribute(ACCESS_COUNT, accessCount);

        StringBuffer output = new StringBuffer();
        output.append("Hello ").append(request.getRemoteUser()).append('!');
        output.append('\n');
        output.append("SID: ").append(session.getId()).append('\n');
        output.append("AccessCount: ").append(accessCount).append('\n');
        output.append('\n');

        return output.toString();
    }

}