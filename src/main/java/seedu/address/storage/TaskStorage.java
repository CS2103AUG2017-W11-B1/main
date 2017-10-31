package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.ReadOnlyTask;

import java.io.IOException;
import java.util.Optional;

public interface TaskStorage {
    String getTaskDataFilePath();

    Optional<ReadOnlyTask> readTaskData() throws DataConversionException, IOException;

    Optional<ReadOnlyTask> readTaskData(String filePath) throws DataConversionException, IOException;

    void saveTaskData(ReadOnlyTask taskData) throws IOException;

    void saveTaskData(ReadOnlyTask taskData, String filePath) throws IOException;

    void backupTaskData(ReadOnlyTask taskData) throws IOException;

}

