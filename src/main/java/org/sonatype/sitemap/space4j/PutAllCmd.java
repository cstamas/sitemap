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

import java.util.Map;

import org.space4j.Command;
import org.space4j.CommandException;
import org.space4j.Space;
import org.space4j.indexing.IndexManager;

@SuppressWarnings( "serial" )
public class PutAllCmd<K extends Object, V extends Object>
    extends Command
{
    private final Object target;

    private final Map<K, V> all;

    public PutAllCmd( Object target, Map<K, V> all )
    {
        this.target = target;

        this.all = all;
    }

    private void index( Space space, Object value )
    {
        IndexManager im = space.getIndexManager();

        if ( im != null )
        {
            im.add( value, target );
        }
    }

    public int execute( Space space )
        throws CommandException
    {
        Object obj = space.get( target );

        if ( obj == null )
        {
            return 0;
        }

        // map!

        if ( obj instanceof Map )
        {
            Map<Object, Object> m = (Map<Object, Object>) obj;

            int result = 0;

            for ( Map.Entry<K, V> e : all.entrySet() )
            {
                Object key = e.getKey();

                Object value = e.getValue();

                Object prev = m.put( key, value );

                index( space, value );

                if ( prev == null )
                {
                    result++;
                }
                else if ( prev == value )
                {
                    // added the same thing...
                }
                else
                {
                    result++;
                }
            }

            return result;
        }
        else
        {
            throw new CommandException( "Target is not a map: " + target + " (" + obj.getClass().getName() + ")" );
        }
    }
}
