package pl.pregiel.diceapi.diceapi.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pregiel.diceapi.diceapi.model.Room;
import pl.pregiel.diceapi.diceapi.service.RoomService;

@Component
public class RoomValidator implements Validator {
    @Autowired
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Room.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Room room = (Room) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
        if (room.getTitle().length() < 6 || room.getTitle().length() > 32) {
            errors.rejectValue("title", "title.size");
        }
    }
}
