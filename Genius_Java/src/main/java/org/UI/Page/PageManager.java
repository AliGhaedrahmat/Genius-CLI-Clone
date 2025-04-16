package org.UI.Page;

import org.UI.Console.Console;

public class PageManager {
    public static void runPage(Page page) {
        Console.clear();
        Console.print("\n\n");
        Console.print("Welcome to " + page.name + "\n\n" , Console.Color.LIGHT_GREEN);
        page.displayPage();
    }
}
