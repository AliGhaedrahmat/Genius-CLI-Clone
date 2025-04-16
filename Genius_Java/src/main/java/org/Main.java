package org;

import org.UI.Page.HomePage;
import org.UI.Page.PageManager;
import org.Utils.SeedManager;

public class Main {
    public static void main(String[] args) {
        if (!SeedManager.checkIfSeedExists()) {
            SeedManager.seedDatabase();
        }
        PageManager.runPage(new HomePage());
    }
}
