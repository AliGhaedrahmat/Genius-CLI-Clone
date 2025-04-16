# Genius CLI Music App

Genius CLI Music App is a command-line music management system built with Java and Gradle. It allows users to search for songs, artists, albums, and engage with music through actions like liking, commenting, and submitting edits. Artists can manage their profiles, create new songs and albums, and even review edits submitted for their content. Administrators have special controls over unapproved edits and user registration.



## Table of Contents
- [Features](#features)
- [Architecture and Project Structure](#architecture-and-project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features

- **File Based DataBase**
  - Save the changes and holds the data using file systems
  - Uses JSON for saving information (GSON for java)
  - This way no information is lost when the app is closed

- **Authentication System**
  - Can Sign up as different account roles
  - Can log in if signed up before
  - The application user is able to log in / log out without closing the app in realtime

- **Initial Seed Manager**
  - When running the program the database is seeded with initial data containing : 
    - Several Artists , Albums and Songs which are all linked
    - An Admin account (Username : admin , Password : admin)

- **User Accounts**
  - Create and authenticate accounts.
  - View Songs and Albums
  - Access to Advanced Search Page (Search with a query among all Songs , Artists and Albums)
  - Access to songs , artists and albums pages 
  - Writing comments and viewing song's comments (Sorted by date)
  - Viewing Song's lyrics and submiting edits to them
  - Like and Dislike songs
  - Viewing popular songs at the moment
  - Ability to follow / unfollow artists

- **Artist Accounts**
    - Create and authenticate artists.
    - Artist accounts can create, manage, and edit songs and albums.
    - Artist can approve or reject user submited edits on their lyrics
    - Can be followed by users
    - Can have a genre
    - View song's stats (Views , Likes , Dislikes , Comments)

- **Song Management**
    - Search songs by name or lyrics.
    - View song details, stats (views, likes, dislikes, comments), and lyrics.
    - Like, dislike, and comment on songs.
    - Submit edits for song lyrics (requires admin approval).

- **Album Management**
    - Create albums and add songs to them.
    - Search and view albums with associated artist information.
    - View popular songs based on play count.

- **Artist Profiles**
    - Manage an artist’s profile, including name, bio, and genre.
    - Follow/unfollow artists.
    - View an artist’s songs, albums, and popular tracks.

- **Administrative Controls**
    - Review and manage unapproved edits.
    - Manage the overall content of the application.

- **CLI Interface**
    - A clean, menu-driven interface using ANSI colors.
    - Universal “Back” and “Exit” options for smooth navigation.
    - Cross-platform functionality through Gradle.

- **Settings for users and artists**
  - Artist: Change name , bio and genre
  - User: Change name , bio

- **Search System**
  - Users are able to search through all the different entities with just one keyword
  - This way you can search through Songs , Artists and Albums all at the same time and fetch more accurate data

- **UUID usage**
  - Every entity is assigned with a UUID for better database and management 
  - When Entities Are Created , they are assigned with a random UUID that will be saved in the database 

## Architecture and Project Structure

- **Model Classes (org.Model):**  
  Contains definitions for core entities including `Account`, `Artist`, `User`, `Song`, `Album`, `Comment`, `Edit`, etc.

- **Services (org.Services):**  
  Contains manager classes (`AccountManager`, `SongManager`, `AlbumManager`, etc.) that handle business logic and data persistence using JSON as a lightweight database.

- **UI Pages (org.UI.Page):**  
  Contains CLI pages such as `HomePage`, `SearchPage`, `SongPageForUser`, `ArtistPage`, `AdminPanel`, and settings pages.

- **Console Utilities (org.UI.Console):**  
  Provides methods to print colored text and interact with the user via CLI.

- **Database Layer (org.database):**  
  Provides JSON-based file storage and custom deserialization (using a custom `JsonManager` with polymorphic support).

- **Build Configuration:**  
  Managed by Gradle, making it easy to build and run the application.

## Installation

### Prerequisites
- **Java JDK:** Version 11 or higher.
- **Git:** For cloning the repository.
- **Gradle Wrapper:** The project includes a Gradle wrapper; you don't need to install Gradle separately.

### Clone the Repository
Open your terminal and run:
```bash
git clone https://github.com/AliGhaedrahmat/Nowruz-Project.git
cd your_repo_to_(genius_java)
