# Spotistics 🎵  
**Discover your music listening habits with personalized Spotify statistics.**  

## Overview  
Spotistics is a web application that connects to your Spotify account to display your top tracks across three time periods:  
- **Last month**  
- **Last six months**  
- **All time**  

The application provides an interactive and visually appealing interface to explore your favorite songs and artists.  

## Features  
- **Spotify Integration**: Securely authenticate with your Spotify account.  
- **Dynamic Time Ranges**: View your top tracks for different periods.  
- **User-Friendly Design**: Responsive and interactive layout built with HTML, CSS, and JavaScript.  
- **Data Processing**: Python and Jinja2 templates for fetching and presenting data dynamically.  

---

## Demo  

### Pre-login page
![image](https://github.com/user-attachments/assets/a50b7cbc-becf-45a2-b7ab-1984f10a21da)
*Above: Example visualization of index.html*

![image](https://github.com/user-attachments/assets/98cad4fb-c666-4274-bff6-423f047fd67a)
*Above: Example visualization of top artists over time.*

---

## Tech Stack  
- **Backend**: Python (Flask)  
- **Frontend**: HTML, CSS, JavaScript  
- **Templates**: Jinja2 with custom filters for dynamic content rendering  
- **Spotify API**: Fetch user data (top tracks, artists, and more)  

---

## Installation and Setup  

### Prerequisites  
1. Python 3.x installed  
2. Spotify Developer Account ([Create here](https://developer.spotify.com/dashboard/))  
3. Flask installed  

### Steps  
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/spotistics.git
   cd spotistics```
2. Install dependencies:
   ```bash
   pip install -r requirements.txt
3. Set up Spotify API Credentials:
   ```bash
   - Create a new app in your Spotify Developer Dashboard.
   - Add a redirect URI (e.g., http://127.0.0.1:5000/callback).
   - Copy the Client ID and Client Secret into the main.py file:
       SPOTIFY_CLIENT_ID=your_client_id
       SPOTIFY_CLIENT_SECRET=your_client_secret
       SPOTIFY_REDIRECT_URI=http://127.0.0.1:5000/callback
4. Run the Application:
   ```bash
     python main.py
5. Open your browser and visit: http://127.0.0.1:5000

