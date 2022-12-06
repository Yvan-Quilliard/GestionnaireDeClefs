package fr.gestionnaire.gestionnairedeclefs.controller;

import fr.gestionnaire.gestionnairedeclefs.model.Clef;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainControllerTest extends TestCase {

    public void testOnClickBtnSearch() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpjfx", "tpjfx", "tpjfx");

        String search = "103756";

        List<Clef> listClefBase = new Clef().getAll(connection);
        List<Clef> sortListClef = new ArrayList<Clef>();

        List<Clef> listEqualsNumber = listClefBase.stream().filter(clef ->
                String.valueOf(clef.getNumber()).toLowerCase().startsWith(search.toLowerCase())).collect(Collectors.toList());
        List<Clef> listEqualsColor = listClefBase.stream().filter(clef ->
                clef.getColor().toLowerCase().startsWith(search.toLowerCase())).collect(Collectors.toList());
        List<Clef> listEqualsDescription = listClefBase.stream().filter(clef ->
                clef.getDescription().toLowerCase().startsWith(search.toLowerCase())).collect(Collectors.toList());

        sortListClef.addAll(0, listEqualsDescription);
        sortListClef.addAll(0, listEqualsColor);
        sortListClef.addAll(0, listEqualsNumber);

        List<Clef> listContainNumber = listClefBase.stream().filter(clef ->
                String.valueOf(clef.getNumber()).toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainNumber);


        List<Clef> listContainColor = listClefBase.stream().filter(clef ->
                clef.getColor().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainColor);


        List<Clef> listContainDescription = listClefBase.stream().filter(clef ->
                clef.getDescription().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        sortListClef.addAll(0, listContainDescription);


        List<Clef> listSortFinal = sortListClef.stream().distinct().collect(Collectors.toList());
        ObservableList<Clef> data = FXCollections.observableArrayList();
        listSortFinal.forEach(clef -> data.add(clef));

        Collections.reverse(data);

        assertEquals(103756, data.get(0).getNumber());
        assertEquals(1, data.size());
    }
}