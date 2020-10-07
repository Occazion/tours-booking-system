package com.epam.project.web.command;

import com.epam.project.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupCommand extends Command{
    private static final Logger LOG = Logger.getLogger(SignupCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");


        LOG.debug("Command finished");
        return Path.PAGE_ERROR_PAGE;
    }
}