package com.example.demo4;

import com.example.demo4.entities.plats;
import com.example.demo4.services.platsService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class StatisticsController implements Initializable {

    @FXML
    private PieChart StatsChart;

    private final platsService platsService = new platsService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayStatistics();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayStatistics() throws SQLException {
        List<plats> allPlats = platsService.recupererplats();

        Map<String, Long> platsByType = allPlats.stream()
                .collect(Collectors.groupingBy(plats::getType_plat, Collectors.counting()));

        Map<String, Double> prixByType = new HashMap<>();
        prixByType.put("Plats", platsByType.getOrDefault("Plats", 0L) * 250 * 0.6);
        prixByType.put("Plats de grillades", platsByType.getOrDefault("Plats de grillades", 0L) * 150 * 0.75);
        prixByType.put("Plats de pâtes", platsByType.getOrDefault("Plats de pâtes", 0L) * 100 * 0.9);

        List<PieChart.Data> pieChartData = prixByType.keySet().stream()
                .map(type -> new PieChart.Data(type + " (" + Math.round(prixByType.get(type)) + " DT)", prixByType.get(type)))
                .collect(Collectors.toList());

        StatsChart.setData(FXCollections.observableArrayList(pieChartData));

        StatsChart.getData().forEach(data -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), data.getNode());
                st.setToX(1.1);
                st.setToY(1.1);
                st.play();
            });

            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), data.getNode());
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });

            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                double currentValue = data.getPieValue();
                long nbPlats = platsByType.getOrDefault(data.getName().split(" ")[0], 0L);
                double totalWithDiscount = calculateTotalWithDiscount(currentValue, data.getName(), nbPlats);
                String reduction = calculateReduction(data.getName().split(" ")[0], nbPlats);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Statistique Plat(s)");
                alert.setHeaderText(data.getName());
                alert.setContentText("Nombre de plats : " + nbPlats + "\n"
                        + "Prix total : " + Math.round(currentValue) * nbPlats + " DT\n"
                        + "Réduction : " + reduction + "\n"
                        + "Prix total avec réduction pour " + nbPlats + " plats : " + Math.round(totalWithDiscount) + " DT");
                alert.showAndWait();
            });
        });
    }

    private double calculateTotalWithDiscount(double price, String platType, long nbPlats) {
        double discount = getDiscountPercentage(platType, nbPlats);
        double discountedPrice = price - (price * discount);
        return discountedPrice * nbPlats;
    }

    private double getDiscountPercentage(String platType, long nbPlats) {
        double discount = 0.0;
        switch (platType) {
            case "Plats de pâtes":
                discount = 0.1;
                break;
            case "Plats de grillades":
                discount = 0.25;
                break;
            case "Plats de cuisine asiatique":
                discount = 0.4;
                if (nbPlats % 3 >= 1) {
                    discount += 0.01;
                }
                break;
        }
        return discount;
    }

    private String calculateReduction(String platType, long nbPlats) {
        double discount = getDiscountPercentage(platType, nbPlats) * 100;
        return String.format("%.0f", discount) + "%";
    }
}
