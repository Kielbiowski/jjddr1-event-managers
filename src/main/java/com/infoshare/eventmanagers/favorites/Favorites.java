package com.infoshare.eventmanagers.favorites;

import com.infoshare.eventmanagers.Menu;
import com.infoshare.eventmanagers.model.Event;
import com.infoshare.eventmanagers.repository.Repository;
import com.infoshare.eventmanagers.utils.Utils;
import jdk.jshell.execution.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Favorites {

    private final static Logger LOGGER = LogManager.getLogger(Menu.class);

    /**
     * Declaration and initialization of List of Favorites Events.
     */
    List<Event> favoritesList = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    /**
     * Declaration and initialization of String Array, which include available options
     * in Menu of Favorites.
     */
    String[] menuListFavorites = {"Wyświetl listę ulubionych wydarzeń", "Dodaj wydarzenie do listy ulubionych",
            "Usuń wydarzenie z listy ulubionych" };

    /**
     * Shows Menu of Favorites Events with all options in switch instruction, which could be
     * chosen by input in Scanner.
     */
    public void showFavoriteMenu() {
        LOGGER.info("WITAMY W MENU ULUBIONYCH!!!\n");
        printFavoriteMenu();
        boolean next = true;
        while (next) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    LOGGER.info("Wybrano opcję: " + menuListFavorites[0] + "\n");
                    viewFavorites();
                    showFavoriteMenu();
                    break;
                case 2:
                    LOGGER.info("Wybrano opcję: " + menuListFavorites[1] + "\n");
                    addToFavorites();
                    showFavoriteMenu();
                    break;
                case 3:
                    LOGGER.info("Wybrano opcję: " + menuListFavorites[2] + "\n");
                    deleteFromFavorites();
                    showFavoriteMenu();
                    break;
                case 4:
                    next = false;
                    break;
                default:
                    LOGGER.info("Brak takiej opcji \n");
            }
        }
    }


    /**
     * Prints Menu of Favorites Events.
     */
    private void printFavoriteMenu() {
        Utils.printLine();
        Utils.printMenu(menuListFavorites);
    }

    /**
     * Searches an event by ID in eventList and add this event to favoritesList.
     */
    void addToFavorites() {

        for (Event e : Repository.eventList) {
            e.printAsElement();
        }

        LOGGER.info("Write ID number of an event you want to add: \n");
        int index = scanner.nextInt();

        List<Event> eventList = Repository.eventList
                .stream()
                .filter(e -> e.getId() == index)
                .collect(Collectors.toList());

        if (eventList.isEmpty()) {
            LOGGER.info("No such element in the list!\n");
        } else {
            favoritesList.add(eventList.get(0));
            LOGGER.info("Event added to your List of Favourites.\n");
        }
        printFavoriteMenu();
        saveToRepository();
    }

    /**
     * Searches an event by ID in favoritesList and deletes this event.
     */
    void deleteFromFavorites() {

        viewFavorites();
        LOGGER.info("Write ID number of an event you want to delete: \n");
        int index = scanner.nextInt();

        List<Event> elementsToDelete = Repository.eventList
                .stream()
                .filter(e -> e.getId() == index)
                .collect(Collectors.toList());

        if (elementsToDelete.isEmpty()) {
            LOGGER.info("No such element in the list!\n");
        } else {
            favoritesList.remove(elementsToDelete);
            LOGGER.info("Event deleted form List of Favorites.\n");
        }
        printFavoriteMenu();
        saveToRepository();
    }

    /**
     * Prints every event in favoritesList
     */
    void viewFavorites() {
        for (Event event : favoritesList) {
            event.printAsElement();
        }
    }

    /**
     * Updates favoriteslist in Repository class.
     */
    public void saveToRepository() {
        Repository.favoritesList = favoritesList;
    }

}