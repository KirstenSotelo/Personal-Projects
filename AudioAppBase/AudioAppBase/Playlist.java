// Name: Kirsten Sotelo
// Student ID: 501169612 
import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents() // iterating through contents ArrayList, and using get method to print its info.
	{	
		for (int i = 0; i < contents.size(); i++){
			System.out.print((i+1) + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll() // for each audiocontent in contents, play it
	{
		for (AudioContent c: contents){
			c.play();
			System.out.println();
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index) // checking if index is inside the contents and greater than 0, then playing index
	{
		if (index >= 1 && index <= contents.size())
		{
			index = index - 1;
			contents.get(index).play();
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other) // casting other object into a playlist, then checking if the two objects have equal titles.
	{
		Playlist otherPlay = (Playlist) other;
		return title.equals(otherPlay.title);
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index) // if statement to check if contents contains the index, if it does not, exit. If it does, then remove the index.
	{
		if (!contains(index)) return;
		contents.remove(index-1);
	}
}
