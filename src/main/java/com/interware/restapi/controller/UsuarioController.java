package com.interware.restapi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Alfredo Estrada
 */
@RequestMapping("/usuario")
@RestController
public class UsuarioController {
    private static final Logger logger = Logger.getLogger(UsuarioController.class);

    @PostConstruct
    public void postConstruct(){
        logger.debug("-->>postConstruct: ok");
    }
    
    @RequestMapping(
			value = "/now", 
			method = RequestMethod.GET, 
			produces = "application/json")
    public String getNow() {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now=new Date();
        logger.debug("-->>getNow: now="+sdf.format(now));
        return "{\"now\":\""+sdf.format(now)+"\"}";
    }
    
}
