package org.UI.Page;

import org.UI.Console.Console;

public abstract class Page {
    String name;

    public abstract void displayPage();

    public final void Continue(){
        Console.getInput("\n    Press Enter to continue ... ");
    }

}
