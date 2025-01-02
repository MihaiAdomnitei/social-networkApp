package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie>{

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        Long id1 = entity.getId().getLeft();
        Long id2 = entity.getId().getRight();

        if(id1.equals(id2))
            throw new ValidationException("Idul nu poate fi acelasi");

    }
}
