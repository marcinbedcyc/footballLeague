package pl.football.league.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import pl.football.league.entities.Fan;

import javax.persistence.EntityManager;
import java.io.IOException;

public class MenuBarController {

    private MainScreenController mainController;
    private EntityManager entityManager;
    private Fan currentUser;

    @FXML
    private ToggleButton homeButton;

    @FXML
    private ToggleGroup menu;

    @FXML
    private ToggleButton leagueTableButton;

    @FXML
    private ToggleButton resultsButton;

    @FXML
    private ToggleButton teamsButton;

    @FXML
    private ToggleButton footballersButton;

    @FXML
    private ToggleButton coachesButton;

    @FXML
    private ToggleButton fansButton;

    @FXML
    private ToggleButton optionButton;

    @FXML
    private ToggleButton adminOptionButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label whoLogIn;

    @FXML
    void initialize() {
        whoLogIn.setText("Zalogowany jako " + currentUser.getNickname());
        if(!currentUser.getNickname().equals("admin")){
            adminOptionButton.setVisible(false);
        }
    }

    @FXML
    void tableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/tableScreen.fxml"));
        TableScreenController tableScreenController = new TableScreenController();
        tableScreenController.setCurrentUser(currentUser);
        tableScreenController.setEntityManager(entityManager);
        tableScreenController.setMainController(mainController);

        loader.setController(tableScreenController);
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void start(){
        tableOpen();
        homeButton.setSelected(true);
    }

    @FXML
    void footballersTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballersTableScreen.fxml"));
        FootballersTableScreenController footballersTableScreenController = new FootballersTableScreenController();
        footballersTableScreenController.setCurrentUser(currentUser);
        footballersTableScreenController.setEntityManager(entityManager);
        footballersTableScreenController.setMainController(mainController);

        loader.setController(footballersTableScreenController);
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    void coachesTableOpen(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/coachesTableScreen.fxml"));
        CoachesTableScreenController coachesTableScreenController = new CoachesTableScreenController();
        coachesTableScreenController.setCurrentUser(currentUser);
        coachesTableScreenController.setEntityManager(entityManager);
        coachesTableScreenController.setMainController(mainController);

        loader.setController(coachesTableScreenController);
        tryLoader(loader);
    }

    @FXML
    void teamsTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamsTable.fxml"));
        TeamsTableScreenController teamsTableScreenController = new TeamsTableScreenController();

        teamsTableScreenController.setDependencies(currentUser, entityManager, mainController);
        loader.setController(teamsTableScreenController);

        tryLoader(loader);
    }


    @FXML
    void logout() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScreen.fxml"));

        LoginScreenController loginScreenController = new LoginScreenController();
        loginScreenController.setEntityManager(entityManager);
        loginScreenController.setMainController(mainController);

        loader.setController(loginScreenController);
        Pane pane;
        try {
            pane = loader.load();
            mainController.getBorderPane().setCenter(pane);
            mainController.getBorderPane().setLeft(null);
            mainController.getStage().setWidth(1000);
            mainController.getStage().setHeight(600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fansTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/fansTableScreen.fxml"));
        FansScreenTableController fansScreenTableController = new FansScreenTableController();

        fansScreenTableController.setCurrentUser(currentUser);
        fansScreenTableController.setEntityManager(entityManager);
        fansScreenTableController.setMainController(mainController);

        loader.setController(fansScreenTableController);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainController.getBorderPane().setCenter(root);
    }

    @FXML
    void resultTableOpen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/resultTableScreen.fxml"));
        ResultTableScreenController resultTableScreenController = new ResultTableScreenController();
        resultTableScreenController.setCurrentUser(currentUser);
        resultTableScreenController.setEntityManager(entityManager);
        resultTableScreenController.setMainController(mainController);

        loader.setController(resultTableScreenController);
        tryLoader(loader);
    }

    @FXML
    void openOption(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/optionScreen.fxml"));
        OptionScreenController optionScreenController = new OptionScreenController();

        optionScreenController.setDependencies(entityManager, currentUser, mainController);
        loader.setController(optionScreenController);

        tryLoader(loader);
    }

    @FXML
    void openManagment(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/managmentScreen.fxml"));
        ManagementScreenController managementScreenController = new ManagementScreenController();

        managementScreenController.setEntityManager(entityManager);
        loader.setController(managementScreenController);
        tryLoader(loader);
    }


    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    public void setDependencies(MainScreenController mainScreenController, EntityManager entityManager, Fan currentUser){
        setMainController(mainScreenController);
        setEntityManager(entityManager);
        setCurrentUser(currentUser);
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
