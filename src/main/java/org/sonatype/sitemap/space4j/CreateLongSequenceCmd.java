/*
 * Space4J http://www.space4j.org/
 * Copyright (C) 2007  Sergio Oliveira Jr. (sergio.oliveira.jr@gmail.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.sonatype.sitemap.space4j;

import org.space4j.Command;
import org.space4j.CommandException;
import org.space4j.Space;

@SuppressWarnings( "serial" )
public class CreateLongSequenceCmd
    extends Command
{
    private final Object key;

    private final long startWith;

    public CreateLongSequenceCmd( Object key )
    {
        this.key = key;

        this.startWith = 0L;
    }

    public CreateLongSequenceCmd( Object key, long startWith )
    {
        this.key = key;

        this.startWith = startWith;
    }

    public int execute( Space space )
        throws CommandException
    {
        if ( space.check( key ) )
        {
            return 0;
        }

        long[] id = new long[1];

        id[0] = startWith;

        space.put( key, id );

        return 1;
    }
}
