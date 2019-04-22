package com.sixdee.wfm.controller;
/** @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]*/ 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/meta")
@Api(value = "MetaService", description = "Metadetails of Application (Health,MetaService)", tags = { "MetaService" })
public class IndexController {
	public static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @GetMapping
    @RequestMapping("/name")
    public String getApplicationName() {
        return "WFM : Work Force Management [Stateless API Engine]";
    }
}
