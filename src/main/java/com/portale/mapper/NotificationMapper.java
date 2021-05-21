package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.NotificationObject;

public interface NotificationMapper {
	// deprecated void CreateNotification(@Param("from") Long from, @Param("date")
	// Long date, @Param("message") String message);

	Long GetNotificationsCount(@Param("user_id") int user_id);

	void CreateNotification(@Param("notification") NotificationObject notification, @Param("date") Long date,
			@Param("title") String title, @Param("message") String message, @Param("importancy") int importancyLevel,
			@Param("from_user") int from_user, @Param("type") int type);

	void AppendNotificationToUser(@Param("ref_to") int ref_to, @Param("notification_ref") int notification_ref);

	void SetNotificationAsSeen(@Param("user_id") int user_id, @Param("notificationId") int notificationId);

	Integer isNotificationForUser(@Param("user_id") int user_id, @Param("notificationId") int notificationId);

	NotificationObject GetNotificationById(@Param("user_id") int user_id,
			@Param("notificationId") int notificationId);

	List<NotificationObject> GetAllNotifications(@Param("user_id") int user_id, @Param("limit") int limit,
			@Param("offset") int offset);

	Boolean AnyUnseenNotifications(@Param("to") int userRequestLogin);
}
