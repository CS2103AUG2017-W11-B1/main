package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.ReadOnlyTask;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class XmlTaskDataStorage implements TaskStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlTaskDataStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTaskDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTask> readTaskData() throws DataConversionException, IOException {
        return readTaskData(filePath);
    }

    @Override
    public Optional<ReadOnlyTask> readTaskData(String filePath) throws DataConversionException, IOException {

        requireNonNull(filePath);

        File taskDataFile = new File(filePath);

        if (!taskDataFile.exists()) {
            logger.info("TaskData file "  + taskDataFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTask taskDataOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);

    }

    @Override
    public void saveTaskData(ReadOnlyTask taskData) throws IOException {

    }

    @Override
    public void saveTaskData(ReadOnlyTask taskData, String filePath) throws IOException {

    }

    @Override
    public void backupTaskData(ReadOnlyTask taskData) throws IOException {

    }
}
