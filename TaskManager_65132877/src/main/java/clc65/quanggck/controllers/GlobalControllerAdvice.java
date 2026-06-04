package clc65.quanggck.controllers;

import clc65.quanggck.services.NotificationService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final NotificationService notificationService;

    public GlobalControllerAdvice(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("topNotifications", notificationService.getTop5RecentNotifications());
    }
}