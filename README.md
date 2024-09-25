# CONTACT MANAGER

In this project, I developed an Android application using the MVVM architecture, Retrofit for network calls, and LiveData for data observation. Let me walk you through the key aspects and functionality of the app:

### Introduction
This app fetches and displays content from a specified URL. The content is processed to remove images and then analyzed to extract specific textual information.

### Architecture
The app follows the MVVM (Model-View-ViewModel) architecture, ensuring a clean separation of concerns and facilitating easy maintenance and scalability.

### Key Components
1. **Retrofit Service**:
    - We use Retrofit to handle HTTP requests. The service fetches raw HTML content from the given URL.

2. **Repository**:
    - The repository acts as a single source of truth for data, handling the network call through Retrofit and processing the HTML content using Jsoup. It removes all images from the content and extracts the text, which is then provided to the ViewModel.

3. **ViewModel**:
    - The ViewModel holds and manages UI-related data in a lifecycle-conscious way. It fetches the website content via the repository and performs three main tasks:
        1. **Task 1**: Finds the 15th character in the text.
        2. **Task 2**: Extracts every 15th character from the text.
        3. **Task 3**: Counts the occurrences of each word in the text.

4. **UI Components**:
    - The UI is built using a RelativeLayout containing a Button to trigger the content load, and TextViews to display the raw content, the 15th character, every 15th character, and word count results.

### User Interaction
- When the user taps the "Load Content" button, the app fetches and processes the content from the specified URL.
- The processed content and analysis results are observed via LiveData and displayed in the TextViews.

### Strong Points
1. **MVVM Architecture**: This architecture promotes separation of concerns, making the app easier to test and maintain.
2. **Retrofit Integration**: Efficient and scalable network requests handling.
3. **Jsoup for HTML Parsing**: Robust parsing and manipulation of HTML content.
4. **LiveData**: Real-time updates to the UI as data changes, ensuring a responsive user experience.
5. **Scalability**: The modular design allows for easy addition of new features and services.

### Summary
This app demonstrates a clean and efficient way to fetch, process, and display web content in an Android application. By leveraging modern Android development practices such as MVVM, Retrofit, and LiveData, it ensures a robust, maintainable, and scalable codebase. The ability to analyze and display specific content details showcases the app's potential for handling complex data processing tasks, making it a strong candidate for real-world applications where web content manipulation is required.
