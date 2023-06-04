package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Qualifier("UserDbStorage")
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage, FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }


    public Friendship addFriend(int userId, int friendId) {
        var friendShip = friendshipStorage.getFriendsRelation(userId, friendId);

        if (friendShip == null) {
            try {
                return friendshipStorage.added(new Friendship(userId, friendId, false));
            } catch (Exception e) {
                throw new ObjectNotFoundException("Запись не найдена");
            }
        } else if (friendShip.isStatus()) {
            return friendShip;
        } else if (friendShip.getFriendId() == userId) {
            friendShip.setStatus(true);
            return friendshipStorage.update(friendShip);
        }

        return friendShip;
    }

    public void deleteFriend(int userId, int friendId) {
        var friendShip = friendshipStorage.getFriendsRelation(userId, friendId);

        if (friendShip == null) {
            throw new ObjectNotFoundException("Запись не найдена");
        } else {
            if (friendShip.isStatus()) {
                if (friendShip.getUserId() == userId) {
                    friendShip.setUserId(friendId);
                    friendShip.setFriendId(userId);
                    friendShip.setStatus(false);
                    friendshipStorage.update(friendShip);
                } else {
                    friendShip.setStatus(false);
                    friendshipStorage.update(friendShip);
                }
            } else {
                if (friendShip.getUserId() == userId) {
                    friendshipStorage.deleteById(friendShip);
                }
            }
        }
    }

    public List<User> getMutualFriends(int id, int otherId) {
        var friendshipsByUser1 = friendshipStorage.getFriendsIdByUser(id);
        var friendshipsByUser2 = friendshipStorage.getFriendsIdByUser(otherId);
        List<Integer> friendIdByUser1 = new ArrayList<>();
        List<Integer> friendIdByUser2 = new ArrayList<>();
        viewCommonFriends(friendshipsByUser1, friendIdByUser1, id);
        viewCommonFriends(friendshipsByUser2, friendIdByUser2, otherId);
        friendIdByUser1.retainAll(friendIdByUser2);
        List<User> users = new ArrayList<>();
        Map<Integer, User> userMap = userStorage.getUsersMap();

        for (var commonFriendId : friendIdByUser1) {
            if (userMap.containsKey(commonFriendId)) {
                users.add(userMap.get(commonFriendId));
            }
        }
        return users;
    }

    private void viewCommonFriends(List<Friendship> list, List<Integer> listId, int id) {
        for (var friendshipsByUser : list) {
            int userId;
            if (friendshipsByUser.getUserId() == id)
                userId = friendshipsByUser.getFriendId();
            else
                userId = friendshipsByUser.getUserId();
            listId.add(userId);
        }
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
        var friendships = friendshipStorage.getFriendsIdByUser(userId);
        List<User> users = new ArrayList<>();
        Map<Integer, User> userMap = userStorage.getUsersMap();

        for (var friendship : friendships) {
            int friendId;
            if (friendship.getUserId() == userId)
                friendId = friendship.getFriendId();
            else
                friendId = friendship.getUserId();
            if (userMap.containsKey(friendId)) {
                users.add(userMap.get(friendId));
            }
        }

        return users;
    }

}
