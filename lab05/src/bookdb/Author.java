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
 * Record of author of book
 * @param firstName First name of author
 * @param lastName Last name of author
 * @param id Unique identifier of author in system
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Author
{
    /**
     * First name of author
     */
    private String firstName;
    
    /**
     * Last name of author
     */
    private String lastName;
    
    /**
     * Identifier of author in system
     */
    private String id;

    public Author(String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder reti = new StringBuilder();
        reti.append(this.lastName);
        reti.append(", ");
        reti.append(this.firstName);
        return reti.toString();
    }
    
    
    
}
