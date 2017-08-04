package by.mozgo.craps.command;

public class ActionResult {
    private ActionType type;
    private String page;

    public ActionResult(ActionType type, String content) {
        this.type = type;
        this.page = content;
    }

    public ActionType getType() {
        return type;
    }

    public String getPage() {
        return page;
    }

    public enum ActionType {
        FORWARD, REDIRECT
    }
}
