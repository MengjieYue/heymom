package com.heymom.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lujian
 * @date 15/5/25.
 */

@Controller
@RequestMapping(value = "/backend/tips")
public class TipsController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView tips(HttpServletRequest request) {
        int code = (Integer) request.getAttribute("code");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tips");
        mav.addObject("code", code);
        return mav;
    }
}
