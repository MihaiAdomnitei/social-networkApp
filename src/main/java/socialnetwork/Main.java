package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.*;
import socialnetwork.repository.PagingRepository.CerereDBPagingRepository;
import socialnetwork.repository.PagingRepository.PrietenieDBPagingRepository;
import socialnetwork.repository.PagingRepository.UserDBPagingRepository;
import socialnetwork.service.CerereService;
import socialnetwork.service.ConversationService;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;

import java.io.IOException;

public class Main extends Application {

    final String url = "jdbc:postgresql://localhost:5432/SocialNetworkDB";
    final String username = "postgres";
    final String password = "mihai123";

    UserDBRepository repoUtilizator = new UserDBRepository(new UtilizatorValidator(), url, username, password);
    UserDBPagingRepository userDBPagingRepository = new UserDBPagingRepository(new UtilizatorValidator(), url, username, password);
    UtilizatorService serv = new UtilizatorService(userDBPagingRepository, repoUtilizator);

    CerereValidator cerereValidator = new CerereValidator();
    PrietenieValidator prietenieValidator = new PrietenieValidator();
    CerereDBRepository cerereDBRepository = new CerereDBRepository(cerereValidator, url, username, password);
    PrietenieDBRepository prietenieDBRepository = new PrietenieDBRepository(prietenieValidator, url, username, password);
    UtilizatorValidator utilizatorValidator = new UtilizatorValidator();
    UserDBRepository userDBRepository = new UserDBPagingRepository(utilizatorValidator, url, username, password);
    CerereDBPagingRepository cerereDBPagingRepository = new CerereDBPagingRepository(cerereValidator, url, username, password);
    PrietenieDBPagingRepository prietenieDBPagingRepository = new PrietenieDBPagingRepository(prietenieValidator, url,username,password);
    CerereService cerereService = new CerereService(prietenieDBRepository, userDBRepository, cerereDBRepository, cerereDBPagingRepository);
    PrietenieService prietenieService = new PrietenieService(repoUtilizator, prietenieDBPagingRepository, prietenieDBRepository);
    ConversationDBRepository conversationDBRepository = new ConversationDBRepository(new ConversationValidator(), url, username, password);
    ConversationService conversationService = new ConversationService(conversationDBRepository, prietenieDBRepository, userDBRepository);

    public static void main(String[] args) {
        launch(args);
    }

    public void loginStage(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("login-view.fxml"));

        VBox loginVBox = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setMain(this);

        Scene scene = new Scene(loginVBox);

        primaryStage.setTitle("Social Network");
        primaryStage.setScene(scene);

        loginController.setService(serv, primaryStage);

        primaryStage.show();
    }



    public void openUserStage(Utilizator user) {
        try {
        FXMLLoader userLoader = new FXMLLoader();
        userLoader.setLocation(getClass().getResource("user-view.fxml"));


        Stage userStage = new Stage();
        Scene userScene = null;
        userScene = new Scene(userLoader.load());


        userStage.setTitle("Social Network");
        userStage.setScene(userScene);

        UserController userController = userLoader.getController();
        userController.setService(user, serv, cerereService, prietenieService, conversationService, userStage);

        userStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        loginStage(primaryStage);
    }

}
