package seedu.address.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * The Task Panel of the App.
 */
public class TaskView extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";
    private static final Logger logger = LogsCenter.getLogger(TaskView.class);
    private static final String FXML = "TaskView.fxml";

    @FXML
    private ListView listView;

    @FXML
    private Button addTaskButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField taskString;

    public TaskView() {
        super(FXML);
        datePicker.setValue(LocalDate.now());
        //We will reuse this code later on in our project to fill in the list
        /*ObservableList<String> list = FXCollections.observableArrayList();
        list.add("first task");
        listView.getItems().addAll(list);*/
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
