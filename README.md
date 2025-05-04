# IntuLearn

**IntuLearn** is an Android app aimed at teaching and demonstrating key algorithms, such as sorting and searching algorithms. The app presents these algorithms through interactive lessons, allowing users to learn by exploring various unit modules.

### Key Features

* **Algorithm Units:** The app includes multiple algorithm units, such as:

  * **Linear Search**
  * **Binary Search**
  * **Bubble Sort**
  * **Insertion Sort**
  * **Selection Sort**

* **Skill Level Indicators:** Each unit has a skill level associated with it (Basic, Moderate, Advanced) to help users choose the appropriate difficulty.

* **Search Functionality:** Users can search for units based on names, authors, or categories.

* **Settings Customization:** Users can adjust UI speed in the settings (Fast, Medium, or Slow).

* **User Guide and About Section:** Includes a user guide and about section to help new users understand how to use the app.

* **Share Functionality:** Users can share the app or content with others using Android’s built-in share functionality.

* **Offline Support:** The app provides offline access to various resources, including the user guide.

## Screenshots

Add screenshots here that demonstrate the main app interface, list view of algorithm units, and settings page.

---

## Installation

### Prerequisites

* **Android Studio** (latest stable version)
* **Android SDK** (installed via Android Studio)

### Steps to Install

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mihirgokani007/intulearn.git
   ```

2. **Open the project in Android Studio:**

   Launch Android Studio and select **Open an existing project**, then navigate to the folder where you cloned the repo.

3. **Install dependencies:**

   Android Studio will automatically download any necessary dependencies once the project is opened.

4. **Build the app:**

   Click on **Build** > **Make Project** to ensure everything compiles correctly.

5. **Run the app on an emulator or physical device:**

   Connect your Android device via USB or use an emulator, then click **Run** in Android Studio.

---

## App Structure

### `MainActivity.java`

`MainActivity` is the entry point of the app and displays a list of available algorithm units. The key features include:

* **List of Units:**

  * Displays algorithm names, authors, and skill levels (Basic, Moderate, Advanced).
  * Includes a search feature to filter algorithm units by name, author, or category.

* **Unit Click Action:**

  * Clicking on an algorithm unit opens the `DetailsActivity`, where the user can explore more about the algorithm.

* **Menu Options:**

  * **Settings:** Opens the `SettingsActivity` for UI speed customization.
  * **User Guide:** Displays a local HTML guide to help users navigate through the app.
  * **About:** Shows information about the app and its creator.
  * **Share:** Allows users to share the app via text.

### `SettingsActivity.java`

`SettingsActivity` provides options to modify app settings, particularly the **UI Speed** preference. The settings are stored using Android’s `SharedPreferences` system.

* **UI Speed Preference:**

  * Users can select from three options: **Fast**, **Medium**, or **Slow**.
  * The setting impacts the overall UI speed for smoother interactions.

---

## Usage

1. **Launch the app:** Upon opening, you will be presented with a list of algorithm units.
2. **Search for units:** Use the search bar in the top-right corner to filter units by name, author, or category.
3. **Explore units:** Click on a unit to see more details and learn how the algorithm works.
4. **Change settings:** Open the settings to customize the app's UI speed.
5. **Access the user guide and about section:** Use the respective menu options for further guidance.

---

## Technologies Used

* **Language:** Kotlin / Java (depending on your implementation)
* **Libraries:**

  * **Android SDK** for UI and app components
  * **Android Preferences** for saving UI speed settings
  * **SearchView** for searching and filtering units
* **Database:** Internal app data (no external database)

---

## Contributing

We welcome contributions to improve IntuLearn! If you'd like to contribute, follow these steps:

1. Fork the repository.
2. Create a new branch for your changes.
3. Implement your changes and test them locally.
4. Commit your changes with descriptive messages.
5. Push your changes and submit a pull request.

Please follow the coding guidelines and code of conduct.

---

## License

This project is licensed under the GNU General Public License v2.0 - see the [LICENSE](LICENSE) file for details.

---

## Acknowledgements

* **Mihir Gokani** - Creator and Original Author
* **Android SDK** - for providing the base framework to build the app

---

### Adding Icons and Screenshots

* The app uses icons such as `ic_atom_1`, `ic_atom_2`, and `ic_atom_3` to indicate the different skill levels. You can find them in the `res/drawable` directory.
* Feel free to add more screenshots or diagrams in the `README` to showcase how the app looks in action.
