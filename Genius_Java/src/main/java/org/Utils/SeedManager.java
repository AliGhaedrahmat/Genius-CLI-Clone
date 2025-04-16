package org.Utils;

import org.Model.*;
import org.Services.AccountManager;
import org.Services.AlbumManager;
import org.Services.SongManager;

import java.util.*;

public class SeedManager {

    public static boolean checkIfSeedExists() {
        AccountManager accountManager = new AccountManager();
        List <Account> artists = accountManager.getAccountsByRole(AccountRole.ARTIST);
        return !artists.isEmpty();
    }

    public static void seedDatabase() {
        AccountManager accountManager = new AccountManager();
        SongManager songManager = new SongManager();
        AlbumManager albumManager = new AlbumManager();

        // Admin
        accountManager.add(new Admin("admin" , "admin" , "admin" , "This is admin"));

        // === Artist: Slipknot ===
        Artist slipknot = new Artist("slipknot", "pass123", "Slipknot", "Heavy metal band from Iowa.", Genre.METAL);
        accountManager.add(slipknot);

        List<Song> slipknotSongs = List.of(
                new Song(slipknot, "Duality", Genre.METAL, "I push my fingers into my eyes..."),
                new Song(slipknot, "Before I Forget", Genre.METAL, "Go!"),
                new Song(slipknot, "Psychosocial", Genre.METAL, "I did my time and I want out..."),
                new Song(slipknot, "Snuff", Genre.ROCK, "Bury all your secrets in my skin..."),
                new Song(slipknot, "The Devil In I", Genre.METAL, "Undo these chains, my friend..."),
                new Song(slipknot, "Wait and Bleed", Genre.METAL, "I've felt the hate rise up in me..."),
                new Song(slipknot, "Nero Forte", Genre.METAL, "I'm never enough..."),
                new Song(slipknot, "Unsainted", Genre.METAL, "Oh, I'll never kill myself to save my soul...")
        );
        slipknotSongs.forEach(songManager::add);

        Album allHopeIsGone = new Album("All Hope Is Gone", Genre.METAL, slipknot);
        allHopeIsGone.getSongs().add(slipknotSongs.get(0).getId()); // Duality
        allHopeIsGone.getSongs().add(slipknotSongs.get(1).getId()); // Before I Forget
        albumManager.add(allHopeIsGone);

        // === Artist: Drake ===
        Artist drake = new Artist("drake", "pass123", "Drake", "Canadian rapper and singer.", Genre.RAP);
        accountManager.add(drake);

        List<Song> drakeSongs = List.of(
                new Song(drake, "God's Plan", Genre.RAP, "I hold back, sometimes I won't..."),
                new Song(drake, "In My Feelings", Genre.RAP, "Kiki, do you love me?..."),
                new Song(drake, "Hotline Bling", Genre.RAP, "You used to call me on my cell phone..."),
                new Song(drake, "One Dance", Genre.RAP, "Baby, I like your style..."),
                new Song(drake, "Started From The Bottom", Genre.RAP, "Started from the bottom now we here..."),
                new Song(drake, "Nonstop", Genre.RAP, "Look, I just flipped the switch..."),
                new Song(drake, "Laugh Now Cry Later", Genre.RAP, "Sometimes we laugh, sometimes we cry..."),
                new Song(drake, "Headlines", Genre.RAP, "I might be too strung out on compliments..."),
                new Song(drake, "Nice for What", Genre.RAP, "That's a real one in your reflection...")
        );
        drakeSongs.forEach(songManager::add);

        Album scorpion = new Album("Scorpion", Genre.RAP, drake);
        scorpion.getSongs().add(drakeSongs.get(0).getId()); // God's Plan
        scorpion.getSongs().add(drakeSongs.get(1).getId()); // In My Feelings
        scorpion.getSongs().add(drakeSongs.get(2).getId()); // Hotline Bling
        albumManager.add(scorpion);


        // === Artist: Eminem ===
        Artist eminem = new Artist("eminem", "pass123", "Eminem", "One of the greatest rappers alive.", Genre.RAP);
        accountManager.add(eminem);

        List<Song> eminemSongs = List.of(
                new Song(eminem, "Lose Yourself", Genre.RAP, "His palms are sweaty..."),
                new Song(eminem, "Rap God", Genre.RAP, "I'm beginning to feel like a Rap God..."),
                new Song(eminem, "The Real Slim Shady", Genre.RAP, "Will the real Slim Shady please stand up..."),
                new Song(eminem, "Mockingbird", Genre.RAP, "Hush little baby, don't you cry..."),
                new Song(eminem, "Not Afraid", Genre.RAP, "I'm not afraid to take a stand..."),
                new Song(eminem, "Love The Way You Lie", Genre.RAP, "Just gonna stand there and watch me burn..."),
                new Song(eminem, "Without Me", Genre.RAP, "Guess who's back..."),
                new Song(eminem, "Stan", Genre.RAP, "Dear Slim, I wrote you but you still ain't callin'..."),
                new Song(eminem, "Godzilla", Genre.RAP, "I can swallow a bottle of alcohol and I'll feel like Godzilla...")
        );
        eminemSongs.forEach(songManager::add);

        Album mmlp2 = new Album("The Marshall Mathers LP 2", Genre.RAP, eminem);
        mmlp2.getSongs().add(eminemSongs.get(0).getId()); // Lose Yourself
        mmlp2.getSongs().add(eminemSongs.get(1).getId()); // Rap God
        albumManager.add(mmlp2);

        Album relapse = new Album("Relapse", Genre.RAP, eminem);
        relapse.getSongs().add(eminemSongs.get(7).getId()); // Stan
        albumManager.add(relapse);

        // === Artist: Adele ===
        Artist adele = new Artist("adele", "pass123", "Adele", "British singer-songwriter.", Genre.POP);
        accountManager.add(adele);

        List<Song> adeleSongs = List.of(
                new Song(adele, "Hello", Genre.POP, "Hello, it's me..."),
                new Song(adele, "Rolling in the Deep", Genre.POP, "We could have had it all..."),
                new Song(adele, "Someone Like You", Genre.POP, "Never mind, I'll find someone like you..."),
                new Song(adele, "Set Fire to the Rain", Genre.POP, "But I set fire to the rain..."),
                new Song(adele, "Skyfall", Genre.POP, "This is the end..."),
                new Song(adele, "Easy On Me", Genre.POP, "Go easy on me, baby..."),
                new Song(adele, "When We Were Young", Genre.POP, "Let me photograph you in this light..."),
                new Song(adele, "Send My Love", Genre.POP, "Send my love to your new lover...")
        );
        adeleSongs.forEach(songManager::add);

        // No album for Adele (0 albums)

        // === Artist: Imagine Dragons ===
        Artist imagineDragons = new Artist("imagine_dragons", "pass123", "Imagine Dragons", "Pop rock band from Las Vegas.", Genre.ROCK);
        accountManager.add(imagineDragons);

        List<Song> idSongs = List.of(
                new Song(imagineDragons, "Believer", Genre.ROCK, "First things first..."),
                new Song(imagineDragons, "Radioactive", Genre.ROCK, "I'm waking up..."),
                new Song(imagineDragons, "Demons", Genre.ROCK, "When the days are cold..."),
                new Song(imagineDragons, "Thunder", Genre.ROCK, "Just a young gun..."),
                new Song(imagineDragons, "Whatever It Takes", Genre.ROCK, "Falling too fast to prepare for this..."),
                new Song(imagineDragons, "Natural", Genre.ROCK, "Will you hold the line..."),
                new Song(imagineDragons, "Enemy", Genre.ROCK, "Look out for yourself..."),
                new Song(imagineDragons, "Bones", Genre.ROCK, "Gimme, gimme, gimme some time to think..."),
                new Song(imagineDragons, "It's Time", Genre.ROCK, "So this is what you meant...")
        );
        idSongs.forEach(songManager::add);

        Album evolve = new Album("Evolve", Genre.ROCK, imagineDragons);
        evolve.getSongs().add(idSongs.get(0).getId()); // Believer
        evolve.getSongs().add(idSongs.get(1).getId()); // Radioactive
        evolve.getSongs().add(idSongs.get(2).getId()); // Demons

        albumManager.add(evolve);
    }
}
