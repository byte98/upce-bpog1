/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package bookdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Model {
    /**
     * Name of CSV file containing list of authors
     */
    private static final URL AUTHORS = bookdb.class.getResource("authors.csv");
    
    /**
     * Name of CSV file containing list of books
     */
    private static final URL BOOKS = bookdb.class.getResource("books.csv");
    
    /**
     * List of available characters used to generate book identifiers
     */
    private static final String BOOK_IDS = "ABCDEFGHIJKLMNOPQRSTUVabcdefghijklmnopqrstuvwxyz0123456789";
    
    /**
     * List of available charactes used to generate author identifiers
     */
    private static final String AUTHOR_IDS = Model.BOOK_IDS;
    
    /**
     * Maximal length of book identifier
     */
    private static final int BOOK_ID_LEN = 8;
    
    /**
     * Maximal length of author identifier
     */
    private static final int AUTHOR_ID_LEN = 4;
    
    /**
     * List of authors available to system
     */
    private List<Author> authors;
    
    /**
     * List of books available to system
     */
    private List<Book> books;
    
    /**
     * Creates new data model for system
     */
    public Model()
    {
        this.authors = new ArrayList<>();
        this.books   = new ArrayList<>();
        this.loadAuthors();
        this.loadBooks();
    }
    
    /**
     * Loads authors from CSV file
     */
    private void loadAuthors()
    {
        try
        {
            File file = Paths.get(Model.AUTHORS.toURI()).toFile();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length == 3) // Valid row is only and if only has 3 items
                {
                    this.authors.add(new Author(parts[1], parts[2], parts[0]));
                }
            }
        }
        catch (URISyntaxException | FileNotFoundException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    /**
     * Saves list of available authors to CSV file
     */
    private void saveAuthors()
    {
        // Step 1: Create string with output
        StringBuilder output = new StringBuilder();
        ListIterator<Author> it = this.authors.listIterator();
        while (it.hasNext())
        {
            Author a = it.next();
            if (a != null)
            {
                output.append(a.getId());
                output.append(",");
                output.append(a.getFirstName());
                output.append(",");
                output.append(a.getLastName());
                output.append(System.lineSeparator());
            }
        }
        // Step 2: Write string with output to file
        try
        {
            FileWriter fw = new FileWriter(Paths.get(Model.AUTHORS.toURI()).toFile());
            fw.write(output.toString());
            fw.close();
        }
        catch (URISyntaxException | IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    
    /**
     * Loads books from CSV file
     */
    private void loadBooks()
    {
        try
        {
            File file = Paths.get(Model.BOOKS.toURI()).toFile();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length == 4) // Valid row is only and if only has 4 items
                {
                    this.books.add(new Book(this.getAuthorById(parts[1]), parts[2], Integer.parseInt(parts[3]), parts[0]));
                }
            }
        }
        catch (URISyntaxException | FileNotFoundException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * Saves list of available authors to CSV file
     */
    private void saveBooks()
    {
        // Step 1: Create string with output
        StringBuilder output = new StringBuilder();
        Iterator<Book> it = this.books.listIterator();
        while (it.hasNext())
        {
            Book b = it.next();
            if (b != null)
            {
                output.append(b.getId());
                output.append(",");
                output.append(b.getAuthor().getId());
                output.append(",");
                output.append(b.getName());
                output.append(",");
                output.append(b.getPrice());
                output.append(System.lineSeparator());
            }
        }
        // Step 2: Write string with output to file
        try
        {
            FileWriter fw = new FileWriter(Paths.get(Model.BOOKS.toURI()).toFile());
            fw.write(output.toString());
            fw.close();
        }
        catch (URISyntaxException | IOException ex)
        {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets author by its identifier
     * @param id Identifier of author in system
     * @return Author with selected identifier
     *         or NULL if there is not such an author
     */
    public Author getAuthorById(String id)
    {
        Author reti = null;
        Iterator<Author> it = this.authors.listIterator();
        while(it.hasNext())
        {
            Author a = it.next();
            if (a != null && a.getId().equals(id))
            {
                reti = a;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets book by its identifier in system
     * @param id Identifier of book in system
     * @return Book selected by its identifier in system
     *         or NULL, if there is not such a book
     */
    public Book getBookById(String id)
    {
        Book reti = null;
        Iterator<Book> it = this.books.listIterator();
        while(it.hasNext())
        {
            Book b = it.next();
            if (b != null && b.getId().equals(id))
            {
                reti = b;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Generates random string
     * @param length Length of string
     * @param alphabet Alphabet used to generate string
     * @return Randomly generated string
     */
    private String generateRandomString(int length, String alphabet)
    {
        Random random = ThreadLocalRandom.current();
        char[] reti = new char[length];
        for (int i = 0; i < length; i++)
        {
            reti[i] = alphabet.toCharArray()[random.nextInt(0, alphabet.length())];
        }
        return new String(reti);
    }
    
    /**
     * Creates new author
     * @param firstName First name of author
     * @param lastName Last name of author
     * @return New instance of author
     */
    public Author createAuthor(String firstName, String lastName)
    {
        String id = "";
        Random random = ThreadLocalRandom.current();
        do
        {
            id = this.generateRandomString(random.nextInt(1, Model.AUTHOR_ID_LEN + 1), Model.AUTHOR_IDS);
        }
        while (this.getAuthorById(id) != null);
        Author reti = new Author(firstName, lastName, id);
        this.authors.add(reti);
        this.saveAuthors();
        return reti;
    }
    
    /**
     * Creates new book in system
     * @param author Author of book
     * @param name Name of book
     * @param price Price of book
     * @return New instance of book
     */
    public Book createBook(Author author, String name, int price)
    {
        String id = "";
        Random random = ThreadLocalRandom.current();
        do
        {
            id = this.generateRandomString(random.nextInt(1, Model.BOOK_ID_LEN + 1), Model.BOOK_IDS);
        }
        while (this.getAuthorById(id) != null);
        Book reti = new Book(author, name, price, id);
        this.books.add(reti);
        this.saveBooks();
        return reti;
    }
    
    /**
     * Creates new book in system
     * @param authorID Identifier of author of book
     * @param name Name of book
     * @param price Price of book
     * @return New instance of book
     */
    public Book createBook(String authorID, String name, int price)
    {
        return this.createBook(this.getAuthorById(authorID), name, price);
    }
    
    /**
     * Gets all available authors to system
     * @return List of all available authors to system
     */
    public List<Author> getAuthors()
    {
        return this.authors;
    }
    
    /**
     * Gets all available books in system
     * @return List of all available books in system
     */
    public List<Book> getBooks()
    {
        return this.books;
    }
    
    /**
     * Removes book from system
     * @param b Book which will be removed from system
     */
    public void removeBook(Book b)
    {
        this.books.remove(b);
        this.saveBooks();
    }
    
    /**
     * Saves all data to file
     */
    public void save()
    {
        this.saveAuthors();
        this.saveBooks();
    }
}
