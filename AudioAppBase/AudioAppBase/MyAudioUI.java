// Name: Kirsten Sotelo
// Student ID: 501169612
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.management.RuntimeErrorException;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore("store.txt");
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		try{
			while (scanner.hasNextLine())
			{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
					// Gets a start index and end index form user
					int startIndex = 0;
					int endIndex = 0;
					
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						startIndex = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}

					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt())
					{
						endIndex = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}

					// iterates from start index to start index, and uses the store.getContent(index) method to download the indexes, otherwise throw exceptions if its already downloaded or if the index is not valid
					for (int i = startIndex; i <= endIndex; i++){
						try{
							mylibrary.download(store.getContent(i));
						} catch (AlreadyDownloadedException	e){
							System.out.println(e.getMessage());
						} catch (NullPointerException e){
							System.out.println(e.getMessage());
						}
					}
			}

			else if (action.equalsIgnoreCase("DOWNLOADA")) 
			{
				String artist = "";
				
				// Gets a user input for artist, and gets the artists' indices arraylist and downloads the indices, otherwise catch the already downloaded exception or artist not found exception
				System.out.print("Artist name: ");
				if (scanner.hasNextLine())
				{
					artist = scanner.nextLine();
				}

				try{
					ArrayList<Integer> indices = store.getArtistIndices(artist);
					for(int i : indices){
						try{
							mylibrary.download(store.getContent(i));
						} catch (AlreadyDownloadedException e){
							System.out.println(e.getMessage());
						}
					}
				} catch (ArtistNotFoundException e) {
					System.out.println(e.getMessage());
				}				
			}

			else if (action.equalsIgnoreCase("DOWNLOADG")) 
			{
				String genre = "";
				
				// Gets the user input of genre, then gets the indices of said genre and downloads based on the indices arraylist
				System.out.print("Genre: ");
				if (scanner.hasNextLine())
				{
					genre = scanner.nextLine();
				}

				ArrayList<Integer> indices = store.getGenreIndices(genre);
				for(int i : indices){
					try{
						mylibrary.download(store.getContent(i));
					} catch(AlreadyDownloadedException e){
						System.out.println(e.getMessage());
					}
				}
									
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Getting index from user input, then checking if the content is in mylibrary, playing it from mylibrary, if not then return false
				int index = 0;
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); 
				}
				
				mylibrary.playSong(index);											
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				// Getting index from user input, then checking if the content is in mylibrary, then printing it from mylibrary, if not then return false
				int index = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the book doesn't exist in the library
				try{
					mylibrary.printAudioBookTOC(index);
				} catch (IndexOutOfBoundsException e){
					System.out.println(e.getMessage());
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				// getting index and chapter from user input, then playing it if they are both valid inputs.
				int index = 0;
				int chapter = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine();
				}
				// Print error message if the book doesn't exist in the library
				try{
					mylibrary.playAudioBook(index, chapter);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				// getting title from user input, if it exists in mylibrary, play the playlist
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				// Print error message if the playlist doesn't exist in the library
				try{
					mylibrary.playPlaylist(title);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				// getting title and content # from user input, if they are valid, use play(title,index) method, if not, print error msg.
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				int index = 0;
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				try{
					mylibrary.playPlaylist(title);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				// getting index from user input, if its valid, delete the song from library and all playlists, if index is not valid, print error msg.
				int index = 0;
				System.out.print("Library Song #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				
				// Print error message if the book doesn't exist in the library
				try{
					mylibrary.deleteSong(index);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				// making a new playlist with user input of the title. If it already exists, print error msg.
				String title = "";
				
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}
				
				// Print error message if a playlist with the same title already exists in the library.
				try{
					mylibrary.makePlaylist(title);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))
			{
				// getting playlist title from user input, if the title is in mylibrary, then print the playlist, if not, print error msg.
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				// Print error message if the playlist does not exist in mylibrary
				mylibrary.printPlaylist(title);
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				// getting the playlist title, the type of the audiocontent, and index from user input. If the playlist exists, the content type exists, and index is valid, add it to playlist
				String playlist = "";
				String conType = "";
				int index = 0;

	
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					playlist = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNext())
				{
					conType = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Library Content #: ");
				if (scanner.hasNext())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// If either the content type does not exist or play list does not exist, print error message
				try{
					AudioContent content = store.getContent(index);
					if (content == null)
						System.out.println("Content Not Found in Library");
					else
						mylibrary.addContentToPlaylist(conType, index, playlist);
				} catch (RuntimeException e){
					System.out.println(e.getMessage());
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				// getting playlist title and index from user input. If they are all valid, delete content from playlist 
				String title = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext())
				{
					title = scanner.next();
					scanner.nextLine();
				}

				int index = 0;
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if either the index is not valid or play list doesnt exist.
				try{
					mylibrary.delContentFromPlaylist(index, title);
				} catch (RuntimeErrorException e){
					System.out.println(e.getMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			// ASSIGNMENT 2 ACTIONS
			
			else if (action.equalsIgnoreCase("SEARCH"))
			{
				// getting audiocontent title from user input, then using the search function from AudioContentStore, otherwise catch the TitleNotFoundException
				String title = "";
				System.out.print("Title: ");
				if (scanner.hasNext())
				{
					title = scanner.nextLine();
				}
				try {
					store.search(title);
				} catch (TitleNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("SEARCHA"))
			{
				// getting artist title from user input, then using the searchArtist from AudioContentStore, otherwise catch the ArtistNotFoundException method
				String artist = "";
				System.out.print("Artist: ");
				if (scanner.hasNext())
				{
					artist = scanner.nextLine();
				}
				try {
					store.searchArtist(artist);
				} catch (ArtistNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SEARCHG"))
			{
				// getting the genre from user input, then using searchGenre method from AudioContentStore
				String genre = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if (scanner.hasNext())
				{
					genre = scanner.nextLine();
				}
				try {
					store.searchGenre(genre);
				} catch (GenreNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			System.out.print("\n>");
			}
		
		} catch (AudioContentNotFoundException e){
			System.out.println(e.getMessage());
		} catch (RuntimeException e){
			System.out.println(e.getMessage());
		}
	}
}
