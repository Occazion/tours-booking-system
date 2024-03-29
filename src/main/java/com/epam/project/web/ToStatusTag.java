package com.epam.project.web;

import com.epam.project.db.Status;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class ToStatusTag extends SimpleTagSupport {
    StringWriter sw = new StringWriter();
    public void doTag()

            throws JspException, IOException {
        getJspBody().invoke(sw);

        String content = sw.toString();

            content = content.trim();
        int value;
        if (content.length() == 0) {
            value = 0;
        } else {
            try{
                value = Integer.parseInt(content);
            } catch(NumberFormatException e) {
                value = 0;
            }
        }
        getJspContext().getOut().println(Status.getStatus(value));
    }

}
