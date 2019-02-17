package pl.pregiel.diceapi.diceapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.pregiel.diceapi.diceapi.model.Room;
import pl.pregiel.diceapi.diceapi.model.User;
import pl.pregiel.diceapi.diceapi.repository.RoleRepository;
import pl.pregiel.diceapi.diceapi.repository.UserRepository;
import pl.pregiel.diceapi.diceapi.service.UserService;
import pl.pregiel.diceapi.diceapi.validation.UserValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserValidator userValidator;


//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registrationForm";
//    }


    /**
     * @return String in JSON
     *      {
     *            "registration": "success/failure",
     *            "errors": [ list_of_errors ]
     *      }
     *
     * Possible returned errors:
     *  username.notEmpty, username.size, username.duplicate,
     *  password.notEmpty, password.size,
     *  confirmPassword.notEmpty, confirmPassword.diff
     */
    @RequestMapping(value = "/registration/", method = RequestMethod.POST)
    public String registration(@RequestBody User user,
                               BindingResult bindingResult) {
        user.setRole(roleRepository.findById(1));

        userValidator.validate(user, bindingResult);

        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode arrayNode = mapper.createArrayNode();
        ObjectNode objectNode = mapper.createObjectNode();

        if (bindingResult.hasErrors()) {
            objectNode.put("registration", "failure");


            List<String> errors = bindingResult.getAllErrors().stream().map(
                    objectError -> objectError.getCode()).collect(Collectors.toList());

            ArrayNode errorArrayNode = mapper.valueToTree(errors);
            objectNode.putArray("errors").addAll(errorArrayNode);

//            objectNode.add(objectNode);
        } else {
            objectNode.put("registration", "success");
            userService.save(user);
        }

        System.out.println(objectNode.toString());
        return objectNode.toString();
    }

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//        if (error != null) {
//            model.addAttribute("error", "Your login and password is invalid.");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "You have been logged out successfully.");
//        }
//
//        return "logi2n";
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(@RequestParam String login,
//                        @RequestParam String password,
//                        Model model) {
//        User user = userService.login(login, password);
//
//        if (user == null) {
//            return "0";
//        }

//   //     return "username=" + user.getUsername();
//    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(@RequestParam(defaultValue = "noname") String name) {
        return "welcome " + name;
    }


    @RequestMapping(value = "/jackson")
    @ResponseBody
    public Room jackson(@RequestParam(name = "title", required = false, defaultValue = "moj pokoj") String title) {
        Room rom = new Room();
        rom.setId(123);
        rom.setTitle(title);
        rom.setPassword("qwe");
        return rom;
    }
}
