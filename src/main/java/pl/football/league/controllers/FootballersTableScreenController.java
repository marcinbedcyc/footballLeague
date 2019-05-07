package pl.football.league.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pl.football.league.entities.Fan;
import pl.football.league.entities.Footballer;
import pl.football.league.entities.Team;
import pl.football.league.fxmlUtils.TableControls;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FootballersTableScreenController {
    private EntityManager entityManager;
    private MainScreenController mainController;
    private Fan currentUser;
    private List<Footballer> footballers;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label teamLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    void initialize() {
        setSorts();
        fillTable();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setMainController(MainScreenController mainController) {
        this.mainController = mainController;
    }

    public void setCurrentUser(Fan currentUser) {
        this.currentUser = currentUser;
    }

    private void tryLoader(FXMLLoader loader){
        try {
            Parent root = loader.load();
            mainController.getBorderPane().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(){
        int i=0;
        gridPane.getChildren().remove(0, gridPane.getChildren().size());

        for (Footballer f : footballers) {
            Label name = TableControls.LabelVGrow(150, f.getName());
            gridPane.setHgrow(name, Priority.ALWAYS);
            name.setOnMouseClicked(event->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/footballerScreen.fxml"));
                FootballerScreenController footballerScreenController = new FootballerScreenController();
                Footballer footballer1 = entityManager.find(Footballer.class, f.getFootballerID());

                footballerScreenController.setDependency(footballer1, currentUser, entityManager, mainController);
                loader.setController(footballerScreenController);
                tryLoader(loader);
            });

            Label surname = TableControls.LabelVGrow(150, f.getSurname());
            gridPane.setHgrow(surname, Priority.ALWAYS);
            surname.setOnMouseClicked(name.getOnMouseClicked());

            Label teamLabel = TableControls.LabelVGrow(150, f.getTeam().getName());
            gridPane.setHgrow(teamLabel, Priority.ALWAYS);
            teamLabel.setOnMouseClicked(event ->{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/teamScreen.fxml"));
                TeamScreenController teamScreenController = new TeamScreenController();
                Team team = entityManager.find(Team.class, f.getTeam().getTeamID());

                teamScreenController.setDependencies(team, entityManager, mainController, currentUser);
                loader.setController(teamScreenController);
                tryLoader(loader);
            });

            Label number = TableControls.Label(50, String.valueOf(f.getNumber()));

            Label position = TableControls.Label(60, f.getPosition());
            gridPane.addRow(i, name, surname, teamLabel, number, position );
            i++;
        }

    }

    private void setSorts() {
        footballers = entityManager.createQuery("select F from Footballer F").getResultList();
        footballers.sort(Comparator.comparing(Footballer::getSurname));

        surnameLabel.setOnMouseClicked(event -> {
            footballers.sort(Comparator.comparing(Footballer::getSurname));
            fillTable();
        });

        nameLabel.setOnMouseClicked(event -> {
            footballers.sort(Comparator.comparing(Footballer::getName));
            fillTable();
        });

        teamLabel.setOnMouseClicked(event->{
            footballers.sort((Footballer f1, Footballer f2) -> {
                return f1.getTeam().getName().compareTo(f2.getTeam().getName());
            });
            fillTable();
        });

        positionLabel.setOnMouseClicked(event -> {
            footballers.sort(Comparator.comparing(Footballer::getPosition));
            fillTable();
        });

        numberLabel.setOnMouseClicked(event -> {
            footballers.sort(Comparator.comparing(Footballer::getNumber));
            fillTable();
        });
    }
}