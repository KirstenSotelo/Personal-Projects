// Name: Kirsten Sotelo
// Student ID: 501169612
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */

 // This method is the same as the old Library.java file, but instead of the variable errorMsg, it uses throws and catches of exceptions.
public class Library extends RuntimeException
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) throws AlreadyDownloadedException, NullPointerException // TESTED AND WORKS
	{
		// Determening if the content is a song or audiobook, then through iterate 'songs' or 'audiobooks' ArrayLists to check if it is inside. If it is, (meaning it is already downloaded), then return false, otherwise add it and return true
		if (content.getType().equals(Song.TYPENAME)){
			for (Song i : songs){
				if (i.equals(content)){
					throw new AlreadyDownloadedException("SONG " + i.getTitle() + " already downloaded");
				}
			}
			System.out.println("SONG " + content.getTitle() + " Added to Library");
			songs.add((Song)content);

		} else if (content.getType().equals(AudioBook.TYPENAME)) {
			for (AudioBook i : audiobooks) {
				if (i.equals(content)){
					throw new AlreadyDownloadedException("AUDIOBOOK " + i.getTitle() + " already downloaded");
					}
				}
			System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
			audiobooks.add((AudioBook)content);
		} else {
			throw new NullPointerException("N/A");
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		// Iterating through all songs in ArrayList and printing using printInfo().
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		// Iterating through audiobooks in ArrayList and printing using printInfo().
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			// Iterating through playlists in ArrayList and printing the title.
			int index = i + 1;
			System.out.print("" + index + ". ");
			String title = playlists.get(i).getTitle();
			System.out.println(title);	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists() // TESTED AND WORKS
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names

		// Empty artists ArrayList, iterate through songs and get each artist, then if the artist is not in the 'artists' ArrayList, add it to artists, then print.
		ArrayList<String> artists = new ArrayList<String>(); 
		for (Song i : songs){
			String temp = i.getArtist();
			boolean inside = artists.contains(temp);
			if (!inside) {
				artists.add(temp);
			}
		}

		for (int i = 0; i < artists.size(); i++){
			System.out.println((i+1)+ ". " + artists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) throws RuntimeException // TESTED AND WORKS
	{
		// Next 3 lines checks if the index is greater than zero and less than the 'songs' size, if true, remove the song at said index
		if (index-1 >= 0 && index-1 < songs.size()){
			// saving the song to delete, in s
			Song s = songs.get(index-1);
			songs.remove(index-1);
			
			// Next 13 lines is for deleting the song in all existing playlists
			// Iterating through all audiocontent in all playlists, check if each audiocontent is equal to s, if it is, delete it from the playlist
			if(playlists.size() > 0){
				// As we iterate through each playlist, use getContent and save a copy of the content in variable playlistContent, if the song is in here, then remove it from playlistContent and set the original playlist to playlistContent 
				for (int i = 0; i < playlists.size(); i++){
					ArrayList<AudioContent> playlistContent = playlists.get(i).getContent();
					for(int j = 0; j < playlistContent.size(); j++){
						AudioContent toDelete = playlistContent.get(j);
						if (toDelete.equals(s)){
							playlistContent.remove(s);
							playlists.get(i).setContent((playlistContent));
							}
						}
					}
				}
		
		// If the song isnt found in library (index is invalid), then return false
		} else {
			throw new RuntimeException("Song not found");
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear() // TESTED AND WORKS
	{
		// Use Collections.sort(), and sort ArrayList 'songs' using SongYearComparator
		Collections.sort(songs, new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		// Compares song a and song b years, returns an int based on if a-b is positive, negative or 0, then sorts it based on these integers. (The numbers are small enough to avoid the subtraction errors)
		public int compare(Song a, Song b)
   		{
        	return a.getYear() - b.getYear();
    	}
	}

	// Sort songs by length
	public void sortSongsByLength() // TESTED AND WORKS
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	 
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b)
   		{
			// Compares song a and song b lenghts, returns an int based on if a-b is positive, negative or 0, then sorts it based on these integers. (The numbers are small enough to avoid the subtraction errors)
        	return a.getLength() - b.getLength();
    	}
	}

	// Sort songs by title 
	public void sortSongsByName() // TESTED AND WORKS
	{
		// Collections.sort by default sorts it alphabetically.
		Collections.sort(songs);
	}

	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws AudioContentNotFoundException
	{
		// Checking if index is valid, if it is not, return false, if it is, play then return true.
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws RuntimeException
	{
		// Checking if index is valid, if it is not, return false, if it is, select chapter using chapter parameter, then play
		if (index < 1 || index > audiobooks.size())
		{
			throw new RuntimeException("AudioBook Not Found");
		}
		// get the audiobook from audiobooks using index, then get chapter from said index.
		
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws IndexOutOfBoundsException
	{
		// checking if index valid, if not, return false, otherwise use printTOC() method and return true.
		if (index < 1 || index > audiobooks.size())
		{
			throw new IndexOutOfBoundsException("Audiobook Not Found");
		}
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws RuntimeException
	{	
		// checking playlists ArrayList and if the title is in the playlist, return false, otherwise make a new playlist and add it to arraylists playlist.
		for (Playlist i : playlists) {
			if (i.getTitle().equals(title)){
				throw new RuntimeException("Playlist " + title + " Already Exists");
			}
		}
		Playlist newPlaylist = new Playlist(title);
		playlists.add(newPlaylist);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws RuntimeException
	{	
		// checking if the title of the playlist is in playlists ArrayList, if not return false, if it is print all of its contents.
		Playlist toPrint = null;
		for (Playlist p : playlists){
			if (p.getTitle().equals(title)){
				toPrint = p;
				toPrint.printContents();
			}
		}
		throw new RuntimeException("This playlist doesn't exist");
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws RuntimeException
	{
		// checking if the playlist is in playlists, if it is, use playlist.playAll() to play all of its contents, otherwise return false
		Playlist toPlay = null;
		for (Playlist p : playlists){
			if (p.getTitle().equals(playlistTitle)){
				toPlay = p;
				toPlay.playAll();
			}
		}
		if (toPlay == null){
			throw new RuntimeException("This play list doesn't exist");
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws RuntimeException
	{
		// checking if playlist exists, if it does, play the certain index of the playlist, otherwise return false if either the playlist doesnt exist or index is invalid.
		Playlist toPlay = null;
		for (Playlist p : playlists){
			if (p.getTitle().equals(playlistTitle)){
				toPlay = p;
				System.out.println(playlistTitle);
				toPlay.play(indexInPL);
			} else {
				throw new RuntimeException("This play list doesn't exist");
			}
		}
		if (toPlay == null){
			throw new RuntimeException("This content doesn't exist");
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws RuntimeException
	{	
		// checking if the title is in the playlists arrayList, then save the playlist in variable toAdd, if it does not exist, return false.
		Playlist toAdd = null;
		for (Playlist p : playlists){
			if (p.getTitle().equals(playlistTitle)){
				toAdd = p;
				break;
			}
		}
		if (toAdd == null){
			throw new RuntimeException("This playlist doesn't exist");
		}

		// checking if type is a song or audiobook
		// in both cases, check if the index is valid in the arraylist songs or audiobooks, then add it to said arraylist
		// if index is invalid, or content type is invalid, return false
		if (type.equalsIgnoreCase(Song.TYPENAME)) {
			if (index-1 >= 0 && index-1 < songs.size()) {
					toAdd.addContent(songs.get(index-1));
			} else {
				throw new RuntimeException("This song is not in your library");
			}
		} else if (type.equalsIgnoreCase(AudioBook.TYPENAME)) {
			if (index-1 >= 0 && index-1 < audiobooks.size()) {
					toAdd.addContent(audiobooks.get(index-1));
			} else {
				throw new RuntimeException("This audiobook is not in your library");
			}
		} else {
			throw new RuntimeException("This content type does not exist");
		}

	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws RuntimeException
	{
		// check if the the title is in arraylist playlists, if it doesnt then return false
		Playlist toDel = null;
		for (Playlist p : playlists){
			if (p.getTitle().equals(title)){
				toDel = p;
			} else {
				throw new RuntimeException("This Playlist doesn't exist");
			}
		}

		// checking if index is valid, if it is, delete the content from the playlist and return true, otherwise return false
		if (index-1 >= 0 && index-1 < toDel.getContent().size()){
			toDel.deleteContent(index);
		} else {
			throw new RuntimeException("Content not found");
		}
	}


	
}

// Custom exceptions
class AudioContentNotFoundException extends RuntimeException{
	AudioContentNotFoundException(String message){
		super(message);
	}
}

class AlreadyDownloadedException extends RuntimeException{
	AlreadyDownloadedException(String message){
		super(message);
	}
}


class DuplicatePlaylistException extends RuntimeException{
	DuplicatePlaylistException(String message){
		super(message);
	}
}



