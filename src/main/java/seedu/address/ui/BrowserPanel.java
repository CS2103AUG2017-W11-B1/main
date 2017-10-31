package seedu.address.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_MAPS_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private TextArea taskDisplayed;


    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        displayTask();
        loadDefaultPage();
        displayOnTextArea();
        registerAsAnEventHandler(this);
    }

    private void displayTask() {
        //taskDisplayed.textProperty().bind(displayed);
        taskDisplayed.setStyle("-fx-font-family: monospace; -fx-background-color: #f8ecc2; -fx-font-size: 22px;");

    }


    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAPS_SEARCH_URL_PREFIX + person.getAddress().toString()
                .replaceAll(" ", "+").replaceAll(",", "%2C"));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    public TextArea getTaskDisplayed() {
        return taskDisplayed;
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    /**
     *  Read and Display the task on the Text Area
     */
    public void displayOnTextArea() {
        try {
            Writer output;
            String curr = System.getProperty("user.dir");
            output = new BufferedWriter(new FileWriter(curr + "/data/Hello.txt", true));
            output.append("\r\n");
            Scanner s = new Scanner(new File(curr + "/taskData1.txt")).useDelimiter
                    (Pattern.compile("\\r\\n"));

            while (s.hasNext()) {

                taskDisplayed.appendText(s.next() + "\n");

            }


        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }
}
