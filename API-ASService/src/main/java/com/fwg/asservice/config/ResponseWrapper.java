package com.fwg.asservice.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper{
    
	private StringWriter stringWriter;
    private boolean isOutputStreamCalled;
 
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
 
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.stringWriter != null) {
            throw new IllegalStateException("The getWriter() is already called.");
        }
        isOutputStreamCalled = true;
        return super.getOutputStream();
    }
 
    @Override
    public PrintWriter getWriter() throws IOException {
        if (isOutputStreamCalled) {
            throw new IllegalStateException("The getOutputStream() is already called.");
        }
 
        this.stringWriter = new StringWriter();
 
        return new PrintWriter(this.stringWriter);
    }
 
    public String getResponseContent() {
        if (this.stringWriter != null) {
            return this.stringWriter.toString();
        }
        return "";
    }
}