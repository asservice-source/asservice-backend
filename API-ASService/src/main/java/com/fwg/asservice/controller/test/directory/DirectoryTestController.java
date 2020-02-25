package com.fwg.asservice.controller.test.directory;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DirectoryTestController {

	
	@RequestMapping(value="/test/directory/ViewProjectRootPath")
	public void viewProjectRootPath(HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();
		writer.write("user.dir(System property) : " + System.getProperty("user.dir"));
		writer.write("\r\nPath from classloader : " + this.getClass().getClassLoader().getResource("").getPath());
	}
}
