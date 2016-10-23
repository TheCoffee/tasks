package in.ghostcode.tasks;

/**
 * Created by Coffee on 10/16/16.
 */

public class Task {
    private int id;
    private String title, category;
    private Boolean status;

    public Task() {
    }

    public Task(String title, String category, Boolean status) {
        this.title = title;
        this.category = category;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
