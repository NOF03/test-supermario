package org.grupogjl.model.game.elements.menu;

import org.grupogjl.Game;
import org.grupogjl.model.game.elements.buttons.Button;

import java.util.Vector;

public abstract class MenuPauseModel {
    protected Vector<Button> buttons;
    protected boolean selectedOption = false;
    protected String textOption;
    private Byte selectedButton = 0;
    public boolean isSelectedOption() {
        return selectedOption;
    }
    public void setSelectedOption(boolean selectedOption) {
        this.selectedOption = selectedOption;
    }
    public String getTextOption() {
        return textOption;
    }
    public void setTextOption(String textOption) {
        this.textOption = textOption;
    }
    public Vector<Button> getButtons() {
        return buttons;
    }
    public Byte getSelectedButton() {
        return selectedButton;
    }
    public void nextPosition() {
        if (selectedButton + 1 < buttons.size()) selectedButton++;
    }
    public void lastPosition() {
        if (selectedButton - 1 >= 0) selectedButton--;
    }
    public void execute(Game game) {
        buttons.elementAt(selectedButton).OnPressed(game);
    }
}
