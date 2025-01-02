package socialnetwork.repository.PagingRepository;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.UserDBRepository;
import socialnetwork.repository.paging.Page;
import socialnetwork.repository.paging.Pageable;
import socialnetwork.repository.paging.PagingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBPagingRepository extends UserDBRepository implements PagingRepository<Long, Utilizator> {


    private Connection connection;

    public UserDBPagingRepository(UtilizatorValidator validator, String url, String username, String password) {
        super(validator, url, username, password);
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Page<Utilizator> findAllOnPage(Pageable pageable, Long i) {

        List<Utilizator> utilizators = new ArrayList<>();
        try (
                PreparedStatement pagestatement = connection.prepareStatement("SELECT * from users limit ? offset ?");
                PreparedStatement countstatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM users")
        ) {

            pagestatement.setInt(1,pageable.getPageSize());
            pagestatement.setInt(2,pageable.getPageSize() *pageable.getPageNr());

            try(
                    ResultSet pageResultSet = pagestatement.executeQuery();
                    ResultSet countResultSet = countstatement.executeQuery()
            ){
                int count = 0;
                if(countResultSet.next()){
                    count = countResultSet.getInt("count");
                }

            while(pageResultSet.next()){
                Long id = pageResultSet.getLong("id");
                String username = pageResultSet.getString("username");
                String firstName = pageResultSet.getString("first_name");
                String lastName = pageResultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(username, firstName, lastName);
                utilizator.setId(id);

                utilizators.add(utilizator);
            }

            return new Page<>(utilizators, count);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
