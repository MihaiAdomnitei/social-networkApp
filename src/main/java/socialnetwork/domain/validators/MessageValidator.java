package socialnetwork.domain.validators;

import socialnetwork.domain.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        if(entity.getText().isEmpty())
            throw new ValidationException("Textul mesajului nu poate fi nul");
    }
}
