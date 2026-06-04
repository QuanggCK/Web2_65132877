package clc65.quanggck.services;

import clc65.quanggck.models.Notification;
import clc65.quanggck.repos.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getTop5RecentNotifications() {
        return notificationRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public void addNotification(String message) {
        Notification notification = new Notification(message);
        notificationRepository.save(notification);
    }
}