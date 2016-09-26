package at.aictopic1.sentimentanalysis.preprocessor;

import at.aictopic1.sentimentanalysis.preprocessor.interfaces.ITask;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract preprocessor class with language and tasks list
 */
public class AbstractPreprocessor {
    private String language;
    private List<ITask> tasks;

    public AbstractPreprocessor(String language){
        this.language = language;
    }

    public String getLanguage() {return this.language;}
    public void setLanguage(String language) { this.language = language; }

    public List<ITask> getTasks() { return this.tasks; }
    public void setTasks(List<ITask> tasks) { this.tasks = tasks; }
    public void addTask(ITask task) {
        if(tasks == null)
            this.tasks = new ArrayList<ITask>();

        this.tasks.add(task);
    }
}
