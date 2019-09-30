package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc.controller;

import com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * .
 * Created by jinm on  2019/09/27.  contact: keanemer.gmail.com
 */

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping("/person/create")
    public String createPerson(){
        personService.createPerson();
        return "success";
    }

}
