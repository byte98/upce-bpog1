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

/**
 * Class representing book in system
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Book
{
    /**
     * Author of book
     */
    private Author author;
    
    /**
     * Name of book
     */
    private String name;
    
    /**
     * Identifier of book in system
     */
    private String id;
    
    /**
     * Price of book
     */
    private int price;

    /* Creates new book
     * @param author Author of book
     * @param name Name of book
     * @param price Price of book
     * @param id Unique identifier of book in system
     */
    public Book(Author author, String name, int price, String id)
    {
        this.author = author;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    @Override
    public String toString()
    {
        StringBuilder reti = new StringBuilder();
        reti.append(this.name);
        reti.append(" (");
        reti.append(this.author);
        reti.append(")");
        return reti.toString();
    }
    
    
    
}
