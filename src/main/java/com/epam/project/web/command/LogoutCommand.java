package com.epam.project.web.command;

import com.epam.project.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends Command{
        private static final Logger log = Logger.getLogger(LogoutCommand.class);

        @Override
        public String execute(HttpServletRequest request, HttpServletResponse response) {
            log.debug("Command starts");

            HttpSession session = request.getSession(false);
            if (session != null) {
                log.debug("Invalidating session");
                session.invalidate();
            }

            log.debug("Command finished");
            return Path.PAGE_LOGIN;
        }
}
