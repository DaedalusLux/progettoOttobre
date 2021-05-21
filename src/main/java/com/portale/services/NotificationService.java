package com.portale.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.NotificationMapper;
import com.portale.model.NotificationObject;

@Service
@Repository
public class NotificationService {
	@Autowired
	private NotificationMapper mapper;
	
	public Long GetNotificationsCount(int user_id) {
		Long result = mapper.GetNotificationsCount(user_id);
		return result == null ? 0 : result;
	}

	public void CreateNotification(NotificationObject notification, String title, String message, int importancyLevel, int from_user, int type) {
		mapper.CreateNotification(notification, new Date().getTime(), title, message, importancyLevel, from_user, type);
	}
	
	public void AppendNotificationToUser(int ref_to, int notification_ref) {
		mapper.AppendNotificationToUser(ref_to, notification_ref);
	}
	
	public Boolean isNotificationForUser(int user_id, int notificationId)
	{
		Integer ref_to = mapper.isNotificationForUser(user_id, notificationId);
		if(ref_to != null)
		{
			return true;
		}
		return false;
	}
	
	public void SetNotificationAsSeen(int user_id, int notificationId) {
		mapper.SetNotificationAsSeen(user_id, notificationId);
	}
	
	public NotificationObject GetNotificationById(int user_id, int notificationId)
	{
		return mapper.GetNotificationById(user_id, notificationId);
	}
	
	public List<NotificationObject> GetAllNotifications(int userId, int limit, int offset) {
		return mapper.GetAllNotifications(userId, limit, offset);
	}
	
	public Boolean AnyUnseenNotifications(int userRequestLogin) {
		Boolean any = false;
		try {
			any = mapper.AnyUnseenNotifications(userRequestLogin);
			any = any == false ? true : false;
			return any;
		}
		catch(Exception e){
			return any;
		}
	}
}
