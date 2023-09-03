// Name: Kirsten Sotelo
// Student ID: 501169612
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore extends RuntimeException
{
		// Declaring contents ArrayList and also the three different maps accordingly
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> titleAndIndex;
		private Map<String, ArrayList<Integer>> artistAndSongs;
		private Map<String, ArrayList<Integer>> genreAndSongs;
		
		public AudioContentStore(String filename)
		{
			contents = new ArrayList<AudioContent>();
			titleAndIndex = new TreeMap<String, Integer>();
			artistAndSongs = new TreeMap<String, ArrayList<Integer>>();
			genreAndSongs = new TreeMap<String, ArrayList<Integer>>();
			
		// Create some songs audiobooks and podcasts and to store
		// Song in store.txt: Song.TYPENAME, id (string), title(string), year(int), length(int), artist(string), composer(string), Song.Genre.POP, n lines of lyrics(int), lyrics(string)
		// Song: title(string), year(int), id(string), Song.TYPENAME, audioFile(string), length(int), artist(string), composer(string), Song.Genre.POP, lyrics(string)
		// AudioBook in store.txt: AudioBook.TYPENAME, id(string), title(string), title(string), year(int), length(int), author(string), narrator(string), n lines of chapter titles, chapter titles, n lines of the first chapter, the first chapter, etc...

		// read files from store.txt
		try {
            File store = new File(filename);
            Scanner scanner = new Scanner(store);

			// collecting the appropriate variables given by store.txt, then adding it to contents ArrayList
			// The process is slightly different for songs and audiobooks, but same concept
            while (scanner.hasNextLine()) {
				String contentType = scanner.nextLine();
				if(contentType.equalsIgnoreCase("SONG")){
					String id = scanner.nextLine();
					String title = scanner.nextLine();
					int year = Integer.parseInt(scanner.nextLine());
					int length = Integer.parseInt(scanner.nextLine());
					String artist = scanner.nextLine();
					String composer = scanner.nextLine();
					String genreStr = scanner.nextLine();
					Song.Genre genre = Song.Genre.valueOf(genreStr);
					int linesOfLyrics = Integer.parseInt(scanner.nextLine());
					String audioFile = "";

					for (int i = 0; i < linesOfLyrics; i++){
						audioFile += scanner.nextLine();
					}

					System.out.println("Loading SONG");
					contents.add(new Song(title, year, id, Song.TYPENAME, audioFile, length, artist, composer, genre, audioFile));

					
				} else if(contentType.equalsIgnoreCase("AUDIOBOOK")){
					String id = scanner.nextLine();
					String title = scanner.nextLine();
					int year = Integer.parseInt(scanner.nextLine());
					int length = Integer.parseInt(scanner.nextLine());
					String author = scanner.nextLine();
					String narrator = scanner.nextLine();
					int linesOfChapterTitles = Integer.parseInt(scanner.nextLine());
					ArrayList<String> chapterTitles = new ArrayList<String>();
					
					for(int i = 0; i< linesOfChapterTitles; i++){
						chapterTitles.add(scanner.nextLine());
					}

					ArrayList<String> chapters = new ArrayList<String>();
					for(int i = 0; i< linesOfChapterTitles; i++){
						int nthChapter = Integer.parseInt(scanner.nextLine());
						String chapterContent = "";
						for(int j = 0; j<nthChapter;j++){
							chapterContent += scanner.nextLine() + "\n";
						}
						chapters.add(chapterContent);
					}

					System.out.println("Loading AUDIOBOOK");
					contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
				}

            }


			// Start of titleAndIndex map
			// for each audiocontent in contents, get the title and store it with the corresponding index and put it in titleAndIndex map.
			int index = 1;
			for(AudioContent a : contents){
				String title = a.getTitle();
				titleAndIndex.put(title, index);
				index++;
			}	
			// End of titleAndIndex map


			// Start of artistAndSongs map
			// Adding to the artistAndSongs map, which contains a key of string (artist name) and value of arraylist<integer> (indices of songs in store of said artist)
			// The following block of code creates a separate arraylist of all the different artists
			ArrayList<String> artists = new ArrayList<String>(); 
			for (AudioContent a : contents){
				if(a.getType().equals(Song.TYPENAME)){
					Song s = (Song) a;
					String artist = s.getArtist();
					boolean inside = artists.contains(artist);
					if (!inside) {
						artists.add(artist);
					}
				}
			}

			// The following block of code utilizes the artists arraylist
			// For each artist in 'artists', go through the content store and see if each content in the store has an equal artist name. 
			// If it does, add it to a temporary 'indices' arraylist. At the end of iterating through all content in store, add the artist name and indices arraylist to the key and value of artistAndSongs TreeMap
			int index2 = 1;
			for(String name : artists){
				ArrayList<Integer> indices = new ArrayList<Integer>();
				for(AudioContent a : contents){
					if(a.getType().equals(Song.TYPENAME)){
						Song s = (Song) a;
						String artist = s.getArtist();
						if (name.equals(artist)){
							indices.add(index2);
						}
					}
					index2++;
				}
				artistAndSongs.put(name, indices);
				index2=1;
			}
			// End of artistAndSongs map

			// Start of genreAndSongs map
			// The following block of code creates an ArrayList of the different genres
			ArrayList<String> genres = new ArrayList<String>(); 
			for (AudioContent a : contents){
				if(a.getType().equals(Song.TYPENAME)){
					Song s = (Song) a;
					Song.Genre genre = s.getGenre();
					boolean inside = genres.contains(genre.name());
					if (!inside) {
						genres.add(genre.name());

					}
				}
			}

			// The following block of code utilizes the different genres ArrayList and creates an ArrayList of indices with each different song that shares the current genre
			int index3 = 1;
			for(String g : genres){
				ArrayList<Integer> indices = new ArrayList<Integer>();
				for(AudioContent a : contents){
					if(a.getType().equals(Song.TYPENAME)){
						Song s = (Song) a;
						String genre = s.getGenre().name();
						if (g.equals(genre)){
							indices.add(index3);
						}
					}
					index3++;
				}
				genreAndSongs.put(g, indices);
				index3=1;
			}

        } catch (IOException e) {
            System.out.println(e.getMessage());
			System.exit(1);
        	}
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}

		// Method to return the artist indices to use for MyAudioUI download
		public ArrayList<Integer> getArtistIndices(String artist) throws ArtistNotFoundException
		{
			if(artistAndSongs.containsKey(artist)){
				return artistAndSongs.get(artist);
			} else {
				throw new ArtistNotFoundException(artist + " not found");
			}
		}

		// Method to return the genre indices to use for MyAudioUI download
		public ArrayList<Integer> getGenreIndices(String genre) throws GenreNotFoundException
		{
			if(genreAndSongs.containsKey(genre)){
				return genreAndSongs.get(genre);
			}
			throw new GenreNotFoundException("Genre not found");
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		// ASSIGNMENT 2 FUNCTION : search -> search a title of a song, and prints the song and its index in the store if the title exists
		public void search(String title) throws TitleNotFoundException{
			if(titleAndIndex.containsKey(title)){
				int index = titleAndIndex.get(title);
				System.out.print(index + ". ");
				contents.get(index-1).printInfo();
			} else {
				throw new TitleNotFoundException("No matches for " + title);
			}
		}

		// ASSIGNMENT 2 FUNCTION : searchArtist -> search an artist's name, and prints the song(s) and its index in the store if the artist exists.
		public void searchArtist(String artist) throws ArtistNotFoundException{
			if(artistAndSongs.containsKey(artist)){
				ArrayList<Integer> indices = artistAndSongs.get(artist);
				for(int i : indices){
					System.out.print(i + ". ");
					contents.get(i-1).printInfo();
					System.out.println();
				}
			} else {
				throw new ArtistNotFoundException("No matches for " + artist);
			}
		}

		// ASSIGNMENT 2 FUNCTION : searchArtist -> search a genre and prints the song(s) and its index in the store if the genre exists.
		public void searchGenre(String genre) throws GenreNotFoundException{
			if(genreAndSongs.containsKey(genre)){
				ArrayList<Integer> indices = genreAndSongs.get(genre);
				for(int i : indices){
					System.out.print(i + ". ");
					contents.get(i-1).printInfo();
					System.out.println();
				}
			} else {
				throw new GenreNotFoundException("No matches for " + genre);
			}
		}

		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/



		
}

class TitleNotFoundException extends RuntimeException{
	TitleNotFoundException(String message){
		super(message);
	}
}

class ArtistNotFoundException extends RuntimeException{
	ArtistNotFoundException(String message){
		super(message);
	}
}

class GenreNotFoundException extends RuntimeException{
	GenreNotFoundException(String message){
		super(message);
	}
}

class IndicesNotValidException extends RuntimeException{
	IndicesNotValidException(String message){
		super(message);
	}
}