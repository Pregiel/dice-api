package pl.pregiel.diceapi.diceapi.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pregiel.diceapi.diceapi.View;
import pl.pregiel.diceapi.diceapi.model.Room;
import pl.pregiel.diceapi.diceapi.model.User;
import pl.pregiel.diceapi.diceapi.service.RoomService;
import pl.pregiel.diceapi.diceapi.service.UserService;
import pl.pregiel.diceapi.diceapi.validation.RoomValidator;

import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomValidator roomValidator;

    @RequestMapping(value = "/newroom", method = RequestMethod.GET)
    public String newRoom(Model model) {
        model.addAttribute("roomForm", new Room());

        return "newRoom";
    }

    @RequestMapping(value = "/newroom", method = RequestMethod.POST)
    public String newRoom(@ModelAttribute Room roomform,
                          BindingResult bindingResult,
                          Model model) {
        roomValidator.validate(roomform, bindingResult);

        if (bindingResult.hasErrors()) {
            return "newRoomError";
        }

        roomService.save(roomform);

        return "newRoomSuccess";
    }

    @GetMapping(value = "/{username}/roomlist")
    @ResponseBody
    @JsonView(View.RoomListView.class)
    public List<Room> roomList(@PathVariable String username) {
        if (!validUser(username)) {
            return null;
        }
        return userService.findByUsername(username).getRoomList();
    }

    @GetMapping(value = "/room/{roomId}/")
    @ResponseBody
    @JsonView(View.RoomView.class)
    public Room roomInfo(@PathVariable int roomId) {
        Room room = roomService.findById(roomId);
        if (!validUser(room.getUserList())) {
            return null;
        }
        return room;
    }

    private boolean validUser(@NonNull String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return username.equals(auth.getName());
    }

    private boolean validUser(@NonNull List<User> userList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (User user : userList) {
            if (user.getUsername().equals(auth.getName())) {
                return true;
            }
        }
        return false;
    }
}
