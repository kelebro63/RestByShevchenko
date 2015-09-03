package ru.ID20.app.constants.enums;
import ru.ID20.app.R;

/**
 * Created by s.shevchenko on 26.03.2015.
 */
public enum  DrawerItems {

    TASKS(R.string.tv_button_tasks);

    int itemNameStringId;

    DrawerItems(int itemNameStringId) {
        this.itemNameStringId = itemNameStringId;
    }

    public int getItemNameStringId() {
        return itemNameStringId;
    }
}
