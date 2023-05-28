package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.FriendshipDbStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Qualifier("UserDbStorage")
    private final UserStorage userStorage;
    private final FriendshipDbStorage friendshipDbStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage, FriendshipDbStorage friendshipDbStorage) {
        this.userStorage = userStorage;
        this.friendshipDbStorage = friendshipDbStorage;
    }


    public Friendship addFriend(int userId, int friendId) {
        var friendShip = friendshipDbStorage.getFriendsRelation(userId, friendId);

        if (friendShip == null) {
            try {
                return friendshipDbStorage.added(new Friendship(userId, friendId, false));
            } catch (Exception e) {
                throw new ObjectNotFoundException("Запись не найдена");
            }
        } else if (friendShip.isStatus()) {
            return friendShip;
        } else if (friendShip.getFriendId() == userId) {
            friendShip.setStatus(true);
            return friendshipDbStorage.update(friendShip);
        }

        return friendShip;
    }

    public void deleteFriend(int userId, int friendId) {
        var friendShip = friendshipDbStorage.getFriendsRelation(userId, friendId);

        if (friendShip == null) {
            throw new ObjectNotFoundException("Запись не найдена");
        } else {
            if (friendShip.isStatus()) {
                if (friendShip.getUserId() == userId) {
                    friendShip.setUserId(friendId);
                    friendShip.setFriendId(userId);
                    friendShip.setStatus(false);
                    friendshipDbStorage.update(friendShip);
                } else {
                    friendShip.setStatus(false);
                    friendshipDbStorage.update(friendShip);
                }
            } else {
                if (friendShip.getUserId() == userId) {
                    friendshipDbStorage.deleteById(friendShip);
                }
            }
        }
    }

    public List<User> getMutualFriends(int id, int otherId) {
        var friendshipsByUser1 = friendshipDbStorage.getFriendsIdByUser(id);
        var friendshipsByUser2 = friendshipDbStorage.getFriendsIdByUser(otherId);
        List<Integer> friendIdByUser1 = new ArrayList<>();
        List<Integer> friendIdByUser2 = new ArrayList<>();
        for (var friendshipsByUser : friendshipsByUser1) {
            int userId;
            if (friendshipsByUser.getUserId() == id)
                userId = friendshipsByUser.getFriendId();
            else
                userId = friendshipsByUser.getUserId();
            friendIdByUser1.add(userId);
        }

        for (var friendshipsByUser : friendshipsByUser2) {
            int userId;
            if (friendshipsByUser.getUserId() == otherId)
                userId = friendshipsByUser.getFriendId();
            else
                userId = friendshipsByUser.getUserId();
            friendIdByUser2.add(userId);
        }

        friendIdByUser1.retainAll(friendIdByUser2);
        List<User> users = new ArrayList<>();

        for (var commonFriendId : friendIdByUser1) {
            users.add(userStorage.findUserById(commonFriendId));
        }

        return users;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User findUserById(int userId) {
        return userStorage.findUserById(userId);
    }

    public List<User> getListOfFriends(int userId) {
        var friendships = friendshipDbStorage.getFriendsIdByUser(userId);
        List<User> users = new ArrayList<>();

        for (var friendship : friendships) {
            int friendId;
            if (friendship.getUserId() == userId)
                friendId = friendship.getFriendId();
            else
                friendId = friendship.getUserId();
            var user = userStorage.findUserById(friendId);
            users.add(user);
        }

        return users;
    }

}
