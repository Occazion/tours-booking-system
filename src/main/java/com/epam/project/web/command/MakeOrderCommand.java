package com.epam.project.web.command;

import com.epam.project.Path;
import com.epam.project.db.Status;
import com.epam.project.db.entity.User;
import com.epam.project.db.service.TourService;
import com.epam.project.exception.AppException;
import com.epam.project.exception.DBException;
import com.epam.project.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MakeOrderCommand extends Command{

    private static final Logger log = Logger.getLogger(MakeOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        log.debug("Command starts");
        String[] tourIds = request.getParameterValues("tourId");
        log.debug("Tour IDs --> " + Arrays.toString(tourIds));

        if (tourIds == null) {
            throw new AppException("Tour not selected");
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        log.info("User id --> " + user.getId());
        List<Long> idList = new ArrayList<>();

        Arrays.stream(tourIds).forEach(x -> idList.add(Long.parseLong(x)));

        log.debug("Ordering tours");

        idList.forEach(x -> {
            try {
                TourService.orderTour(user.getId(), x,Status.CONFIRMED);
            } catch (DBException e) {
                String errorMessage = Messages.ERR_CANNOT_UPDATE_TOUR;
                log.error("errorMessage --> " + errorMessage);
                //throw new AppException(errorMessage + ":" + e.getMessage(),e);
            }
        });

        log.debug("Command finished");
        return Path.COMMAND_TOUR_MENU;
    }
}
